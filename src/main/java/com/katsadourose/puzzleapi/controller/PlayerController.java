package com.katsadourose.puzzleapi.controller;

import com.katsadourose.puzzleapi.dto.NewPlayerDTO;
import com.katsadourose.puzzleapi.model.Player;
import com.katsadourose.puzzleapi.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Player Operations", description = "APIs related to player operations")
@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @Operation(summary = "Create a new player")
    @ApiResponse(responseCode = "201", description = "Player created")
    @ApiResponse(responseCode = "400", description = "Player Creation Failed")
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Player> createNewPlayer(@Valid @RequestBody NewPlayerDTO newPlayerDTO) {
        Player player = playerService.createNewPlayer(newPlayerDTO);
        return new ResponseEntity<>(player, HttpStatus.CREATED);
    }

    @Operation(summary = "Get player by ID")
    @ApiResponse(responseCode = "200", description = "Player retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Player Not Found")
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Player> getPlayerById(@PathVariable Integer id) {

        return new ResponseEntity<>(playerService.getPlayerById(id), HttpStatus.OK);
    }

    @Operation(summary = "Delete player by ID")
    @ApiResponse(responseCode = "204", description = "Player deleted successfully")
    @ApiResponse(responseCode = "404", description = "Player Not Found")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Void> deletePlayer(@PathVariable Integer id) {
        playerService.deletePlayer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
