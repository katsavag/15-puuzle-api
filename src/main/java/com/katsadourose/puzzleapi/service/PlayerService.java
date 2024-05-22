package com.katsadourose.puzzleapi.service;

import com.katsadourose.puzzleapi.dto.NewPlayerDTO;
import com.katsadourose.puzzleapi.exception.PlayerServiceException;
import com.katsadourose.puzzleapi.model.Player;

public interface PlayerService {

    void createNewPlayer(NewPlayerDTO newPlayerDTO);

    Player getPlayerById(int playerId) throws PlayerServiceException;
}
