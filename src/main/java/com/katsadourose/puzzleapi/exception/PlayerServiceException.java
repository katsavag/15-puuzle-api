package com.katsadourose.puzzleapi.exception;

import lombok.Getter;

@Getter
public class PlayerServiceException extends RuntimeException{

    private final ErrorCode errorCode;

    public PlayerServiceException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
