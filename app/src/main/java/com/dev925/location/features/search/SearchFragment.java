package com.dev925.location.features.search;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dev925.location.MainActivity;
import com.dev925.location.R;
import com.dev925.location.models.Location;
import com.dev925.location.utils.DataStore;
import com.dev925.location.utils.ResultHandler;
import com.dev925.location.utils.SelectionResultHandler;

import java.lang.ref.WeakReference;
import java.util.List;

public class SearchFragment extends Fragment implements ResultHandler<List<Location>>, SelectionResultHandler<Location> {

    private SearchTask searchTask;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        searchAdapter = new SearchAdapter(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(actionBar != null) {
            actionBar.show();
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.location_recyclerview);
        setupViews();

        return rootView;
    }

    private void setupViews() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(searchAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        searchTask = new SearchTask(SearchFragment.this);
        searchTask.execute("");
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


    @Override
    public void onResult(List<Location> result) {
        searchAdapter.updateContent(result);
    }

    @Override
    public void onSelection(Location location) {
        if(getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.addLocationFragment(location);
        }
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
            searchTask = new SearchTask(SearchFragment.this);
            searchTask.execute(newText);
            return false;
        }
    };

    private static class SearchTask extends AsyncTask<String, Void, List<Location>> {
        WeakReference<ResultHandler<List<Location>>> resultHandler;

        public SearchTask(ResultHandler<List<Location>> handler) {
            resultHandler = new WeakReference<>(handler);
        }

        @Override
        protected List<Location> doInBackground(String... strings) {
            return DataStore.search(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Location> result) {
            if(!isCancelled() && resultHandler.get() != null) {
                resultHandler.get().onResult(result);
            }

        }
    }

}
