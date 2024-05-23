package com.katsadourose.puzzleapi.controller;

import com.katsadourose.puzzleapi.dto.NewGameDTO;
import com.katsadourose.puzzleapi.model.Game;
import com.katsadourose.puzzleapi.model.TilePosition;
import com.katsadourose.puzzleapi.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Game Operations", description = "APIs related to game operations")
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
    public ResponseEntity<Game> createNewGame(@Valid @RequestBody NewGameDTO newGameDTO) {

        Game createdGame = gameService.createGame(newGameDTO);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }

    @Operation(summary = "Move tile to a new position")
    @ApiResponse(responseCode = "200", description = "Tile is moved successfully")
    @ApiResponse(responseCode = "400", description = "Tile Move Failed")
    @ApiResponse(responseCode = "500", description = "Tile Move Failed due to unexpected error")
    @PatchMapping(path = "/{id}/puzzle-board/move-tile", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Game> moveTile(@PathVariable int id, @RequestBody TilePosition tilePosition) {

        Game updatedGame = gameService.moveTile(id, tilePosition);
        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }

    @Operation(summary = "Get game by id")
    @ApiResponse(responseCode = "200", description = "Get Game by id successfully")
    @ApiResponse(responseCode = "404", description = "Game Not Found")
    @ApiResponse(responseCode = "500", description = "Game Retrieval Failed due to unexpected error")
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Game> getGameById(@PathVariable int id) {
        return new ResponseEntity<>(gameService.getGameById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get game by player id or all games if no player id is provided")
    @ApiResponse(responseCode = "200", description = "Get Games successfully")
    @ApiResponse(responseCode = "500", description = "Games Retrieval Failed due to unexpected error")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Game>> getGames(@RequestParam(required = false) Integer playerId) {
        if (playerId != null) {
            return new ResponseEntity<>(gameService.getGamesByPlayerId(playerId), HttpStatus.OK);
        }
        return new ResponseEntity<>(gameService.getGames(), HttpStatus.OK);
    }

    @Operation(summary = "Delete existing game")
    @ApiResponse(responseCode = "204", description = "Game deleted")
    @ApiResponse(responseCode = "404", description = "Game Not Found")
    @ApiResponse(responseCode = "500", description = "Game Deletion Failed due to unexpected error")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Integer id) {

        gameService.deleteGame(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
