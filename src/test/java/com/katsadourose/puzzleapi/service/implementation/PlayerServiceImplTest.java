package com.katsadourose.puzzleapi.service.implementation;

import com.katsadourose.puzzleapi.dto.NewPlayerDTO;
import com.katsadourose.puzzleapi.model.Player;
import com.katsadourose.puzzleapi.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    public void testCreateNewPlayer() {
        NewPlayerDTO newPlayerDTO = new NewPlayerDTO("john");
        playerService.createNewPlayer(newPlayerDTO);
        verify(playerRepository).save(Mockito.any(Player.class));
    }
}