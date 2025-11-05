package com.example.travelapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapp.R;
import com.example.travelapp.models.Destination;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.travelapp.adapters.DestinationAdapter;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private DestinationAdapter adapter;
    private List<Destination> favoriteList;

    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        favoriteList = new ArrayList<>();
        adapter = new DestinationAdapter(getContext(), favoriteList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadFavorites();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites(); // Cập nhật lại khi quay về tab này
    }

    private void loadFavorites() {
        SharedPreferences prefs = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE);
        Set<String> favoriteNames = prefs.getStringSet("favorite_names", new HashSet<>());

        if (favoriteNames.isEmpty()) {
            showEmptyState();
            return;
        }

        db.collection("tours")
                .get()
                .addOnSuccessListener(query -> {
                    favoriteList.clear();

                    for (DocumentSnapshot doc : query.getDocuments()) {
                        Destination dest = doc.toObject(Destination.class);
                        if (dest != null && favoriteNames.contains(dest.getName())) {
                            favoriteList.add(dest);
                        }
                    }

                    if (favoriteList.isEmpty()) {
                        showEmptyState();
                    } else {
                        hideEmptyState();
                    }

                    adapter.updateList(favoriteList);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Lỗi tải dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void showEmptyState() {
        View view = getView();
        if (view == null) return;

        TextView tvEmpty = view.findViewById(R.id.tvEmptyFavorites);
        tvEmpty.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideEmptyState() {
        View view = getView();
        if (view == null) return;

        TextView tvEmpty = view.findViewById(R.id.tvEmptyFavorites);
        tvEmpty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
