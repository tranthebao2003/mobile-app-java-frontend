package com.bao.appgame.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bao.appgame.R;
import com.bao.appgame.adapter.ListOrderAdapter;
import com.bao.appgame.api.GameApi;
import com.bao.appgame.model.ItemOrderList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListOrderActivity extends AppCompatActivity {

    ArrayList<ItemOrderList> list;
    ListOrderAdapter adapter;
    ListView lv;
    Retrofit retrofit;
    GameApi apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_order);
        setupRetrofit();
        lv = findViewById(R.id.lvorder);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "defaultEmail");

        apiService.getUserOrders(email).enqueue(new Callback<List<ItemOrderList>>() {
            @Override
            public void onResponse(Call<List<ItemOrderList>> call, Response<List<ItemOrderList>> response) {

                if (response.body() != null) Toast.makeText(ListOrderActivity.this, "khac null",Toast.LENGTH_SHORT);
                else Toast.makeText(ListOrderActivity.this,"bang null",Toast.LENGTH_SHORT).show();
                List<ItemOrderList> itemOrderLists = response.body();
                if (itemOrderLists != null) {
                    list = new ArrayList<>();

                    for (int i = 0; i < itemOrderLists.size(); i++) {
                        ItemOrderList item = itemOrderLists.get(i);
                        String gamename = item.getGamename();
                        String account = item.getAccount();
                        String pass = item.getPass();
                        String price = item.getPrice();
                        list.add(new ItemOrderList(gamename, account, pass, price));
                    }
                    adapter = new ListOrderAdapter(ListOrderActivity.this, R.layout.viewholder_item_order, list);
                    lv.setAdapter(adapter);
                }
            }
                @Override
                public void onFailure (Call < List < ItemOrderList >> call, Throwable t){
                    Log.e("API_ERROR", "API call failed: " + t.getMessage());
                }
        });
    }

    private void setupRetrofit() {
        // Cấu hình Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // URL backend
                .addConverterFactory(GsonConverterFactory.create()) // Sử dụng Gson để parse JSON
                .build();
        apiService = retrofit.create(GameApi.class); // Khởi tạo API interface
    }
}