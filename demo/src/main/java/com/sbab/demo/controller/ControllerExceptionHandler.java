package com.sbab.demo.controller;

import com.sbab.demo.util.DemoAppRunTimeException;
import com.sbab.demo.util.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(DemoAppRunTimeException.class)
    ErrorMessage handleDemoAppException(DemoAppRunTimeException e) {
        return new ErrorMessage("400", e.getMessage());
    }
}
