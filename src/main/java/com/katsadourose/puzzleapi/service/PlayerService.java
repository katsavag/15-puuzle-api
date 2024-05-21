package com.katsadourose.puzzleapi.service;

import com.katsadourose.puzzleapi.dto.NewPlayerDTO;

public interface PlayerService {

    void createNewPlayer(NewPlayerDTO newPlayerDTO);
}
