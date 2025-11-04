package com.example.travelapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.travelapp.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Views
        ImageView imageView = findViewById(R.id.detail_image);
        TextView nameView = findViewById(R.id.detail_name);
        TextView locationView = findViewById(R.id.detail_location);
        TextView descView = findViewById(R.id.detail_description);
        Button btnBack = findViewById(R.id.btnBack);

        // Nhận dữ liệu từ Intent
        String name = getIntent().getStringExtra("name");
        String location = getIntent().getStringExtra("location");
        String description = getIntent().getStringExtra("description");
        int imageResId = getIntent().getIntExtra("imageResId", 0);

        // Gán dữ liệu lên giao diện
        nameView.setText(name);
        locationView.setText(location);
        descView.setText(description);
        imageView.setImageResource(imageResId);

        // Nút quay lại
        btnBack.setOnClickListener(v -> finish()); // finish() sẽ quay về ExploreFragment
    }
}
