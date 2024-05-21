package com.katsadourose.puzzleapi.factory;

import com.katsadourose.puzzleapi.model.*;

public class PuzzleFactory {
    public static Puzzle15 createPuzzle(PuzzleType type) {
        return switch (type) {
            case EASY -> new EasyPuzzle15();
            case MEDIUM -> new MediumPuzzle15();
            case HARD -> new HardPuzzle15();
            default -> null;
        };
    }
}
