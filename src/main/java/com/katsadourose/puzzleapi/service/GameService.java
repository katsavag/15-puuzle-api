package com.katsadourose.puzzleapi.service;

import com.katsadourose.puzzleapi.dto.NewGameDTO;
import com.katsadourose.puzzleapi.exception.GameServiceException;
import com.katsadourose.puzzleapi.model.Game;
import com.katsadourose.puzzleapi.model.TilePosition;

public interface GameService {

    Game createGame(NewGameDTO newGameDTO) throws GameServiceException;

    Game moveTile(int gameId, TilePosition tilePosition);

    void deleteGame(int id);
}
