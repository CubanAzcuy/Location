package com.dev925.location;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dev925.location.models.Location;
import com.dev925.location.utils.DataStore;

import java.util.List;

public class SearchFragment extends Fragment {

    private SearchTask searchTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = createSearchView();


        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);

        searchView.setOnQueryTextListener(searchQueryTextListener);
    }

    @NonNull
    private SearchView createSearchView() {
        AppCompatActivity appCompatActivity = ((AppCompatActivity) getContext());
        Context themeContext = appCompatActivity.getSupportActionBar().getThemedContext();
        if(themeContext == null) themeContext = getContext();
        return new SearchView(themeContext);
    }

    SearchView.OnQueryTextListener searchQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }
        @Override
        public boolean onQueryTextChange(String newText) {
            if(searchTask != null) {
                searchTask.cancel(true);
            }
            searchTask = new SearchTask();
            searchTask.execute(newText);
            return false;
        }
    };

    private static class SearchTask extends AsyncTask<String, Void, List<Location>> {
        @Override
        protected List<Location> doInBackground(String... strings) {
            return DataStore.search(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Location> result) {
            if(!isCancelled()) {
                Log.d("Test", Integer.valueOf(result.size()).toString());
            }
        }
    }

}
