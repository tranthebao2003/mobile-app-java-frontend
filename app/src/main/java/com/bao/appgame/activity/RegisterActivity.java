package com.bao.appgame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bao.appgame.R;

public class RegisterActivity extends AppCompatActivity {
    EditText usernameRegister, passwordRegister, phoneRegister, emailRegister;
    TextView btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setBtn();
    }

    private void setBtn() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterActivity.this, "Đăng kí thành công !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void initView() {
        usernameRegister = findViewById(R.id.usernameRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        phoneRegister = findViewById(R.id.phoneRegister);
        emailRegister = findViewById(R.id.emailRegister);

        btnRegister = findViewById(R.id.btnRegister);
    }
}