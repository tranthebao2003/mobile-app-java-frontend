package com.bao.appgame.response;

import com.bao.appgame.model.Game;

import java.util.List;

public class GamePageRes {
    private List<Game> gameList;
    private int currentPage;

    public List<Game> getGameList() {
        return gameList;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
