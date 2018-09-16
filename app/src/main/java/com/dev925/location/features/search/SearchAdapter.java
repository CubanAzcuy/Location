package com.dev925.location.features.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dev925.location.models.Location;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewholder> {

    List<Location> locations = new ArrayList<>();

    @NonNull
    @Override
    public SearchViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        return SearchViewholder.createInstance(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewholder searchViewholder, int position) {
        searchViewholder.bind(locations.get(position));
    }

    public void updateContent(List<Location> locationList) {
        locations = locationList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return locations.size();
    }
}
