package com.bao.appgame.api;

import com.bao.appgame.model.ReviewRequest;
import com.bao.appgame.response.ReviewCommentRes;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewCommentApi {
    @GET("review/{gameId}")
    Call<List<ReviewCommentRes>> getReviewCommentByGameId(@Path("gameId") Long gameId);

    @POST("/addReview")
    Call<Boolean> addReview(@Body ReviewRequest reviewRequest);
}
