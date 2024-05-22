package com.katsadourose.puzzleapi.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public final class Game {

    private final Integer id;
    private final Integer playerId;
    private final AtomicInteger totalMoves;
    private final PuzzleType puzzleType;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Puzzle15 puzzle;

    private Game(GameBuilder builder) {
        this.id = builder.id;
        this.playerId = builder.playerId;
        this.totalMoves = builder.totalMoves;
        this.puzzleType = builder.puzzleType;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.puzzle = builder.puzzle;
    }

    public static class GameBuilder {
        private Integer id;
        private Integer playerId;
        private AtomicInteger totalMoves;
        private PuzzleType puzzleType;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Puzzle15 puzzle;

        public GameBuilder() {}

        public GameBuilder(Game game) {
            this.id = game.getId();
            this.playerId = game.getPlayerId();
            this.totalMoves = game.getTotalMoves();
            this.puzzleType = game.getPuzzleType();
            this.createdAt = game.getCreatedAt();
            this.updatedAt = game.getUpdatedAt();
            this.puzzle = game.getPuzzle();
        }

        public GameBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public GameBuilder setPlayerId(Integer playerId) {
            this.playerId = playerId;
            return this;
        }

        public GameBuilder setTotalMoves(AtomicInteger totalMoves) {
            this.totalMoves = totalMoves;
            return this;
        }

        public GameBuilder setPuzzleType(PuzzleType puzzleType) {
            this.puzzleType = puzzleType;
            return this;
        }

        public GameBuilder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public GameBuilder setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public GameBuilder setPuzzle(Puzzle15 puzzle) {
            this.puzzle = puzzle;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Game{");
        sb.append(String.format("id: %s, ", id));
        sb.append(String.format("playerId: %s, ", playerId));
        sb.append(String.format("totalMoves: %s, ", totalMoves));
        sb.append(String.format("puzzleType: %s, ", puzzleType));
        sb.append(String.format("createdAt: %s, ", createdAt));
        sb.append(String.format("updatedAt: %s, ", updatedAt));
        sb.append(String.format("puzzle: %s", puzzle));
        sb.append("}");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id.equals(game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}