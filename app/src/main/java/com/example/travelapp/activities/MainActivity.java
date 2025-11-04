package com.example.travelapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.travelapp.R;
import com.example.travelapp.fragments.ExploreFragment;
import com.example.travelapp.fragments.FavoritesFragment;
import com.example.travelapp.fragments.HomeFragment;
import com.example.travelapp.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        // Mặc định hiển thị HomeFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, new HomeFragment())
                .commit();

        // Bắt sự kiện click
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.nav_explore) {
                selectedFragment = new ExploreFragment();
            } else if (id == R.id.nav_favorites) {
                selectedFragment = new FavoritesFragment();
            } else if (id == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, selectedFragment)
                    .commit();

            return true;
        });
    }
}
