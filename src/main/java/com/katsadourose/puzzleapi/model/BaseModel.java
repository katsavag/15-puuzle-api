package com.katsadourose.puzzleapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
abstract class BaseModel {

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}
