package com.katsadourose.puzzleapi.repository;

import com.katsadourose.puzzleapi.exception.ResourceNotFoundException;
import com.katsadourose.puzzleapi.model.Player;
import com.katsadourose.puzzleapi.transaction_engine.TransactionManager;

import java.util.Optional;

public interface PlayerRepository {

    Player save(Player player, TransactionManager transactionManager);

    Optional<Player> findById(int id) throws ResourceNotFoundException;

    Optional<Player> findPlayerByName(String playerName);

    void delete(int id, TransactionManager transactionManager);
}
