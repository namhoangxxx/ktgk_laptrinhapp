package com.example.travelapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapp.R;
import com.example.travelapp.adapters.DestinationAdapter;
import com.example.travelapp.models.Destination;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private RecyclerView recyclerView;
    private DestinationAdapter adapter;
    private List<Destination> destinationList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewExplore);
        SearchView searchView = view.findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        destinationList = new ArrayList<>();
        adapter = new DestinationAdapter(getContext(), destinationList);
        recyclerView.setAdapter(adapter);

        // 🔹 Load Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tours")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    destinationList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Destination dest = doc.toObject(Destination.class);
                        destinationList.add(dest);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Lỗi tải dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show());

        // 🔹 Search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        return view;
    }

    private void filterList(String text) {
        List<Destination> filtered = new ArrayList<>();
        for (Destination d : destinationList) {
            if (d.getName().toLowerCase().contains(text.toLowerCase()) ||
                    d.getLocation().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(d);
            }
        }
        adapter.updateList(filtered);
    }
}
