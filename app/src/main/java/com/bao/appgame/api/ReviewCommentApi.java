package com.bao.appgame.api;

import com.bao.appgame.response.ReviewCommentRes;
import com.bao.appgame.response.ReviewScore;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReviewCommentApi {
    @GET("review/{gameId}")
    Call<List<ReviewCommentRes>> getReviewCommentByGameId(@Path("gameId") Long gameId);
}
