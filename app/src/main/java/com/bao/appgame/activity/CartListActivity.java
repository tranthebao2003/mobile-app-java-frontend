package com.bao.appgame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bao.appgame.R;
import com.bao.appgame.adapter.CartListAdapter;
import com.bao.appgame.api.DetailGameApi;
import com.bao.appgame.api.GameApi;
import com.bao.appgame.model.CartManager;
import com.bao.appgame.model.Game;
import com.bao.appgame.response.ReviewScore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartListActivity extends AppCompatActivity {

    private CartListAdapter cartListAdapter;
    private RecyclerView recyclerViewCartItem;
    TextView totalItemTxtCart, totalPriceCart, btnCheckoutCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        initView();
        initList();
        bottomNavigation();
    }

    public interface BtnRemoveCartItem{
        void onRemoveCartItem(Game game);
    }

    private void bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, HomeActivity.class));
            }
        });
    }

    private void initView() {
        recyclerViewCartItem = findViewById(R.id.recyclerViewCartItem);
        totalItemTxtCart = findViewById(R.id.totalItemTxtCart);
        totalPriceCart = findViewById(R.id.totalPriceCart);
    }

    private void setupAdapter(){
        totalItemTxtCart.setText(String.valueOf(CartManager.getInstance().getTotalItems()));
        totalPriceCart.setText(String.valueOf(CartManager.getInstance().getTotalPrice()).replace(".0", " Đ"));
        recyclerViewCartItem.setAdapter(cartListAdapter);
    }

    private void initList(){
        List<Game> cartItem = CartManager.getInstance().getCartItems();

        // Hiển thị tổng số lượng và giá tiền

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // Gắn Adapter vào RecyclerView
        cartListAdapter = new CartListAdapter(cartItem, new BtnRemoveCartItem() {
            @Override
            public void onRemoveCartItem(Game game) {
                CartManager.getInstance().removeItem(game);
                setupAdapter();
            }
        });
        recyclerViewCartItem.setLayoutManager(linearLayoutManager);
        setupAdapter();
    }

}