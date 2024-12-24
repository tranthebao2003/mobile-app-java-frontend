package com.bao.appgame.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bao.appgame.R;
import com.bao.appgame.adapter.NewestAdapter;
import com.bao.appgame.adapter.ReviewCommentAdapter;
import com.bao.appgame.api.DetailGameApi;
import com.bao.appgame.api.ReviewCommentApi;
import com.bao.appgame.model.CartManager;
import com.bao.appgame.model.Game;
import com.bao.appgame.response.ReviewCommentRes;
import com.bao.appgame.response.ReviewScore;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.bao.appgame.model.OrderInfo;

import java.util.ArrayList;

public class GameDetailActivity extends AppCompatActivity {
    private TextView gameNameDetail, gamePriceDetail, gameDescriptionDetail;
    private TextView btnAddCartDetail, btnBuyNowDetail, totalReview, totalInStock;
    private ImageView gameImgDetail;
    private RatingBar ratingBarReviewDetail;
    private RecyclerView recyclerViewReviewComment;
    private RecyclerView.Adapter adapterReviewComment;

    // nhận từ response.body() dùng để check xem có click vào đc
    // 2 btn add cart and thanh toán ngay không?
    private int totalGameInStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        initView();
        getGameFromHome();
    }

    private Retrofit setupRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.5.136:8080/detailGame/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
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
            buyNowDetail(game);
            callReviewComment(game);
        }
    }

    private void callReviewComment(Game game){
        Retrofit retrofit = setupRetrofit();

        ReviewCommentApi reviewCommentApi = retrofit.create(ReviewCommentApi.class);

        Call<List<ReviewCommentRes>> reviewCommentByGameId = reviewCommentApi.getReviewCommentByGameId(game.getGameId());

        reviewCommentByGameId.enqueue(new Callback<List<ReviewCommentRes>>() {
            @Override
            public void onResponse(Call<List<ReviewCommentRes>> call, Response<List<ReviewCommentRes>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    recyclerReviewComment(response.body());
                    Log.d("ReviewCommentByGameId", "tìm kiếm thành công ");
                } else {
                    Log.e("API Error", "Mã lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ReviewCommentRes>> call, Throwable t) {
                Log.d("ErrorReviewCommentByGameId", "onFailure: " + t);
            }
        });
    }

    private void recyclerReviewComment(List<ReviewCommentRes> reviewCommentResList){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewReviewComment = findViewById(R.id.recyclerViewReviewComment);
        recyclerViewReviewComment.setLayoutManager(linearLayoutManager);

        adapterReviewComment = new ReviewCommentAdapter(reviewCommentResList);
        recyclerViewReviewComment.setAdapter(adapterReviewComment);
    }

    private void callReviewScoreGame (Long gameId){
        Retrofit retrofit = setupRetrofit();

        DetailGameApi detailGameApi = retrofit.create(DetailGameApi.class);

        Call<ReviewScore> reviewScoreCall = detailGameApi.getReviewScoreByGameId(gameId);

        reviewScoreCall.enqueue(new Callback<ReviewScore>() {
            @Override
            public void onResponse(Call<ReviewScore> call, Response<ReviewScore> response) {

                if (response.isSuccessful() && response.body() != null) {
                    totalReview.setText(response.body().getTotalReview() + " đánh giá");
                    totalInStock.setText("Số tài khoản: " +response.body().totalGameInStock());

                    ratingBarReviewDetail.setRating((float)response.body().getAverageScore());

                    totalGameInStock = response.body().totalGameInStock();

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

    private void buyNowDetail(Game game) {
        btnBuyNowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalGameInStock == 0){
                    Toast.makeText(GameDetailActivity.this,
                            "Hết hàng, xin lỗi vì sự bất tiện này !",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent = new Intent(GameDetailActivity.this, BuyActivity.class);

                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    String email = sharedPreferences.getString("email", "defaultEmail");

                    OrderInfo orderInfo = new OrderInfo();
                    orderInfo.setUserEmail(email);

                    List<Long> listGameId = new ArrayList<>();
                    listGameId.add(game.getGameId()); // Giả sử Game có phương thức getId()

                    List<String> listGameName = new ArrayList<>();
                    listGameName.add(game.getGameName());
                    orderInfo.setGameId(listGameId);
                    orderInfo.setGameName(listGameName);
                    orderInfo.setSumprice(game.getGamePrice());

                    intent.putExtra("orderInfo", orderInfo);
                    startActivity(intent);
                }
            }
        });
    }

    private void addCartDetail(Game game){
        btnAddCartDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalGameInStock == 0){
                    Toast.makeText(GameDetailActivity.this,
                            "Hết hàng, xin lỗi vì sự bất tiện này !",
                            Toast.LENGTH_SHORT).show();
                } else{
                    CartManager.getInstance().addItem(game);
                    Toast.makeText(GameDetailActivity.this,
                            "Game đã được thêm thành công!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadImgGame(String nameImg){
        String baseUrl = "http://192.168.5.136:8080/uploadImgGame/";
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
        totalInStock = findViewById(R.id.totalInStock);
    }
}