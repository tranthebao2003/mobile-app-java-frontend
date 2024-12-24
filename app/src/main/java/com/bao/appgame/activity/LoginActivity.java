package com.bao.appgame.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bao.appgame.R;
import com.bao.appgame.api.LoginApi;
import com.bao.appgame.model.UserRequest;
import com.bao.appgame.model.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameLogin, passwordLogin;
    private TextView btnLogin, btnRegisterLogin, btnForgotPass;
    private Retrofit retrofit; // Retrofit instance
    private LoginApi apiService; // API interface

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setupRetrofit(); // Cấu hình Retrofit
        setBtnListeners();
    }

    private void initView() {
        usernameLogin = findViewById(R.id.usernameLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegisterLogin = findViewById(R.id.btnLoginRegister);
        btnForgotPass =findViewById(R.id.forgotpass);
    }

    private void setupRetrofit() {
        // Cấu hình Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // URL backend
                .addConverterFactory(GsonConverterFactory.create()) // Sử dụng Gson để parse JSON
                .build();
        apiService = retrofit.create(LoginApi.class); // Khởi tạo API interface
    }

    private void setBtnListeners() {
        // Xử lý sự kiện nút Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });

        // Xử lý sự kiện nút Register
        btnRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ConfirmEmail.class));
            }
        });


    }

    private void handleLogin() {
        // Lấy dữ liệu
        String email = usernameLogin.getText().toString().trim(); //trim để bỏ khoảng trắg
        String password = passwordLogin.getText().toString().trim();

        // check data
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo request object
        UserRequest userRequest = new UserRequest(email, password);

        // Gọi API
        apiService.getUserInfo(userRequest).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body().isCheckLogin()) {
                    UserResponse userResponse = response.body();
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("username", userResponse.getUsername());
                    editor.putString("email", userResponse.getEmail());
                    editor.putString("phone", userResponse.getPhone());
                    editor.apply();
                    //Toast.makeText(LoginActivity.this,userResponse.getPassword()+userResponse.getUsername()+userResponse.getEmail()+userResponse.getPhone(), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("API_ERROR", "Lỗi: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Không thể kết nối đến server!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
