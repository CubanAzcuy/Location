package com.dev925.location.features.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dev925.location.models.Location;
import com.dev925.location.utils.SelectionResultHandler;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewholder> {

    List<Location> locations = new ArrayList<>();
    SelectionResultHandler<Location> selectionResultHandler;

    public SearchAdapter(SelectionResultHandler<Location> selectionResultHandler) {
        this.selectionResultHandler = selectionResultHandler;
    }

    @NonNull
    @Override
    public SearchViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        return SearchViewholder.createInstance(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewholder searchViewholder, final int position) {
        searchViewholder.bind(locations.get(position));
        searchViewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionResultHandler.onSelection(locations.get(position));
            }
        });
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
