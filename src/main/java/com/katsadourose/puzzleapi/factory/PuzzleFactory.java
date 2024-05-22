package com.katsadourose.puzzleapi.factory;

import com.katsadourose.puzzleapi.model.*;
import com.katsadourose.puzzleapi.model.puzzle_model.EasyPuzzle15;
import com.katsadourose.puzzleapi.model.puzzle_model.HardPuzzle15;
import com.katsadourose.puzzleapi.model.puzzle_model.MediumPuzzle15;

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
