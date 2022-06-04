package com.sbab.demo.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Data
@Component
public class JourDataResponse {

    private int lineNumber;
    private int directionCode;
    private int count;

}
