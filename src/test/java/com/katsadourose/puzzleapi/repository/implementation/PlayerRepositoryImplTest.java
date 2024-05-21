package com.katsadourose.puzzleapi.repository.implementation;

import com.katsadourose.puzzleapi.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class PlayerRepositoryImplTest {

    @InjectMocks
    private PlayerRepositoryImpl playerRepository;

    @Test
    public void testSave() {
        Player player = new Player.PlayerBuilder().setPlayerName("player1").build();
        playerRepository.save(player);
        Player savedPlayer = playerRepository.findById(0);
        assertNotNull(savedPlayer);
        assertEquals(0, savedPlayer.getId());
        player = new Player.PlayerBuilder().setPlayerName("player2").build();
        playerRepository.save(player);
        savedPlayer = playerRepository.findById(1);
        assertNotNull(savedPlayer);
        assertEquals(1, savedPlayer.getId());
    }
}