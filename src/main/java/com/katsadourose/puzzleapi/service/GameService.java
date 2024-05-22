package com.katsadourose.puzzleapi.service;

import com.katsadourose.puzzleapi.dto.NewGameDTO;
import com.katsadourose.puzzleapi.exception.GameServiceException;
import com.katsadourose.puzzleapi.model.Game;

public interface GameService {

    Game createGame(NewGameDTO newGameDTO) throws GameServiceException;

    void deleteGame(int id);
}
