package com.bao.appgame.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bao.appgame.R;
import com.bao.appgame.api.ReviewCommentApi;
import com.bao.appgame.model.ReviewRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewActivity extends AppCompatActivity {

    TextView gamename;
    RatingBar rating;
    EditText comment;
    TextView btnReview;
    Retrofit retrofit;
    ReviewCommentApi apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review);
        ImageView imageView = findViewById(R.id.imageView7);

        TranslateAnimation animation = new TranslateAnimation(0, 0, -70, 0);
        animation.setDuration(1000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        imageView.startAnimation(animation);
        setupRetrofit();
        initView();
        sendReview();

    }

    private void sendReview() {
        ReviewRequest reviewRequest = new ReviewRequest();
        String name = getIntent().getStringExtra("gameName");
        gamename.setText(name);

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewRequest.setGameName(name);

                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String email = sharedPreferences.getString("email", "defaultEmail");
                reviewRequest.setUserEmail(email);

                reviewRequest.setComment(comment.getText().toString());
                reviewRequest.setScore((int) rating.getRating());

                apiService.addReview(reviewRequest).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Boolean responses = response.body();
                        if (responses!=null){
                            Toast.makeText(ReviewActivity.this,"Gửi đánh giá thành công",Toast.LENGTH_SHORT).show();
                            rating.setRating(0);
                            comment.setText("");
                        }


                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(ReviewActivity.this,"Lỗi gọi dữ liệu",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void initView() {
        gamename = findViewById(R.id.gameNameReview);
        rating = findViewById(R.id.ratingGame);
        comment = findViewById(R.id.txtReview);
        btnReview = findViewById(R.id.btnReview);
    }

    private void setupRetrofit() {
        // Cấu hình Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // URL backend
                .addConverterFactory(GsonConverterFactory.create()) // Sử dụng Gson để parse JSON
                .build();
        apiService = retrofit.create(ReviewCommentApi.class); // Khởi tạo API interface
    }
}