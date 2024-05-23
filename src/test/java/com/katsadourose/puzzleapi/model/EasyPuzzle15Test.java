package com.katsadourose.puzzleapi.model;

import com.katsadourose.puzzleapi.PuzzleUtils;
import com.katsadourose.puzzleapi.exception.InvalidPuzzleMoveException;
import com.katsadourose.puzzleapi.factory.GameFactory;
import com.katsadourose.puzzleapi.model.puzzle_model.EasyPuzzle15;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

public class EasyPuzzle15Test {

    @Test
    public void testMoveValidInput() {
        try (MockedStatic<PuzzleUtils> mocked = Mockito.mockStatic(PuzzleUtils.class)) {
            mocked.when(() -> PuzzleUtils.getRandomTilesNumbers(anyInt())).thenReturn(Arrays.asList(1, 2, 0, 3));
            EasyPuzzle15 easyPuzzle15 = new EasyPuzzle15();
            TilePosition targetTile = new TilePosition(1, 1);
            assertDoesNotThrow(() -> easyPuzzle15.moveTile(targetTile));
            assertArrayEquals(new int[][]{{1, 2}, {3, 0}}, easyPuzzle15.getPuzzleBoard());
            assertEquals(1, easyPuzzle15.getTotalMoves());
        }
    }

    @Test
    public void testMoveInvalidInput() {
        try (MockedStatic<PuzzleUtils> mocked = Mockito.mockStatic(PuzzleUtils.class)) {
            mocked.when(() -> PuzzleUtils.getRandomTilesNumbers(anyInt())).thenReturn(Arrays.asList(1, 2, 0, 3));
            EasyPuzzle15 easyPuzzle15 = new EasyPuzzle15();
            TilePosition targetTile = new TilePosition(0, 1);
            assertThrows(InvalidPuzzleMoveException.class, () -> easyPuzzle15.moveTile(targetTile));
        }
    }

    @Test
    public void testInitialization() {
        try (MockedStatic<PuzzleUtils> mocked = Mockito.mockStatic(PuzzleUtils.class)) {
            mocked.when(() -> PuzzleUtils.getRandomTilesNumbers(anyInt())).thenReturn(Arrays.asList(1, 2, 0, 3));
            EasyPuzzle15 easyPuzzle15 = new EasyPuzzle15();
            int[][] puzzleBoard = easyPuzzle15.getPuzzleBoard();
            int zeroCount = 0;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    if (puzzleBoard[i][j] == 0) {
                        zeroCount++;
                    }
                }
            }
            // There should be exactly one 0 in the puzzle
            assertEquals(1, zeroCount);
        }
    }
}