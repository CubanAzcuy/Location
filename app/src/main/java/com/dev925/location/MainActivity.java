package com.dev925.location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.dev925.location.features.loading.LoadingFragment;
import com.dev925.location.features.map.MapFragment;
import com.dev925.location.features.search.SearchFragment;
import com.dev925.location.models.Location;

public class MainActivity extends AppCompatActivity {

    @NonNull public static final String LOCATION_KEY = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addLoadingFragment();

//        Coordinates coordinates = new Coordinates(33.744336834018846, -84.39008675515653);
//        Location location = new Location("Atlanta", "USA", coordinates);
//        addLocationFragment(location);
    }

    public void addLoadingFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new LoadingFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addSearchFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new SearchFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addLocationFragment(Location location) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(LOCATION_KEY, location);
        mapFragment.setArguments(bundle);

        transaction.replace(R.id.fragment_container, mapFragment);
        transaction.addToBackStack(LOCATION_KEY);
        transaction.commit();
    }
}
