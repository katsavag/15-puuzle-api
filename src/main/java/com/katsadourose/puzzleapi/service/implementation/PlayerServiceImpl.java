package com.katsadourose.puzzleapi.service.implementation;

import com.katsadourose.puzzleapi.dto.NewPlayerDTO;
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
}
