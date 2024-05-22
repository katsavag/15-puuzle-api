package com.katsadourose.puzzleapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class Game {
    private final Integer id;
    private final Integer playerId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Puzzle15 puzzle;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Game{");
        sb.append(String.format("id: %s, ", id));
        sb.append(String.format("playerId: %s, ", playerId));
        sb.append(String.format("createdAt: %s, ", createdAt));
        sb.append(String.format("updatedAt: %s, ", updatedAt));
        sb.append(String.format("puzzle: %s", puzzle));
        sb.append("}");

        return sb.toString();
    }
}
