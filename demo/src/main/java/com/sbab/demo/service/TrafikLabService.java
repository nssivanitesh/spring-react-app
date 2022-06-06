package com.sbab.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbab.demo.model.JourData;
import com.sbab.demo.model.JourDataResponse;
import com.sbab.demo.model.StopData;
import com.sbab.demo.proxy.APIProxy;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TrafikLabService {

    @Autowired
    private APIProxy apiProxy;

    @Value("${trafiklab.api.key}")
    private String apiKey;

    public List<Map.Entry<String, Integer>> sortByDesc(HashMap<String, Integer> map) {
        return new LinkedList<Map.Entry<String, Integer>>(map.entrySet())
                .stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toList());
    }

    JsonNode getRemoteAPIData(String model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(apiProxy.getTrafikLabData(model, apiKey));

        log.info("Searching for {}", model);
        if (node.isNull()) {
            return null;
        }

        JsonNode responseData = node.get("ResponseData");
        if (responseData.isNull()) {
            return null;
        }

        return responseData.get("Result");
    }

    public List<JourData> getJourDataList(boolean getAll, int lineNumberIn, int directionCodeIn) throws JsonProcessingException {
        long start = System.currentTimeMillis();
        log.info("Getting jour data list", getAll, lineNumberIn, directionCodeIn);
        JsonNode results = getRemoteAPIData("jour");

        if (results.isNull() || results.isEmpty()) {
            log.info("Jour data list: null");
            return null;
        }

        List<JourData> jourDataList = new ArrayList<>();
        results.spliterator().forEachRemaining(result -> {
            int lineNo = result.get("LineNumber").asInt();
            int directionCode = result.get("DirectionCode").asInt();
            if (getAll || (lineNumberIn == lineNo && directionCodeIn == directionCode)) {
                JourData jourData = new JourData();
                jourData.setLineNumber(lineNo);
                jourData.setDirectionCode(directionCode);
                jourData.setJourneyPatternPointNumber(result.get("JourneyPatternPointNumber").toString());
                jourDataList.add(jourData);
            } else {
                //Just loop until we have everything we need
            }
        });
        log.info("Jour data list: {}", jourDataList.size());
        log.info("Time taken to get jour data list: {}", System.currentTimeMillis() - start);
        return jourDataList;
    }

    public List<StopData> getStopDataList() throws JsonProcessingException {
        long start = System.currentTimeMillis();
        log.info("Getting stop data list");
        JsonNode results = getRemoteAPIData("stop");
        if (results.isNull()) {
            log.info("Stop data list: null");
            return null;
        }

        List<StopData> stopDataList = new ArrayList<>();
        results.spliterator().forEachRemaining(result -> {
            StopData stopData = new StopData();
            stopData.setStopPointNumber(result.get("StopPointNumber").toString());
            stopData.setStopPointName(result.get("StopPointName").asText());

            stopDataList.add(stopData);
        });
        log.info("Stop data list: {}", stopDataList.size());
        log.info("Time taken to get stop data list: {}", System.currentTimeMillis() - start);
        return stopDataList;
    }

    @NotNull
    public ArrayList<JourDataResponse> processGetJourData() {
        log.info("New request: Getting jour data");
        HashMap<String, Integer> top10 = new HashMap<String, Integer>();
        List<JourData> jourDataList = new ArrayList<JourData>();
        ArrayList<JourDataResponse> jourDataResponseList = new ArrayList<>();

        try {
            jourDataList = getJourDataList(true, 0, 0);

            if (jourDataList == null || jourDataList.isEmpty()) {
                log.info("Jour data list: null");
                return jourDataResponseList;
            }

            jourDataList.forEach(jourData -> {
                String key = "LN:" + jourData.getLineNumber() + ":DC:" + +jourData.getDirectionCode();
                if (top10.containsKey(key)) {
                    top10.put(key, top10.get(key) + 1);
                } else {
                    top10.put(key, 1);
                }
            });
            log.info("Top 10: {}", top10);

            if (!top10.isEmpty()) {
                sortByDesc(top10).subList(0, 10).forEach(result -> {
                    JourDataResponse response = new JourDataResponse();
                    String key = result.getKey();
                    String[] arrOfStr = key.split(":", 4);

                    response.setLineNumber(Integer.parseInt(arrOfStr[1]));
                    response.setDirectionCode(Integer.parseInt(arrOfStr[3]));
                    response.setCount(result.getValue());

                    jourDataResponseList.add(response);
                });
            }

            log.info("Jour data response list: {}", jourDataResponseList);
            return jourDataResponseList;
        } catch (JsonProcessingException e) {
            log.error("Error getting jour data", e);
            return null;
        }
    }

    @NotNull
    public ArrayList<StopData> processGetStopData(int lineNumberIn, int directionCodeIn) throws JsonProcessingException {
        log.info("New Request: Getting stops for line: {}", lineNumberIn, directionCodeIn);
        List<JourData> jourDataList = getJourDataList(false, lineNumberIn, directionCodeIn);
        List<StopData> stopDataList = getStopDataList();
        ArrayList<String> journeyPatternPointNumbers = new ArrayList<>();
        jourDataList.forEach(jourData -> {
            journeyPatternPointNumbers.add(jourData.getJourneyPatternPointNumber());
        });

        ArrayList<StopData> stopsForLine = new ArrayList<>();
        if (!journeyPatternPointNumbers.isEmpty()) {
            stopDataList.forEach(stopData -> {
                if (journeyPatternPointNumbers.indexOf(stopData.getStopPointNumber()) > -1) {
                    stopsForLine.add(stopData);
                }
            });
        }
        log.info("Stops for line: {}", stopsForLine.size());
        return stopsForLine;
    }
}
