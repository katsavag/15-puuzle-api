package com.katsadourose.puzzleapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "User is already registered"),
    PARAMETERS_VALIDATION(HttpStatus.BAD_REQUEST, "Parameters validation failed"),
    GAME_NOT_FOUND(HttpStatus.NOT_FOUND, "Game not found"),
    PLAYER_NOT_FOUND(HttpStatus.NOT_FOUND, "Player not found"),
    MAX_GAMES_ENTRIES(HttpStatus.BAD_REQUEST, "Player has reached the maximum number of games"),
    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred");


    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

