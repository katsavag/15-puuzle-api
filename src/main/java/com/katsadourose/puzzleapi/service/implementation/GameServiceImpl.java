package com.katsadourose.puzzleapi.service.implementation;

import com.katsadourose.puzzleapi.dto.NewGameDTO;
import com.katsadourose.puzzleapi.exception.*;
import com.katsadourose.puzzleapi.factory.GameFactory;
import com.katsadourose.puzzleapi.model.Game;
import com.katsadourose.puzzleapi.model.TilePosition;
import com.katsadourose.puzzleapi.repository.GameRepository;
import com.katsadourose.puzzleapi.repository.PlayerRepository;
import com.katsadourose.puzzleapi.service.GameService;
import com.katsadourose.puzzleapi.service.PlayerService;
import com.katsadourose.puzzleapi.transaction_engine.TransactionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;


    private int countPlayerTotalGames(int playerId) {
        List<Game> playerGames = gameRepository.findGamesByPlayerId(playerId);
        return playerGames.size();
    }

    private void validateGameCreation(NewGameDTO newGameDTO) {
        playerRepository.findById(newGameDTO.playerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player with id " + newGameDTO.playerId() + " not found"));
        if (countPlayerTotalGames(newGameDTO.playerId()) > 10) {
            throw new MaxResourceEntriesException("Player has reached the maximum number of games");
        }
    }

    private void checkGameExists(int id) {
        if (gameRepository.findGameById(id).isEmpty()) {
            throw new ResourceNotFoundException("Game with id " + id + " not found");
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
            log.error(exception.getMessage(), exception);
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.MAX_GAMES_ENTRIES, String.format("Failed to create new game: %s", ErrorCode.MAX_GAMES_ENTRIES.getMessage()));
        } catch (ResourceNotFoundException exception) {
            log.error(exception.getMessage(), exception);
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.PLAYER_NOT_FOUND, String.format("Failed to create new game: %s", ErrorCode.PLAYER_NOT_FOUND.getMessage()));
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.INTERNAL_SERVER, String.format("Failed to create new game: %s", ErrorCode.INTERNAL_SERVER.getMessage()));
        }
    }

    private void validateGameMove(int gameId) {
        checkGameExists(gameId);
        Game game = gameRepository.findGameById(gameId).get();
        if (game.isCompleted()) {
            throw new InvalidPuzzleMoveException("Game is already completed");
        }
    }
    @Override
    public Game moveTile(int gameId, TilePosition tilePosition) {
        TransactionManager transactionManager = new TransactionManager();
        try {
            validateGameMove(gameId);
            Game game = gameRepository.findGameById(gameId).get();
            game.getPuzzle().moveTile(tilePosition);
            gameRepository.saveGame(game, transactionManager);
            return game;
        } catch (ResourceNotFoundException exception) {
            log.error(exception.getMessage(), exception);
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.GAME_NOT_FOUND, String.format("Failed to move tile: %s", ErrorCode.GAME_NOT_FOUND.getMessage()));
        } catch (InvalidPuzzleMoveException exception) {
            log.error(exception.getMessage(), exception);
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.INVALID_PUZZLE_MOVE, String.format("Failed to move tile: %s", ErrorCode.INVALID_PUZZLE_MOVE.getMessage()));
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.INTERNAL_SERVER, String.format("Failed to move tile: %s", ErrorCode.INTERNAL_SERVER.getMessage()));
        }
    }

    @Override
    public Game getGameById(int id) {
        TransactionManager transactionManager = new TransactionManager();
        try {
            Optional<Game> optionalGame = gameRepository.findGameById(id);
            if (optionalGame.isEmpty()) {
                throw new ResourceNotFoundException("Game with id " + id + " not found");
            }
            return optionalGame.get();
        } catch (ResourceNotFoundException exception) {
            log.error(exception.getMessage(), exception);
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.GAME_NOT_FOUND, String.format("Failed to retrieve game: %s", ErrorCode.GAME_NOT_FOUND.getMessage()));
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.INTERNAL_SERVER, String.format("Failed to retrieve game: %s", ErrorCode.INTERNAL_SERVER.getMessage()));
        }
    }

    @Override
    public List<Game> getGamesByPlayerId(int playerId) {
        TransactionManager transactionManager = new TransactionManager();
        try {
            return gameRepository.findGamesByPlayerId(playerId);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.INTERNAL_SERVER, String.format("Failed to retrieve games: %s", ErrorCode.INTERNAL_SERVER.getMessage()));
        }
    }

    @Override
    public List<Game> getGames() {
        TransactionManager transactionManager = new TransactionManager();
        try {
            return gameRepository.findAllGames();
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.INTERNAL_SERVER, String.format("Failed to retrieve games: %s", ErrorCode.INTERNAL_SERVER.getMessage()));
        }
    }

    @Override
    public void deleteGame(int id) {
        TransactionManager transactionManager = new TransactionManager();
        deleteOperation(id, transactionManager);
    }

    private void deleteOperation(int id, TransactionManager transactionManager) {
        try {
            checkGameExists(id);
            gameRepository.deleteGame(id, transactionManager);
        } catch (ResourceNotFoundException exception) {
            log.error(exception.getMessage(), exception);
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.GAME_NOT_FOUND, String.format("Failed to delete game: %s", ErrorCode.GAME_NOT_FOUND.getMessage()));
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            transactionManager.rollback();
            throw new GameServiceException(ErrorCode.INTERNAL_SERVER, String.format("Failed to delete game: %s", ErrorCode.INTERNAL_SERVER.getMessage()));
        }
    }
}
