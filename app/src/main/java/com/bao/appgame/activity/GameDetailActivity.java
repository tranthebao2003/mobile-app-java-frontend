package com.bao.appgame.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.bao.appgame.R;
import com.bao.appgame.api.DetailGameApi;
import com.bao.appgame.model.CartManager;
import com.bao.appgame.model.Game;
import com.bao.appgame.response.ReviewScore;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameDetailActivity extends AppCompatActivity {
    TextView gameNameDetail, gamePriceDetail, gameDescriptionDetail;
    TextView btnAddCartDetail, btnBuyNowDetail, totalReview;
    ImageView gameImgDetail;
    RatingBar ratingBarReviewDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        initView();
        getGameFromHome();
    }

    private Retrofit setupRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/detailGame/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    private void callReviewScoreGame (Long gameId){
        Retrofit retrofit = setupRetrofit();

        DetailGameApi detailGameApi = retrofit.create(DetailGameApi.class);

        Call<ReviewScore> reviewScoreCall = detailGameApi.getReviewScoreByGameId(gameId);

        reviewScoreCall.enqueue(new Callback<ReviewScore>() {
            @Override
            public void onResponse(Call<ReviewScore> call, Response<ReviewScore> response) {

                if (response.isSuccessful() && response.body() != null) {
                    totalReview.setText(String.valueOf(response.body().getTotalReview() + " đánh giá"));
                    ratingBarReviewDetail.setRating((float)response.body().getAverageScore());

                    Log.d("ReviewScoreByGameId", "tìm kiếm thành công ");
                } else {
                    Log.e("API Error", "Mã lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ReviewScore> call, Throwable t) {
                Log.d("ErrorReviewScoreByGameId", "onFailure: " + t);
            }
        });
    }

    private void getGameFromHome(){
        // Nhận dữ liệu từ Intent của recyclerView newest
        Game game = (Game) getIntent().getSerializableExtra("game_object");

        // Hiển thị dữ liệu và btn add to cart có tác dụng nếu game này khác null
        if (game != null) {
            gameNameDetail.setText(game.getGameName());
            gamePriceDetail.setText(String.valueOf(game.getGamePrice()).replace(".0", " Đ"));
            gameDescriptionDetail.setText(game.getDescription());
            callReviewScoreGame(game.getGameId());
            loadImgGame(game.getGameImg());
            addCartDetail(game);
        }
    }

    private void addCartDetail(Game game){
        btnAddCartDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartManager.getInstance().addItem(game);
                Toast.makeText(GameDetailActivity.this,
                        "Game đã được thêm thành công!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadImgGame(String nameImg){
        String baseUrl = "http://10.0.2.2:8080/uploadImgGame/";
        String imageUrl = baseUrl + nameImg;


        Glide.with(GameDetailActivity.this)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Bộ nhớ đệm cho ảnh
                .placeholder(R.drawable.loading_img) // Ảnh hiển thị khi đang tải (thay bằng ảnh của bạn)
                .into(gameImgDetail);

    }

    private void initView() {
        gameNameDetail = findViewById(R.id.gameNameDetail);
        gamePriceDetail = findViewById(R.id.gamePriceDetail);
        gameDescriptionDetail = findViewById(R.id.gameDescriptionDetail);

        btnAddCartDetail = findViewById(R.id.btnAddCartDetail);

        // thanh toán chưa làm
        btnBuyNowDetail = findViewById(R.id.btnBuyNowDetail);

        gameImgDetail = findViewById(R.id.gameImgDetail);

        // review
        totalReview = findViewById(R.id.totalReviewDetail);
        ratingBarReviewDetail = findViewById(R.id.ratingBarReviewDetail);

    }
}