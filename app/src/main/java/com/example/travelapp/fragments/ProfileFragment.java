package com.example.travelapp.fragments;

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

import com.example.travelapp.R;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvEmail, tvFavoritesCount, tvToursCount, tvPoints;
    private ImageView btnEditAvatar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvFavoritesCount = view.findViewById(R.id.tvFavoritesCount);
        tvToursCount = view.findViewById(R.id.tvToursCount);
        tvPoints = view.findViewById(R.id.tvPoints);
        btnEditAvatar = view.findViewById(R.id.btnEditAvatar);

        // Dữ liệu demo
        tvName.setText("Nguyễn Văn A");
        tvEmail.setText("email@gmail.com");
        tvFavoritesCount.setText("12");
        tvToursCount.setText("5");
        tvPoints.setText("120");

        // Click Edit Avatar
        btnEditAvatar.setOnClickListener(v ->
                Toast.makeText(getContext(), "Chức năng chỉnh sửa avatar", Toast.LENGTH_SHORT).show());

        // Click menu cá nhân
        view.findViewById(R.id.llProfileInfo).setOnClickListener(v ->
                Toast.makeText(getContext(), "Thông tin cá nhân", Toast.LENGTH_SHORT).show());

        view.findViewById(R.id.llSettings).setOnClickListener(v ->
                Toast.makeText(getContext(), "Cài đặt", Toast.LENGTH_SHORT).show());

        view.findViewById(R.id.llLogout).setOnClickListener(v ->
                Toast.makeText(getContext(), "Đăng xuất", Toast.LENGTH_SHORT).show());

        return view;
    }
}
