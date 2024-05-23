package com.katsadourose.puzzleapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.katsadourose.puzzleapi.dto.ErrorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateNewGame() throws Exception {
        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerName\":\"test\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerId\":0,\"puzzleType\": \"EASY\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateNewGame_PlayerNotFound() throws Exception {
        MvcResult result = mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerId\":100,\"puzzleType\": \"EASY\"}"))
                .andExpect(status().isNotFound())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ErrorDTO errorDTO = mapper.readValue(content, ErrorDTO.class);

        assertEquals(404, errorDTO.status());
        assertEquals("PLAYER_NOT_FOUND", errorDTO.error());
    }

    @Test
    public void testCreateNewGame_MaxGames() throws Exception {
        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerName\":\"test\"}"))
                .andExpect(status().isCreated());

        for (int i = 0; i < 11; i++) {
            mockMvc.perform(post("/games")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"playerId\":0,\"puzzleType\": \"EASY\"}"))
                    .andExpect(status().isCreated());
        }
        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerId\":0,\"puzzleType\": \"EASY\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetGameById() throws Exception {
        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerName\":\"test\"}"))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerId\":0,\"puzzleType\": \"EASY\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/games/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetGameById_NotFound() throws Exception {
        mockMvc.perform(get("/games/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetGames() throws Exception {
        mockMvc.perform(get("/games")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteGame() throws Exception {
        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerName\":\"test\"}"))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerId\":0,\"puzzleType\": \"EASY\"}"))
                .andExpect(status().isCreated());
        mockMvc.perform(delete("/games/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}