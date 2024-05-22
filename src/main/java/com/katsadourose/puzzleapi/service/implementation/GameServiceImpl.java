package com.katsadourose.puzzleapi.service.implementation;

import com.katsadourose.puzzleapi.dto.NewGameDTO;
import com.katsadourose.puzzleapi.exception.*;
import com.katsadourose.puzzleapi.factory.GameFactory;
import com.katsadourose.puzzleapi.model.Game;
import com.katsadourose.puzzleapi.repository.GameRepository;
import com.katsadourose.puzzleapi.repository.PlayerRepository;
import com.katsadourose.puzzleapi.service.GameService;
import com.katsadourose.puzzleapi.service.PlayerService;
import com.katsadourose.puzzleapi.transaction_engine.TransactionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;


    private int countPlayerTotalGames(int playerId) {
        List<Game> playerGames = gameRepository.findGamesByPlayerId(playerId);
        return playerGames.size();
    }

    private void validateGameCreation(NewGameDTO newGameDTO) {
        playerService.getPlayerById(newGameDTO.playerId());
        if (countPlayerTotalGames(newGameDTO.playerId()) > 10) {
            throw new MaxResourceEntriesException("Player has reached the maximum number of games");
        }
    }
    @Override
    public Game createGame(NewGameDTO newGameDTO) {
        TransactionManager transactionManager = new TransactionManager();
        try {
            validateGameCreation(newGameDTO);
            Game game = GameFactory.createGame(newGameDTO.playerId(), newGameDTO.puzzleType());
            gameRepository.saveGame(game, transactionManager);
            return game;
        } catch (MaxResourceEntriesException exception) {
            log.error(exception.getMessage());
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.MAX_GAMES_ENTRIES, String.format("Failed to create new game: %s", ErrorCode.MAX_GAMES_ENTRIES.getMessage()));
        } catch (PlayerServiceException exception) {
            log.error(exception.getMessage());
            transactionManager.rollback();
            throw new GameServiceException(exception.getErrorCode(), String.format("Failed to create new game: %s", exception.getErrorCode().getMessage()));
        } catch (Exception exception) {
            log.error(exception.getMessage());
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.INTERNAL_SERVER, String.format("Failed to create new game: %s", ErrorCode.INTERNAL_SERVER.getMessage()));
        }
    }
}
