package com.katsadourose.puzzleapi.dto;

import java.time.LocalDateTime;

public record ErrorDTO (int status, String error, String message, String path, LocalDateTime dateTime){ }
