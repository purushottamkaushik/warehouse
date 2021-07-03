package com.warehouse.custom.handler;

import com.warehouse.custom.error.ErrorType;
import com.warehouse.customexception.UomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UomNotFoundException.class)
    public ResponseEntity<ErrorType> uomExceptionHandling(UomNotFoundException uomNotFoundException) {

        return new ResponseEntity<>(new ErrorType(
                new Date().toString(),
                "UOM",
                uomNotFoundException.getMessage()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
