package com.sbab.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbab.demo.model.*;
import com.sbab.demo.service.TrafikLabService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Slf4j
public class TrafikLabController {
    @Autowired
    private TrafikLabService trafikLabService;


    @CrossOrigin(origins = "*")
    @GetMapping("/getJourData")
    public List<JourDataResponse> getJourData() {
        return trafikLabService.processGetJourData();
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/getStopsForLine")
    public List<StopData>  getStopsForLine(@RequestParam(value = "lineNumber") int lineNumberIn, @RequestParam(value = "directionCode") int directionCodeIn) throws JsonProcessingException {
        return trafikLabService.processGetStopData(lineNumberIn, directionCodeIn);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value={"", "/", "hello"})
    public String  getHello()  {
        return "Hello!";
    }

}
