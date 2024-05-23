package com.katsadourose.puzzleapi.transaction_engine;

public interface TransactionCommand {
    void execute();
    void undo();
}
