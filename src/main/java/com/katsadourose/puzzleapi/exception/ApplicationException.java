package com.katsadourose.puzzleapi.exception;

import lombok.Getter;

@Getter
public final class ApplicationException extends RuntimeException{

    private final ErrorCode errorCode;
    public ApplicationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
