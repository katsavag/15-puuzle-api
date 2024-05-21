package com.katsadourose.puzzleapi.model;

import com.katsadourose.puzzleapi.PuzzleUtils;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Getter
public abstract class Puzzle15 {
    private final int[][] puzzleBoard;
    private final Random random = new Random();

    public Puzzle15(int[][] puzzleBoard) {
        this.puzzleBoard = puzzleBoard;
        initialize();
    }

    private void initialize() {
        List<Integer> randomTilesNumbers = PuzzleUtils.getRandomTilesNumbers();
        Iterator<Integer> tilesIterator = randomTilesNumbers.iterator();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                puzzleBoard[i][j] = tilesIterator.next();
            }
        }
    }

    private TilePosition findZeroTilePosition() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (puzzleBoard[i][j] == 0) {
                    return new TilePosition(i, j);
                }
            }
        }
        throw new IllegalStateException("Zero tile not found");
    }

    private  void isTileMovementValid(TilePosition currentTile, TilePosition zeroTile) {
        if (Math.abs(currentTile.row() - zeroTile.row()) + Math.abs(currentTile.col() - zeroTile.col()) != 1) {
            throw new IllegalArgumentException("Invalid move");
        }
    }

    public synchronized void moveTile(TilePosition targetTile) {

        TilePosition zeroTilePosition = findZeroTilePosition();

        isTileMovementValid(targetTile, zeroTilePosition);

        swap(targetTile, zeroTilePosition);
    }

    private void swap(TilePosition currentTile, TilePosition zeroTile) {
        int temp = puzzleBoard[currentTile.row()][currentTile.col()];
        puzzleBoard[currentTile.row()][currentTile.col()] = puzzleBoard[zeroTile.row()][zeroTile.col()];
        puzzleBoard[zeroTile.row()][zeroTile.col()] = temp;
    }
}
