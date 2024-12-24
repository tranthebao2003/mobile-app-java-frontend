package com.bao.appgame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bao.appgame.R;
import com.bao.appgame.api.LoginApi;
import com.bao.appgame.model.EmailRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfirmEmail extends AppCompatActivity {

    private EditText emailConfirm;
    private TextView btnConfirm;
    private Retrofit retrofit;
    private LoginApi apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirm_email);
        emailConfirm = findViewById(R.id.usernameLogin2);
        btnConfirm = findViewById(R.id.btnconfirm);
        setupRetrofit();
        btnConfirmEmail();
    }
    private void setupRetrofit() {
        // Cấu hình Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // URL backend
                .addConverterFactory(GsonConverterFactory.create()) // Sử dụng Gson để parse JSON
                .build();
        apiService = retrofit.create(LoginApi.class); // Khởi tạo API interface
    }

    private void btnConfirmEmail() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailConfirm.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(ConfirmEmail.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(ConfirmEmail.this, "Địa chỉ email không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng chứa email
                EmailRequest emailPayload = new EmailRequest(email);

                // Gửi đối tượng JSON
                apiService.sendEmail(emailPayload).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.body() != null && response.body()) {
                            Toast.makeText(ConfirmEmail.this, "Kiểm tra Email để nhận password mới", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ConfirmEmail.this, LoginActivity.class));
                        } else {
                            Toast.makeText(ConfirmEmail.this, "Email chưa được đăng kí", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(ConfirmEmail.this, "Lỗi kết nối tới server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });
    }

}