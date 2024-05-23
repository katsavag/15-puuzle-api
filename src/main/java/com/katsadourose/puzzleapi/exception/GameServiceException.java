package com.katsadourose.puzzleapi.exception;

import lombok.Getter;

@Getter
public class GameServiceException extends RuntimeException{
    private final ErrorCode errorCode;
    public GameServiceException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
