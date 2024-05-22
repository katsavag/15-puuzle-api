package com.katsadourose.puzzleapi.repository;

import com.katsadourose.puzzleapi.model.Game;
import com.katsadourose.puzzleapi.transaction_engine.TransactionManager;

import java.util.List;

public interface GameRepository {
    void saveGame(Game game, TransactionManager transactionManager);

    void deleteGame(int id, TransactionManager transactionManager);

    Game findGameById(int id);

    List<Game> findGamesByPlayerId(int playerId);
}
