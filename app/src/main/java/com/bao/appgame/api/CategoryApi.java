package com.bao.appgame.api;

import com.bao.appgame.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryApi {

    @GET("categories")
    Call<List<Category>> getCategoryList();

}
