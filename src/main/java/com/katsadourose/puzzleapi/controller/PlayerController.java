package com.katsadourose.puzzleapi.controller;

import com.katsadourose.puzzleapi.dto.NewPlayerDTO;
import com.katsadourose.puzzleapi.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Player Controller", description = "APIs related to player operations")
@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @Operation(summary = "Create a new player")
    @ApiResponse(responseCode = "201", description = "Player created")
    @ApiResponse(responseCode = "400", description = "Player Creation Failed")
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewPlayer(@Valid @RequestBody NewPlayerDTO newPlayerDTO) {
        playerService.createNewPlayer(newPlayerDTO);
    }



}