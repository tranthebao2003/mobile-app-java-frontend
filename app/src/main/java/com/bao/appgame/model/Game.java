package com.bao.appgame.model;

import java.math.BigDecimal;

public class Game {
    private Long gameId;
    private String gameName;
    private String gameImg;
    private String description;
    private double price;

    public Game(Long gameId, String gameName, String gameImg, String description, double price) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameImg = gameImg;
        this.description = description;
        this.price = price;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameImg() {
        return gameImg;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}
