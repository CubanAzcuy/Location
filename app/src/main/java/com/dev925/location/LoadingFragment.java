package com.dev925.location;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev925.location.utils.DataStore;

import java.lang.ref.WeakReference;

public class LoadingFragment  extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        LoadCache loadCache = new LoadCache(this);
        loadCache.execute();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        return rootView;
    }

    private void loaded() {
        if(getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.addSearchFragment();
        }
    }

    private static class LoadCache extends AsyncTask<Void, Void, Void> {

        private WeakReference<LoadingFragment> weakReference = new WeakReference(null);

        public LoadCache(LoadingFragment context) {
            weakReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DataStore.create(weakReference.get().getContext(), R.raw.cities);
            return null;
        }

        @Override
        protected void onPostExecute(Void nothing) {
            if (!isCancelled()) {
                weakReference.get().loaded();
            }
        }

    }
}