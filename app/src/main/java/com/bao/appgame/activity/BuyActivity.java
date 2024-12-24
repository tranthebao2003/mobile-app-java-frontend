package com.bao.appgame.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bao.appgame.R;
import com.bao.appgame.adapter.ItemBuyListAdapter;
import com.bao.appgame.api.GameApi;
import com.bao.appgame.model.AccountInfo;
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

        TranslateAnimation animation = new TranslateAnimation(0, 0, -70, 0);
        animation.setDuration(1000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
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
        int[] img = {R.drawable.game};

        List<String> gamename = orderInfo.getGameName();

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


                        list.add(new ItemGameBuy(img[0], gamename.get(i), account, pass));
                    }
                    adapter = new ItemBuyListAdapter(BuyActivity.this, R.layout.viewholder_itembuy, list);
                    lv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<AccountInfo>> call, Throwable t) {

                Toast.makeText(BuyActivity.this,"Lỗi gọi dữ liệu",Toast.LENGTH_SHORT).show();
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