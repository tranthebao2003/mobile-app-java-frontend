package com.bao.appgame.model;

public class Category {
    private Long categoryId;
    private String categoryName;
    private String categoryImg;

    public Category(Long categoryId, String categoryName, String categoryImg) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImg = categoryImg;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }


    public String getCategoryImg() {
        return categoryImg;
    }
}
