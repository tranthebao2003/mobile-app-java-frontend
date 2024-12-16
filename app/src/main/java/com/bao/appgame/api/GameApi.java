package com.bao.appgame.api;
import com.bao.appgame.model.Game;
import com.bao.appgame.response.GamePageRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GameApi {

    // pageNoInput là param đc truyền vào từ chỗ gọi hàm getGamePage
    // sau đó đc gán cho pageNo trong @Path để truyền đi số thứ tự trang
    // ở backend sẽ nhận đc thong qua @PathVariable
    @GET("gameList/{pageNo}")
    Call<GamePageRes> getGamePage(@Path("pageNo") int pageNoInput);

    @GET("search")
    Call<List<Game>> getGameBySearchInput(@Query("searchInput") String searchInput);

    @GET("category/{categoryId}")
    Call<List<Game>> getGameByCategoryId(@Path("categoryId") Long categoryId);

}
