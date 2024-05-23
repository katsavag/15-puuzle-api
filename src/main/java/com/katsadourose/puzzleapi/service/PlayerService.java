package com.katsadourose.puzzleapi.service;

import com.katsadourose.puzzleapi.dto.NewPlayerDTO;
import com.katsadourose.puzzleapi.exception.PlayerServiceException;
import com.katsadourose.puzzleapi.model.Player;

public interface PlayerService {

    Player createNewPlayer(NewPlayerDTO newPlayerDTO);

    void deletePlayer(int playerId);

    Player getPlayerById(int playerId) throws PlayerServiceException;
}
