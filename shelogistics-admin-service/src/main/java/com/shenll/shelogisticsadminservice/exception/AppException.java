package com.shenll.shelogisticsadminservice.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
    HttpStatus status;

    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }


}

