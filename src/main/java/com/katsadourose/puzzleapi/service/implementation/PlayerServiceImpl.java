package com.katsadourose.puzzleapi.service.implementation;

import com.katsadourose.puzzleapi.dto.NewPlayerDTO;
import com.katsadourose.puzzleapi.exception.ErrorCode;
import com.katsadourose.puzzleapi.exception.InvalidParameterException;
import com.katsadourose.puzzleapi.exception.PlayerServiceException;
import com.katsadourose.puzzleapi.exception.ResourceNotFoundException;
import com.katsadourose.puzzleapi.model.Game;
import com.katsadourose.puzzleapi.model.Player;
import com.katsadourose.puzzleapi.repository.GameRepository;
import com.katsadourose.puzzleapi.repository.PlayerRepository;
import com.katsadourose.puzzleapi.service.GameService;
import com.katsadourose.puzzleapi.service.PlayerService;
import com.katsadourose.puzzleapi.transaction_engine.TransactionManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    private void checkIfPlayNameExists(String playerName) {
        if (playerRepository.findPlayerByName(playerName).isPresent()) {
            throw new InvalidParameterException(String.format("Player with name: %s already exists", playerName));
        }
    }

    @Override
    public Player createNewPlayer(NewPlayerDTO newPlayerDTO) {
        TransactionManager transactionManager = new TransactionManager();
        try {
            checkIfPlayNameExists(newPlayerDTO.playerName());
            Player player = new Player.PlayerBuilder()
                    .setCreatedAt(LocalDateTime.now())
                    .setPlayerName(newPlayerDTO.playerName())
                    .build();
            player = playerRepository.save(player, transactionManager);
            return player;
        } catch (InvalidParameterException exception) {
            log.error("Failed to create player", exception);
            throw new PlayerServiceException(ErrorCode.PLAYER_ALREADY_EXISTS, String.format("Failed to create player: %s", ErrorCode.PLAYER_ALREADY_EXISTS.getMessage()));
        } catch (Exception exception) {
            log.error("Failed to create player", exception);
            transactionManager.rollback();
            throw new PlayerServiceException(ErrorCode.INTERNAL_SERVER, String.format("Failed to create player: %s", ErrorCode.INTERNAL_SERVER.getMessage()));
        }
    }

    @Override
    public void deletePlayer(int playerId) {
        TransactionManager transactionManager = new TransactionManager();
        try {
            playerRepository.findById(playerId)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Player with id: %d not found", playerId)));
            List<Game> playerGames = gameRepository.findGamesByPlayerId(playerId);

            for (Game game : playerGames) {
                gameRepository.deleteGame(game.getId(), transactionManager);
            }

            playerRepository.delete(playerId, transactionManager);
        } catch (ResourceNotFoundException exception) {
            log.error("Failed to delete player", exception);
            throw new PlayerServiceException(ErrorCode.PLAYER_NOT_FOUND, String.format("Failed to retrieve player: %s", ErrorCode.PLAYER_NOT_FOUND.getMessage()));
        } catch (Exception exception) {
            log.error("Failed to delete player", exception);
            transactionManager.rollback();
            throw new PlayerServiceException(ErrorCode.INTERNAL_SERVER, String.format("Failed to delete player: %s", ErrorCode.INTERNAL_SERVER.getMessage()));
        }
    }

    @Override
    public Player getPlayerById(int playerId) {
        try {
            return playerRepository.findById(playerId)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Player with id: %d not found", playerId)));
        } catch (ResourceNotFoundException exception) {
            throw new PlayerServiceException(ErrorCode.PLAYER_NOT_FOUND, String.format("Failed to retrieve player: %s", ErrorCode.PLAYER_NOT_FOUND.getMessage()));
        } catch (Exception exception) {
            throw new PlayerServiceException(ErrorCode.INTERNAL_SERVER, String.format("Failed to retrieve player: %s", ErrorCode.INTERNAL_SERVER.getMessage()));
        }
    }
}
