package com.katsadourose.puzzleapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "User is already registered"),
    PARAMETERS_VALIDATION(HttpStatus.BAD_REQUEST, "Parameters validation failed"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found");

    private final HttpStatus httpStatus;
    private final String message;

    private ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

