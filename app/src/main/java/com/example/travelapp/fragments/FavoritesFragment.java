package com.example.travelapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView; // 🩷 thêm import này

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapp.R;
import com.example.travelapp.adapters.DestinationAdapter;
import com.example.travelapp.models.Destination;
import com.example.travelapp.utils.DataLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private DestinationAdapter adapter;
    private List<Destination> favoriteList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadFavorites();

        adapter = new DestinationAdapter(getContext(), favoriteList);
        recyclerView.setAdapter(adapter);

        TextView tvEmpty = view.findViewById(R.id.tvEmptyFavorites);
        if (favoriteList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Khi quay lại tab Favorites, cập nhật lại danh sách (phòng khi user mới thả tim)
        loadFavorites();
        adapter.updateList(favoriteList);

        View view = getView();
        if (view != null) {
            TextView tvEmpty = view.findViewById(R.id.tvEmptyFavorites);

            if (favoriteList.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void loadFavorites() {
        SharedPreferences prefs = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE);
        Set<String> favoriteNames = prefs.getStringSet("favorite_names", new HashSet<>());

        // Lấy toàn bộ danh sách địa điểm từ JSON
        List<Destination> allDestinations = DataLoader.loadDestinations(requireContext());
        favoriteList = new ArrayList<>();

        // Lọc ra những địa điểm được yêu thích
        for (Destination d : allDestinations) {
            if (favoriteNames.contains(d.getName())) {
                favoriteList.add(d);
            }
        }
    }
}
