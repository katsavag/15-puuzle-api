package com.katsadourose.puzzleapi.controller;

import com.katsadourose.puzzleapi.dto.NewGameDTO;
import com.katsadourose.puzzleapi.model.Game;
import com.katsadourose.puzzleapi.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Game Controller", description = "APIs related to game operations")
@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @Operation(summary = "Create a new game")
    @ApiResponse(responseCode = "201", description = "New Game created")
    @ApiResponse(responseCode = "400", description = "Game Creation Failed")
    @ApiResponse(responseCode = "500", description = "Game Creation Failed due to unexpected error")
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Game> createNewGame(@Valid @RequestBody NewGameDTO newGameDTO) {

        Game createdGame = gameService.createGame(newGameDTO);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }
}
