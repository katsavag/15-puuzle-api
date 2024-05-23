package com.katsadourose.puzzleapi.repository.implementation;

import com.katsadourose.puzzleapi.model.Player;
import com.katsadourose.puzzleapi.repository.PlayerRepository;
import com.katsadourose.puzzleapi.transaction_engine.SaveCommand;
import com.katsadourose.puzzleapi.transaction_engine.TransactionCommand;
import com.katsadourose.puzzleapi.transaction_engine.TransactionManager;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public final class PlayerRepositoryImpl implements PlayerRepository {

    private final ConcurrentHashMap<Integer, Player> playersStorage = new ConcurrentHashMap<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    @Override
    public Player save(Player player, TransactionManager transactionManager) {
        Player newPlayer = new Player
                .PlayerBuilder(player)
                .setId(idSequence.getAndIncrement())
                .build();
        TransactionCommand saveCommand = new SaveCommand<>(playersStorage, newPlayer, newPlayer.getId());
        transactionManager.executeCommand(saveCommand);
        return newPlayer;
    }

    @Override
    public Optional<Player> findById(int id) {
        if (!playersStorage.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.of(playersStorage.get(id));
    }

    @Override
    public Optional<Player> findPlayerByName(String playerName) {
        return playersStorage.values().stream()
                .filter(player -> player.getPlayerName().equals(playerName))
                .findFirst();
    }

    @Override
    public void delete(int id, TransactionManager transactionManager) {
        playersStorage.remove(id);
    }
}
