package com.sbab.demo.controller;

import com.sbab.demo.util.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.bind.ValidationException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    ErrorMessage handleValidationException(ValidationException e) {
        return new ErrorMessage("400", e.getMessage());
    }
}
