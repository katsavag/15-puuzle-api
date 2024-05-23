package com.katsadourose.puzzleapi.repository.implementation;

import com.katsadourose.puzzleapi.model.Game;
import com.katsadourose.puzzleapi.repository.GameRepository;
import com.katsadourose.puzzleapi.transaction_engine.DeleteCommand;
import com.katsadourose.puzzleapi.transaction_engine.SaveCommand;
import com.katsadourose.puzzleapi.transaction_engine.TransactionManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public final class GameRepositoryImpl implements GameRepository {
    private final ConcurrentHashMap<Integer, Game> gamesStorage = new ConcurrentHashMap<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    @Override
    public void saveGame(Game game, TransactionManager transactionManager) {
        Integer id = idSequence.getAndIncrement();
        Game newGame = new Game.GameBuilder(game)
                .setId(id)
                .build();
        transactionManager.executeCommand(new SaveCommand<>(gamesStorage, newGame, id));
    }

    @Override
    public void deleteGame(int id, TransactionManager transactionManager) {
        transactionManager.executeCommand(new DeleteCommand<>(gamesStorage, id));
    }

    @Override
    public Optional<Game> findGameById(int id) {
        if (!gamesStorage.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.of(gamesStorage.get(id));
    }

    @Override
    public List<Game> findGamesByPlayerId(int playerId) {
        return gamesStorage
                .values()
                .stream()
                .filter(game -> game.getPlayerId().equals(playerId))
                .toList();
    }

    @Override
    public List<Game> findAllGames() {
        return gamesStorage
                .values()
                .stream()
                .toList();
    }
}
