package com.shenll.shelogisticsapigateway.exception;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@Configuration
public class AppExpceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<AppErrorMessage> handleException(AppException ex) {
        return status(ex.getStatus()).body(new AppErrorMessage(ex.getMessage()));
    }

}

