package com.bao.appgame.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bao.appgame.R;
import com.bao.appgame.model.CartManager;
import com.bao.appgame.model.Game;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GameDetailActivity extends AppCompatActivity {
    TextView gameNameDetail, gamePriceDetail, gameDescriptionDetail;
    TextView btnAddCartDetail, btnBuyNowDetail;
    ImageView gameImgDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        initView();

        // Nhận dữ liệu từ Intent của recyclerView newest
        Game game = (Game) getIntent().getSerializableExtra("game_object");

        // Hiển thị dữ liệu và btn add to cart có tác dụng nếu game này khác null
        if (game != null) {
            gameNameDetail.setText(game.getGameName());
            gamePriceDetail.setText(String.valueOf(game.getGamePrice()).replace(".0", " Đ"));
            gameDescriptionDetail.setText(game.getDescription());
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

    }
}