package com.example.travelapp.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelapp.LoginActivity;
import com.example.travelapp.PersonalInfoActivity;
import com.example.travelapp.R;
import com.example.travelapp.SettingsActivity;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvEmail, tvFavoritesCount, tvToursCount, tvPoints;
    private ImageView btnEditAvatar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Ánh xạ
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvFavoritesCount = view.findViewById(R.id.tvFavoritesCount);
        tvToursCount = view.findViewById(R.id.tvToursCount);
        tvPoints = view.findViewById(R.id.tvPoints);
        btnEditAvatar = view.findViewById(R.id.btnEditAvatar);

        // Lấy thông tin người dùng từ SharedPreferences
        SharedPreferences prefs = getContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String fullName = prefs.getString("fullname", "Người dùng");
        String email = prefs.getString("email", "email@gmail.com");

        // Gán lên giao diện
        tvName.setText(fullName);
        tvEmail.setText(email);

        // Các thông tin demo
        tvFavoritesCount.setText("12");
        tvToursCount.setText("5");
        tvPoints.setText("120");

        // Click Edit Avatar
        btnEditAvatar.setOnClickListener(v ->
                Toast.makeText(getContext(), "Chức năng chỉnh sửa avatar", Toast.LENGTH_SHORT).show());

        // Click "Thông tin cá nhân"
        view.findViewById(R.id.llProfileInfo).setOnClickListener(v ->
                startActivity(new Intent(getContext(), PersonalInfoActivity.class)));

        // Click "Cài đặt"
        view.findViewById(R.id.llSettings).setOnClickListener(v ->
                startActivity(new Intent(getContext(), SettingsActivity.class)));

        // ✅ Click "Đăng xuất" — thêm phần này
        view.findViewById(R.id.llLogout).setOnClickListener(v -> {
            // Xóa trạng thái đăng nhập
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            // Thông báo
            Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

            // Chuyển về màn hình đăng nhập
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            requireActivity().finish(); // đóng MainActivity
        });

        return view;
    }
}
