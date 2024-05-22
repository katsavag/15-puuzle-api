package com.katsadourose.puzzleapi.controller;

import com.katsadourose.puzzleapi.controller.PlayerController;
import com.katsadourose.puzzleapi.dto.NewPlayerDTO;
import com.katsadourose.puzzleapi.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    private MockMvc mockMvc;

//    @Test
//    public void testCreateNewPlayer() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
//
//        String playerJson = "{\"playerName\":\"John\"}";
//
//        mockMvc.perform(post("/player")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(playerJson))
//                .andExpect(status().isCreated());
//
//        verify(playerService, times(1)).createNewPlayer(Mockito.any(NewPlayerDTO.class));
//    }
}