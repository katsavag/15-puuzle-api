package com.katsadourose.puzzleapi.repository.implementation;

import com.katsadourose.puzzleapi.model.Player;
import com.katsadourose.puzzleapi.transaction_engine.TransactionManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class PlayerRepositoryImplTest {

    @InjectMocks
    private PlayerRepositoryImpl playerRepository;

    @Test
    public void testSave() {
        Player player = new Player.PlayerBuilder().setPlayerName("player1").build();
        TransactionManager transactionManager = new TransactionManager();
        player = playerRepository.save(player, transactionManager);
        assertNotNull(player);
        assertEquals(0, player.getId());
        transactionManager = new TransactionManager();
        player = new Player.PlayerBuilder().setPlayerName("player2").build();
        player = playerRepository.save(player, transactionManager);
        assertNotNull(player);
        assertEquals(1, player.getId());
    }

    @Test
    public void testFindById() {
        TransactionManager transactionManager = new TransactionManager();
        Player player = new Player.PlayerBuilder().setPlayerName("player1").build();
        player = playerRepository.save(player, transactionManager);

        Optional<Player> foundPlayer = playerRepository.findById(player.getId());

        assertTrue(foundPlayer.isPresent());
        assertEquals(player.getId(), foundPlayer.get().getId());
    }

    @Test
    public void testFindById_NotFound() {
        Optional<Player> foundPlayer = playerRepository.findById(999);
        assertFalse(foundPlayer.isPresent());
    }

    @Test
    public void testDelete() {
        TransactionManager transactionManager = new TransactionManager();
        Player player = new Player.PlayerBuilder().setPlayerName("player1").build();
        player = playerRepository.save(player, transactionManager);
        playerRepository.delete(player.getId(), transactionManager);
        Optional<Player> foundPlayer = playerRepository.findById(player.getId());
        assertFalse(foundPlayer.isPresent());
    }
}