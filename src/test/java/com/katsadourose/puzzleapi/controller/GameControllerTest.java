package com.katsadourose.puzzleapi.controller;

import com.katsadourose.puzzleapi.dto.NewGameDTO;
import com.katsadourose.puzzleapi.factory.GameFactory;
import com.katsadourose.puzzleapi.model.Game;
import com.katsadourose.puzzleapi.model.PuzzleType;
import com.katsadourose.puzzleapi.model.TilePosition;
import com.katsadourose.puzzleapi.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @Test
    public void testCreateNewGame() throws Exception {
        Game game = GameFactory.createGame(1, PuzzleType.EASY);
        when(gameService.createGame(any(NewGameDTO.class))).thenReturn(game);

        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerId\":1,\"puzzleType\": \"EASY\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testMoveTile() throws Exception {
        Game game = GameFactory.createGame(1, PuzzleType.EASY);
        when(gameService.moveTile(anyInt(), any(TilePosition.class))).thenReturn(game);

        mockMvc.perform(patch("/games/1/puzzle-board/move-tile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"row\":1,\"column\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetGameById() throws Exception {
        Game game = GameFactory.createGame(1, PuzzleType.EASY);
        when(gameService.getGameById(anyInt())).thenReturn(game);

        mockMvc.perform(get("/games/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetGames() throws Exception {
        Game game = GameFactory.createGame(1, PuzzleType.EASY);
        when(gameService.getGames()).thenReturn(Collections.singletonList(game));

        mockMvc.perform(get("/games")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteGame() throws Exception {
        doNothing().when(gameService).deleteGame(anyInt());
        mockMvc.perform(delete("/games/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}