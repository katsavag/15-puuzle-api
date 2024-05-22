package com.katsadourose.puzzleapi.service.implementation;

import com.katsadourose.puzzleapi.dto.NewPlayerDTO;
import com.katsadourose.puzzleapi.exception.ErrorCode;
import com.katsadourose.puzzleapi.exception.PlayerServiceException;
import com.katsadourose.puzzleapi.exception.ResourceNotFoundException;
import com.katsadourose.puzzleapi.model.Player;
import com.katsadourose.puzzleapi.repository.PlayerRepository;
import com.katsadourose.puzzleapi.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public void createNewPlayer(NewPlayerDTO newPlayerDTO) {
        Player player = new Player.PlayerBuilder()
                .setCreatedAt(LocalDateTime.now())
                .setPlayerName(newPlayerDTO.playerName())
                .build();
        playerRepository.save(player);
    }

    @Override
    public Player getPlayerById(int playerId) {
        try {
            return playerRepository.findById(playerId);
        } catch (ResourceNotFoundException exception) {
            throw new PlayerServiceException(ErrorCode.PLAYER_NOT_FOUND, String.format("Failed to retrieve player: %s", ErrorCode.PLAYER_NOT_FOUND.getMessage()));
        } catch (Exception exception) {
            throw new PlayerServiceException(ErrorCode.INTERNAL_SERVER, String.format("Failed to retrieve player: %s", ErrorCode.INTERNAL_SERVER.getMessage()));
        }
    }
}
