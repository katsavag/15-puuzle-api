package com.katsadourose.puzzleapi.service.implementation;

import com.katsadourose.puzzleapi.dto.NewGameDTO;
import com.katsadourose.puzzleapi.exception.*;
import com.katsadourose.puzzleapi.factory.GameFactory;
import com.katsadourose.puzzleapi.model.*;
import com.katsadourose.puzzleapi.repository.implementation.GameRepositoryImpl;
import com.katsadourose.puzzleapi.service.PlayerService;
import com.katsadourose.puzzleapi.transaction_engine.TransactionManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    @Mock
    private GameRepositoryImpl gameRepository;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    public void testCreateGame_Success() {
        NewGameDTO newGameDTO = new NewGameDTO(1, PuzzleType.EASY);
        when(gameRepository.findGamesByPlayerId(anyInt())).thenReturn(Collections.emptyList());

        Game game = gameService.createGame(newGameDTO);

        assertNotNull(game);
        verify(gameRepository, times(1)).saveGame(any(Game.class), any(TransactionManager.class));
    }

    @Test
    public void testCreateGame_MaxGamesReached() {
        List<Game> games = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            NewGameDTO newGameDTO = new NewGameDTO(1, PuzzleType.EASY);
            games.add(gameService.createGame(newGameDTO));
        }
        when(gameRepository.findGamesByPlayerId(anyInt())).thenReturn(games);
        NewGameDTO newGameDTO = new NewGameDTO(1, PuzzleType.EASY);
        GameServiceException exception = assertThrows(GameServiceException.class, () -> gameService.createGame(newGameDTO));
        assertEquals(String.format("Failed to create new game: %s", ErrorCode.MAX_GAMES_ENTRIES.getMessage()), exception.getMessage());
        assertEquals("MAX_GAMES_ENTRIES", exception.getErrorCode().name());
    }

    @Test
    public void testCreateGame_ExceptionThrown() {
        NewGameDTO newGameDTO = new NewGameDTO(1, PuzzleType.EASY);
        when(gameRepository.findGamesByPlayerId(anyInt())).thenReturn(Collections.emptyList());
        doThrow(RuntimeException.class).when(gameRepository).saveGame(any(Game.class), any(TransactionManager.class));

        GameServiceException exception = assertThrows(GameServiceException.class, () -> gameService.createGame(newGameDTO));
        assertEquals(String.format("Failed to create new game: %s", ErrorCode.INTERNAL_SERVER.getMessage()), exception.getMessage());
        assertEquals("INTERNAL_SERVER", exception.getErrorCode().name());
    }

    @Test
    public void testCreateGame_PlayerNotFound() {
        NewGameDTO newGameDTO = new NewGameDTO(1, PuzzleType.EASY);
        when(playerService.getPlayerById(anyInt())).thenThrow(new PlayerServiceException(ErrorCode.PLAYER_NOT_FOUND, String.format("Failed to retrieve player: %s", ErrorCode.PLAYER_NOT_FOUND.getMessage())));

        GameServiceException exception = assertThrows(GameServiceException.class, () -> gameService.createGame(newGameDTO));
        assertEquals(String.format("Failed to create new game: %s", ErrorCode.PLAYER_NOT_FOUND.getMessage()), exception.getMessage());
        assertEquals("PLAYER_NOT_FOUND", exception.getErrorCode().name());
    }

    @Test
    public void testMoveTile_GameNotFound() {
        when(gameRepository.findGameById(anyInt())).thenReturn(Optional.empty());

        GameServiceException exception = assertThrows(GameServiceException.class, () -> gameService.moveTile(1, new TilePosition(0, 0)));
        assertEquals(ErrorCode.GAME_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    public void testMoveTile_InvalidMove() {
        Game mockGame = mock(Game.class);
        when(mockGame.isCompleted()).thenReturn(true);
        when(gameRepository.findGameById(anyInt())).thenReturn(Optional.of(mockGame));

        GameServiceException exception = assertThrows(GameServiceException.class, () -> gameService.moveTile(1, new TilePosition(0, 0)));
        assertEquals(ErrorCode.INVALID_PUZZLE_MOVE, exception.getErrorCode());
    }

    @Test
    public void testMoveTile_ExceptionThrown() {
        doThrow(RuntimeException.class).when(gameRepository).findGameById(anyInt());

        GameServiceException exception = assertThrows(GameServiceException.class, () -> gameService.moveTile(1, new TilePosition(0, 0)));
        assertEquals(ErrorCode.INTERNAL_SERVER, exception.getErrorCode());
    }

    @Test
    public void testDeleteGame_Success() {
        when(gameRepository.findGameById(anyInt())).thenReturn(Optional.of(GameFactory.createGame(1, PuzzleType.EASY)));

        gameService.deleteGame(1);

        verify(gameRepository, times(1)).deleteGame(anyInt(), any(TransactionManager.class));
    }

    @Test
    public void testDeleteGame_GameNotFound() {
        when(gameRepository.findGameById(anyInt())).thenReturn(Optional.empty());

        GameServiceException exception = assertThrows(GameServiceException.class, () -> gameService.deleteGame(1));
        assertEquals(ErrorCode.GAME_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    public void testDeleteGame_ExceptionThrown() {
        when(gameRepository.findGameById(anyInt())).thenReturn(Optional.of(GameFactory.createGame(1, PuzzleType.EASY)));
        doThrow(RuntimeException.class).when(gameRepository).deleteGame(anyInt(), any(TransactionManager.class));

        GameServiceException exception = assertThrows(GameServiceException.class, () -> gameService.deleteGame(1));
        assertEquals(ErrorCode.INTERNAL_SERVER, exception.getErrorCode());
    }
}