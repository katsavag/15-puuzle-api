package com.katsadourose.puzzleapi.repository.implementation;

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
        return playersStorage.get(id);
    }
}
