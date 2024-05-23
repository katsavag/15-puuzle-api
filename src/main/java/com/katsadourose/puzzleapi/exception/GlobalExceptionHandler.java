package com.katsadourose.puzzleapi.exception;

import com.katsadourose.puzzleapi.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerServiceException.class)
    public ResponseEntity<ErrorDTO> handlePlayerServiceException(HttpServletRequest req, PlayerServiceException ex) {
        int status = ex.getErrorCode().getHttpStatus().value();
        String error = ex.getErrorCode().name();
        String message = ex.getErrorCode().getMessage();
        String path = req.getRequestURI();
        return new ResponseEntity<>(
                new ErrorDTO(status, error, message, path, LocalDateTime.now()),
                ex.getErrorCode().getHttpStatus()
        );
    }

    @ExceptionHandler(GameServiceException.class)
    public ResponseEntity<ErrorDTO> handlePlayerServiceException(HttpServletRequest req, GameServiceException ex) {
        int status = ex.getErrorCode().getHttpStatus().value();
        String error = ex.getErrorCode().name();
        String message = ex.getErrorCode().getMessage();
        String path = req.getRequestURI();
        return new ResponseEntity<>(
                new ErrorDTO(status, error, message, path, LocalDateTime.now()),
                ex.getErrorCode().getHttpStatus()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handlePlayerServiceException(HttpServletRequest req, Exception ex) {
        int status = 500;
        String error = ErrorCode.INTERNAL_SERVER.name();
        String message = "Unexpected error occurred";
        String path = req.getRequestURI();
        return new ResponseEntity<>(
                new ErrorDTO(status, error, message, path, LocalDateTime.now()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
