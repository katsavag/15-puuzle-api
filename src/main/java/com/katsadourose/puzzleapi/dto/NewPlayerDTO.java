package com.katsadourose.puzzleapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NewPlayerDTO(
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Player's name can only contain English letters, numbers and underscores.")
        String playerName
) { }
