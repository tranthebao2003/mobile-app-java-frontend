package com.bao.appgame.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bao.appgame.R;
import com.bao.appgame.api.LoginApi;
import com.bao.appgame.model.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameRegister, passwordRegister, phoneRegister, emailRegister;
    private TextView btnRegister;
    private Retrofit retrofit; // Retrofit instance
    private LoginApi apiService; // API interface

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setupRetrofit(); // Cấu hình Retrofit
        setBtnListeners();

    }
    private void initView() {
        usernameRegister = findViewById(R.id.usernameRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        phoneRegister = findViewById(R.id.phoneRegister);
        emailRegister = findViewById(R.id.emailRegister);

        btnRegister = findViewById(R.id.btnRegister);
    }
    private void setupRetrofit() {
        // Cấu hình Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.5.136:8080/") // URL backend
                .addConverterFactory(GsonConverterFactory.create()) // Sử dụng Gson để parse JSON
                .build();
        apiService = retrofit.create(LoginApi.class); // Khởi tạo API interface
    }
    private void setBtnListeners() {
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hadleRegister();
                    //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            });

    }

    private void hadleRegister() {
        //lấy data
        String username = usernameRegister.getText().toString().trim();
        String phone = phoneRegister.getText().toString().trim();
        String email = emailRegister.getText().toString().trim();
        String password = passwordRegister.getText().toString().trim();

        //check data
        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "nhập đủ thông tin",Toast.LENGTH_SHORT).show();
            return;
        }

        //tạo đối tượng
        UserResponse userResponse = new UserResponse(username,email,password,phone,true);

        //gọi api
        apiService.saveRegisterUser(userResponse).enqueue(new Callback<UserResponse>() {
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
                    //Toast.makeText(RegisterActivity.this,userResponse.getPassword()+userResponse.getUsername()+userResponse.getEmail()+userResponse.getPhone(), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this,"Email hoặc số điện thoại đã được đăng kí",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });

    }


}