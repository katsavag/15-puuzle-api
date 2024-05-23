package com.katsadourose.puzzleapi.service.implementation;

import com.katsadourose.puzzleapi.dto.NewPlayerDTO;
import com.katsadourose.puzzleapi.model.Player;
import com.katsadourose.puzzleapi.repository.PlayerRepository;
import com.katsadourose.puzzleapi.service.GameService;
import com.katsadourose.puzzleapi.transaction_engine.TransactionManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    public void testCreateNewPlayer_Success() {
        NewPlayerDTO newPlayerDTO = new NewPlayerDTO("john");
        playerService.createNewPlayer(newPlayerDTO);
        verify(playerRepository, times(1)).save(any(Player.class), any(TransactionManager.class));
    }
}