package com.bao.appgame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bao.appgame.R;

public class LoginActivity extends AppCompatActivity {
    EditText usernameLogin, passwordLogin;
    TextView btnLogin, btnRegisterLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setBtn();
    }

    private void setBtn() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });

        btnRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void initView() {
        usernameLogin = findViewById(R.id.usernameRegister);
        passwordLogin = findViewById(R.id.phoneRegister);
        btnLogin = findViewById(R.id.btnRegister);
        btnRegisterLogin = findViewById(R.id.btnLoginRegister);
    }
}