package com.katsadourose.puzzleapi.service;

import com.katsadourose.puzzleapi.dto.NewGameDTO;
import com.katsadourose.puzzleapi.exception.GameServiceException;
import com.katsadourose.puzzleapi.model.Game;
import com.katsadourose.puzzleapi.model.TilePosition;

import java.util.List;

public interface GameService {

    Game createGame(NewGameDTO newGameDTO) throws GameServiceException;

    Game moveTile(int gameId, TilePosition tilePosition);

    Game getGameById(int id);

    List<Game> getGamesByPlayerId(int playerId);

    List<Game> getGames();

    void deleteGame(int id);
}
