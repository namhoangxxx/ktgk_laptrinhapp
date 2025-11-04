package com.example.travelapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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
import java.util.List;

public class ExploreFragment extends Fragment {

    private RecyclerView recyclerView;
    private DestinationAdapter adapter;
    private List<Destination> destinationList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewExplore);
        SearchView searchView = view.findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load danh sách từ JSON
        destinationList = DataLoader.loadDestinations(getContext());
        adapter = new DestinationAdapter(getContext(),destinationList);
        recyclerView.setAdapter(adapter);

        // Tìm kiếm
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
        List<Destination> filteredList = new ArrayList<>();
        for (Destination dest : destinationList) {
            if (dest.getName().toLowerCase().contains(text.toLowerCase()) ||
                    dest.getLocation().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(dest);
            }
        }
        adapter.updateList(filteredList);
    }
}
