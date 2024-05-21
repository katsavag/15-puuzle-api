package com.katsadourose.puzzleapi.repository;

import com.katsadourose.puzzleapi.model.Player;

public interface PlayerRepository {

    void save(Player player);

    Player findById(int id);
}
