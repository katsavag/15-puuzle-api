package com.katsadourose.puzzleapi.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class Player extends BaseModel {

    private final Integer id;
    private final String playerName;

    private Player(LocalDateTime createdAt, LocalDateTime updatedAt, Integer id, String playerName) {
        super(createdAt, updatedAt);
        this.id = id;
        this.playerName = playerName;
    }

    public static class PlayerBuilder {
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Integer id;
        private String playerName;

        public PlayerBuilder() { }
        public PlayerBuilder(Player player) {
            this.createdAt = player.getCreatedAt();
            this.updatedAt = player.getUpdatedAt();
            this.id = player.getId();
            this.playerName = player.getPlayerName();
        }

        public PlayerBuilder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PlayerBuilder setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PlayerBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public PlayerBuilder setPlayerName(String playerName) {
            this.playerName = playerName;
            return this;
        }

        public Player build() {
            return new Player(
                    this.createdAt,
                    this.updatedAt,
                    this.id,
                    this.playerName
            );
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player{");
        sb.append(String.format("createdAt: %s", getCreatedAt()));
        sb.append(String.format("updatedAt: %s", getUpdatedAt()));
        sb.append(String.format("id: %s", id));
        sb.append(String.format("playerName: %s", playerName));
        sb.append("}");

        return sb.toString();
    }
}
