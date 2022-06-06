package com.sbab.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sbab.demo.model.*;
import com.sbab.demo.service.TrafikLabService;
import com.sbab.demo.util.DemoAppRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    public List<StopData> getStopsForLine(
            @RequestParam(value = "lineNumber") @Min(0) @Max(Integer.MAX_VALUE) int lineNumberIn,
            @RequestParam(value = "directionCode") @Min(0) @Max(Integer.MAX_VALUE) int directionCodeIn)
            throws JsonProcessingException {
        return trafikLabService.processGetStopData(lineNumberIn, directionCodeIn);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = {"", "/", "hello"})
    public String getHello() {
        return "Hello!";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = {"/**"}, method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
    public Exception handleException() throws DemoAppRunTimeException {
        throw new DemoAppRunTimeException("Requested route unavailable!");
    }

}
