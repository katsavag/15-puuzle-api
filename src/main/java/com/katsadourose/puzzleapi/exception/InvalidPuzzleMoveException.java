package com.katsadourose.puzzleapi.exception;

public class InvalidPuzzleMoveException extends RuntimeException{
    public InvalidPuzzleMoveException(String message) {
        super(message);
    }
}
