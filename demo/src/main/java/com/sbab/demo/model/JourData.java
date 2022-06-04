package com.sbab.demo.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class JourData {

    private int lineNumber;
    private int directionCode;
    private String journeyPatternPointNumber;

}
