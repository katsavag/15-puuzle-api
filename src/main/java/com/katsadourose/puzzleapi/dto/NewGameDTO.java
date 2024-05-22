package com.katsadourose.puzzleapi.dto;

import com.katsadourose.puzzleapi.model.PuzzleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record NewGameDTO(
        @NotNull
        @Min(0)
        Integer playerId,
        @NotNull
        PuzzleType puzzleType
        )
{ }
