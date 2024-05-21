package com.katsadourose.puzzleapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class PuzzleUtils {

    public static List<Integer> getRandomTilesNumbers() {
        List<Integer> randomTilesNumbers = new ArrayList<>(IntStream.rangeClosed(0, 3).boxed().toList());
        Collections.shuffle(randomTilesNumbers);
        return randomTilesNumbers;
    }
}
