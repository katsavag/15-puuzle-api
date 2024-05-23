package com.katsadourose.puzzleapi.model;

import com.katsadourose.puzzleapi.PuzzleUtils;
import com.katsadourose.puzzleapi.exception.InvalidPuzzleMoveException;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;

@Getter
public abstract class Puzzle15 {
    private final int[][] puzzleBoard;
    private int totalMoves = 0;
    private PuzzleStatus status = PuzzleStatus.OPEN;

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
        throw new InvalidPuzzleMoveException("Zero tile not found");
    }

    private  void isTileMovementValid(TilePosition currentTile, TilePosition zeroTile) {
        if (Math.abs(currentTile.row() - zeroTile.row()) + Math.abs(currentTile.col() - zeroTile.col()) != 1) {
            throw new InvalidPuzzleMoveException("Selected tile is not adjacent to the zero tile");
        }
    }

    public synchronized void moveTile(TilePosition targetTile) {

        TilePosition zeroTilePosition = findZeroTilePosition();

        isTileMovementValid(targetTile, zeroTilePosition);

        swap(targetTile, zeroTilePosition);

        totalMoves++;
    }

    private void swap(TilePosition currentTile, TilePosition zeroTile) {
        int temp = puzzleBoard[currentTile.row()][currentTile.col()];
        puzzleBoard[currentTile.row()][currentTile.col()] = puzzleBoard[zeroTile.row()][zeroTile.col()];
        puzzleBoard[zeroTile.row()][zeroTile.col()] = temp;
    }
}
