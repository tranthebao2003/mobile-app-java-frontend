package com.bao.appgame.model;

public class ItemGameBuy {
    private int img;
    private String gamename;
    private String account;
    private String pass;

    public ItemGameBuy(int img, String gamename, String account, String pass) {
        this.img = img;
        this.gamename = gamename;
        this.account = account;
        this.pass = pass;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String game) {
        this.gamename = game;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
