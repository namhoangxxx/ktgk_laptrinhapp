package com.example.travelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword, edtFullName, edtBirthYear, edtEmail, edtPhone;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Ánh xạ view
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtFullName = findViewById(R.id.edtFullName);
        edtBirthYear = findViewById(R.id.edtBirthYear);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        btnRegister = findViewById(R.id.btnRegister);

        // Sự kiện đăng ký
        btnRegister.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String fullName = edtFullName.getText().toString().trim();
            String birthYear = edtBirthYear.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();

            // Kiểm tra nhập đủ thông tin
            if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() ||
                    birthYear.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lưu thông tin đăng ký vào SharedPreferences
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putString("fullname", fullName);
            editor.putString("birthyear", birthYear);
            editor.putString("email", email);
            editor.putString("phone", phone);
            editor.apply();

            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

            // Sau khi đăng ký → chuyển về LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
