package com.katsadourose.puzzleapi.repository.implementation;

import com.katsadourose.puzzleapi.exception.ResourceNotFoundException;
import com.katsadourose.puzzleapi.model.Game;
import com.katsadourose.puzzleapi.transaction_engine.TransactionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameRepositoryImplTest {
    private GameRepositoryImpl gameRepository;
    private TransactionManager transactionManager;

    @BeforeEach
    void setUp() {
        gameRepository = new GameRepositoryImpl();
        transactionManager = new TransactionManager();
    }

    @Test
    void testSaveGame() {
        Game game = new Game.GameBuilder().setId(0).setPlayerId(1).build();
        gameRepository.saveGame(game, transactionManager);
        Game savedGame = gameRepository.findGameById(0).get();
        assertEquals(game, savedGame);
    }

    @Test
    void testDeleteGame_Success() {
        Game game = new Game.GameBuilder().setId(1).setPlayerId(1).build();
        gameRepository.saveGame(game, transactionManager);
        gameRepository.deleteGame(1, transactionManager);
        Optional<Game> deletedGame = gameRepository.findGameById(1);
        assert (deletedGame.isEmpty());
    }

    @Test
    void testFindGameById() {
        Game game = new Game.GameBuilder().setId(0).setPlayerId(1).build();
        gameRepository.saveGame(game, transactionManager);
        Game foundGame = gameRepository.findGameById(0).get();
        assertEquals(game, foundGame);
    }

    @Test
    void testFindGamesByPlayerId() {
        Game game1 = new Game.GameBuilder().setId(0).setPlayerId(1).build();
        Game game2 = new Game.GameBuilder().setId(1).setPlayerId(1).build();
        gameRepository.saveGame(game1, transactionManager);
        gameRepository.saveGame(game2, transactionManager);
        List<Game> games = gameRepository.findGamesByPlayerId(1);
        assertTrue(games.contains(game1));
        assertTrue(games.contains(game2));
    }

    @Test
    void testFindGamesByPlayerId_NoResults() {
        List<Game> games = gameRepository.findGamesByPlayerId(1);
        assertEquals(0, games.size());
    }
}