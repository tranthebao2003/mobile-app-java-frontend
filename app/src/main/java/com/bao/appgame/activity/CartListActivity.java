package com.bao.appgame.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bao.appgame.R;
import com.bao.appgame.adapter.CartListAdapter;
import com.bao.appgame.model.CartManager;
import com.bao.appgame.model.Game;
import com.bao.appgame.model.OrderInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CartListActivity extends AppCompatActivity {

    private CartListAdapter cartListAdapter;
    private RecyclerView recyclerViewCartItem;
    TextView totalItemTxtCart, totalPriceCart, btnCheckoutCart, emptyCartTxt;


    private ScrollView scrollViewCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        initView();
        initList();

        CalculateCart();
        bottomNavigation();
        buyItem();
    }

    private void buyItem() {
        btnCheckoutCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartListActivity.this, BuyActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String email = sharedPreferences.getString("email", "defaultEmail");

                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setUserEmail(email);
                List<Game> cartItem = CartManager.getInstance().getCartItems();

                List<Long> listGameId = new ArrayList<>();
                List<String> listGameName = new ArrayList<>();
                for (Game gameId : cartItem) {
                    listGameId.add(gameId.getGameId());
                    listGameName.add(gameId.getGameName());
                }

                orderInfo.setGameId(listGameId);
                orderInfo.setGameName(listGameName);
                orderInfo.setSumprice(CartManager.getInstance().getTotalPrice());

                intent.putExtra("orderInfo", orderInfo);
                CartManager.getInstance().clear();
                startActivity(intent);

            }
        });
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
        btnCheckoutCart = findViewById(R.id.btnCheckoutCart);
        emptyCartTxt = findViewById(R.id.emptyCartTxt);
        scrollViewCart = findViewById(R.id.scrollViewCart);
    }

    private void initList(){
        List<Game> cartItem = CartManager.getInstance().getCartItems();

        // Hiển thị tổng số lượng và giá tiền
        totalItemTxtCart.setText(String.valueOf(CartManager.getInstance().getTotalItems()));
        totalPriceCart.setText(String.valueOf(CartManager.getInstance().getTotalPrice()).replace(".0", " Đ"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // Gắn Adapter vào RecyclerView
        cartListAdapter = new CartListAdapter(cartItem);
        recyclerViewCartItem.setLayoutManager(linearLayoutManager);
        recyclerViewCartItem.setAdapter(cartListAdapter);
    }

    // chưa làm
    private void CalculateCart(){

//        // chưa làm
//        double total = Math.round((managementCart.getTotalFee()) * 100) / 100;
//        double itemTotal = Math.round(managementCart.getTotalFee() * 100) / 100;
//
//        totalItemTxtCart.setText("$" + itemTotal);
//        totalPriceCart.setText("$" + total);
    }
}