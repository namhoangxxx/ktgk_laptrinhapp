package com.example.travelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.activities.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView tvRegister, tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        // Kiểm tra nếu người dùng đã đăng nhập rồi thì vào thẳng MainActivity
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        // Xử lý đăng nhập
        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy thông tin tài khoản đã lưu
            String savedUsername = prefs.getString("username", "nam");
            String savedPassword = prefs.getString("password", "123");

            if (username.equals(savedUsername) && password.equals(savedPassword)) {
                // Lưu trạng thái đăng nhập
                prefs.edit().putBoolean("isLoggedIn", true).apply();

                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Tài khoản hoặc mật khẩu chưa đúng", Toast.LENGTH_SHORT).show();
            }
        });

        // Click “Đăng ký”
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Click “Quên mật khẩu”
        tvForgotPassword.setOnClickListener(v -> {
            Toast.makeText(this, "Chức năng quên mật khẩu đang phát triển", Toast.LENGTH_SHORT).show();
        });
    }
}
