package com.katsadourose.puzzleapi.factory;

import com.katsadourose.puzzleapi.model.*;
import com.katsadourose.puzzleapi.model.puzzle_model.EasyPuzzle15;
import com.katsadourose.puzzleapi.model.puzzle_model.HardPuzzle15;
import com.katsadourose.puzzleapi.model.puzzle_model.MediumPuzzle15;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class GameFactory {
    public static Game createGame(Integer playerId, PuzzleType type) {
        Puzzle15 puzzle = switch (type) {
            case EASY -> new EasyPuzzle15();
            case MEDIUM -> new MediumPuzzle15();
            case HARD -> new HardPuzzle15();
            default -> throw new IllegalArgumentException("Invalid puzzle type");
        };
        return new Game.GameBuilder()
                .setPlayerId(playerId)
                .setPuzzleType(type)
                .setPuzzle(puzzle)
                .setTotalMoves(new AtomicInteger(0))
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now())
                .build();
    }
}
