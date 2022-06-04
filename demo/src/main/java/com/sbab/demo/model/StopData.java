package com.sbab.demo.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StopData {

    private String stopPointNumber;
    private String stopPointName;
    //private String stopPointNorthIndicator;
    //private String stopPointEastIndicator;

}
