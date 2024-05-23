package com.katsadourose.puzzleapi.repository;

import com.katsadourose.puzzleapi.model.Game;
import com.katsadourose.puzzleapi.transaction_engine.TransactionManager;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
    Game saveGame(Game game, TransactionManager transactionManager);

    void deleteGame(int id, TransactionManager transactionManager);

    Optional<Game> findGameById(int id);

    List<Game> findGamesByPlayerId(int playerId);

    List<Game> findAllGames();
}
