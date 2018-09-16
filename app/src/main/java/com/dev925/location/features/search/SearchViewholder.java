package com.dev925.location.features.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev925.location.R;
import com.dev925.location.models.Location;

public class SearchViewholder extends RecyclerView.ViewHolder {

    public SearchViewholder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(Location location) {
        TextView locationyName = itemView.findViewById(R.id.location_name_textview);
        locationyName.setText(location.toString());
    }

    public static SearchViewholder createInstance(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_location_name, viewGroup, false);
        return new SearchViewholder(view);
    }
}
