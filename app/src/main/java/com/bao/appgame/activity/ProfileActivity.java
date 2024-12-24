package com.bao.appgame.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.bao.appgame.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView userProfile, emailProfile, phoneProfile, btnorder, btnlogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "defaultUsername");
        String email = sharedPreferences.getString("email", "defaultEmail");
        String phone = sharedPreferences.getString("phone", "defaultPhone");

        userProfile.setText(username);
        emailProfile.setText(email);
        phoneProfile.setText(phone);

        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, ListOrderActivity.class));
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
    }

    private void initView() {
        userProfile = findViewById(R.id.usernameProfile);
        emailProfile =findViewById(R.id.emailProfile);
        phoneProfile = findViewById(R.id.phoneProfile);
        btnorder = findViewById(R.id.btnOrder);
        btnlogout = findViewById(R.id.btnLogout);
    }
}