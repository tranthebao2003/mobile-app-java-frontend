package com.bao.appgame.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bao.appgame.R;
import com.bao.appgame.adapter.ItemBuyListAdapter;
import com.bao.appgame.api.GameApi;
import com.bao.appgame.api.LoginApi;
import com.bao.appgame.model.AccountInfo;
import com.bao.appgame.model.CartManager;
import com.bao.appgame.model.Game;
import com.bao.appgame.model.ItemGameBuy;
import com.bao.appgame.model.OrderInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuyActivity extends AppCompatActivity {

    ArrayList<ItemGameBuy> list;
    ItemBuyListAdapter adapter;
    ListView lv;
    TextView btnHome;
    Retrofit retrofit;
    GameApi apiService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buy);
        lv = findViewById(R.id.lvBuy);
        setupRetrofit();
        ImageView imageView = findViewById(R.id.imageView9);

        // Tạo Animation di chuyển từ vị trí hiện tại sang vị trí mới
        TranslateAnimation animation = new TranslateAnimation(0, 0, -70, 0);
        animation.setDuration(1000); // Thời gian chạy animation (ms)
        animation.setRepeatCount(Animation.INFINITE); // Lặp lại vô hạn
        animation.setRepeatMode(Animation.REVERSE); // Quay lại vị trí ban đầu
        imageView.startAnimation(animation);
        btnHome = findViewById(R.id.btnBuytoHome);
        order();
        btnOut();

    }

    private void btnOut() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyActivity.this,HomeActivity.class));
            }
        });
    }

    private void order()
    {
        OrderInfo orderInfo = (OrderInfo) getIntent().getSerializableExtra("orderInfo");
        int[] img = {R.drawable.game};  // Hoặc thêm hình ảnh tương ứng vào đây

        List<String> gamename = orderInfo.getGameName();  // Lấy danh sách tên game từ OrderInfo

        apiService.getAccountInfo(orderInfo).enqueue(new Callback<List<AccountInfo>>() {
            @Override
            public void onResponse(Call<List<AccountInfo>> call, Response<List<AccountInfo>> response) {
                List<AccountInfo> accountInfos = response.body();
                if (accountInfos != null) {
                    list = new ArrayList<>();
                    // Duyệt qua danh sách các AccountInfo và thêm vào ItemGameBuy
                    for (int i = 0; i < accountInfos.size(); i++) {
                        AccountInfo accountInfo = accountInfos.get(i);  // Lấy thông tin tài khoản từ danh sách
                        String account = accountInfo.getAccount();  // Lấy tài khoản
                        String pass = accountInfo.getPassword();  // Lấy mật khẩu

                        // Thêm ItemGameBuy vào danh sách
                        list.add(new ItemGameBuy(img[0], gamename.get(i), account, pass));
                    }
                    adapter = new ItemBuyListAdapter(BuyActivity.this, R.layout.viewholder_itembuy, list);
                    lv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<AccountInfo>> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc lỗi API
                Log.e("API Error", "Failure: " + t.getMessage());
            }
        });
    }

    private void setupRetrofit() {
        // Cấu hình Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.5.136:8080/") // URL backend
                .addConverterFactory(GsonConverterFactory.create()) // Sử dụng Gson để parse JSON
                .build();
        apiService = retrofit.create(GameApi.class); // Khởi tạo API interface
    }
}