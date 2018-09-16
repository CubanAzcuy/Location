package com.dev925.location.features.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dev925.location.R;
import com.dev925.location.models.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.dev925.location.MainActivity.LOCATION_KEY;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private Location location;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        location = getArguments().getParcelable(LOCATION_KEY);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(actionBar != null) {
            actionBar.show();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        mapView = (MapView) rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        mapView.getMapAsync(this);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng coordinate = new LatLng(location.coord.lat, location.coord.lon);
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(coordinate)
                .title(location.toString()));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 12.0f));
        marker.showInfoWindow();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
