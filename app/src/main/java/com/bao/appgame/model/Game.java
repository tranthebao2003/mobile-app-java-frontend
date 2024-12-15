package com.bao.appgame.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

// bắt buộc phải có Serializable thì mới gửi qua
// bằng intent được
public class Game implements Serializable {
    private Long gameId;
    private String gameName;
    private String gameImg;
    private String description;

    // vì thuộc tính model này là gamePrice khác với key của json trả về là price
    // nên phải dùng thêm @SerializedName("keyOfJson")
    @SerializedName("price")
    private double gamePrice;

    public Game(Long gameId, String gameName, String gameImg, String description, double gamePrice) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameImg = gameImg;
        this.description = description;
        this.gamePrice = gamePrice;
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

    public double getGamePrice() {
        return gamePrice;
    }
}
