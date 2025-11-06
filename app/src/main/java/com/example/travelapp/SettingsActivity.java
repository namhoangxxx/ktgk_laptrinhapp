package com.example.travelapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // ✅ Hiển thị nút quay lại trên ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Cài đặt");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        switchDarkMode = findViewById(R.id.switchDarkMode);

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        boolean darkMode = prefs.getBoolean("dark_mode", false);
        switchDarkMode.setChecked(darkMode);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );
            prefs.edit().putBoolean("dark_mode", isChecked).apply();
        });
    }

    //  Xử lý khi nhấn nút quay lại
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // hoặc finish();
        return true;
    }
}
