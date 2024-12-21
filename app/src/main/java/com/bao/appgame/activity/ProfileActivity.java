package com.bao.appgame.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.bao.appgame.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView userProfile, emailProfile, phoneProfile;
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
    }

    private void initView() {
        userProfile = findViewById(R.id.usernameProfile);
        emailProfile =findViewById(R.id.emailProfile);
        phoneProfile = findViewById(R.id.phoneProfile);
    }
}