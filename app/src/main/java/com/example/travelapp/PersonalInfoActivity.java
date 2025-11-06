package com.example.travelapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        // ✅ Hiển thị nút quay lại trên ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Thông tin cá nhân");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Lấy dữ liệu đã lưu trong SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String fullName = prefs.getString("fullname", "Chưa cập nhật");
        String birthYear = prefs.getString("birthyear", "Chưa cập nhật");
        String email = prefs.getString("email", "Chưa cập nhật");
        String phone = prefs.getString("phone", "Chưa cập nhật");

        // Cập nhật giao diện
        ((TextView) findViewById(R.id.tvFullName)).setText("Họ tên: " + fullName);
        ((TextView) findViewById(R.id.tvBirthYear)).setText("Năm sinh: " + birthYear);
        ((TextView) findViewById(R.id.tvEmail)).setText("Email: " + email);
        ((TextView) findViewById(R.id.tvPhone)).setText("SĐT: " + phone);
    }

    //  Xử lý khi nhấn nút quay lại
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
