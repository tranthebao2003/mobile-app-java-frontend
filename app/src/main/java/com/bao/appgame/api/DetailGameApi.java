package com.bao.appgame.api;

import com.bao.appgame.response.ReviewScore;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DetailGameApi {
    @GET("game/{gameId}")
    Call<ReviewScore> getReviewScoreByGameId(@Path("gameId") Long gameId);

}
