package com.katsadourose.puzzleapi.repository.implementation;

import com.katsadourose.puzzleapi.exception.ResourceNotFoundException;
import com.katsadourose.puzzleapi.model.Player;
import com.katsadourose.puzzleapi.repository.PlayerRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public final class PlayerRepositoryImpl implements PlayerRepository {

    private final Map<Integer, Player> playersStorage = new ConcurrentHashMap<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    private void checkPlayerExists(int id) {
        if (!playersStorage.containsKey(id)) {
            throw new ResourceNotFoundException("Player with id " + id + " not found");
        }
    }

    @Override
    public void save(Player player) {
        Player newPlayer = new Player
                .PlayerBuilder(player)
                .setId(idSequence.getAndIncrement())
                .build();
        playersStorage.put(newPlayer.getId(), newPlayer);
    }

    @Override
    public Player findById(int id) {
        checkPlayerExists(id);
        return playersStorage.get(id);
    }
}
