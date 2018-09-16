package com.dev925.location.features.loading;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev925.location.MainActivity;
import com.dev925.location.R;
import com.dev925.location.utils.DataStore;
import com.dev925.location.utils.ResultHandler;

import java.lang.ref.WeakReference;

public class LoadingFragment extends Fragment implements ResultHandler<Void> {
    private LoadCache loadCacheTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_loading, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(loadCacheTask == null) {
            loadCacheTask = new LoadCache(this.getContext(), this);
            loadCacheTask.execute();
        }
    }

    @Override
    public void onResult(Void result) {
        if(getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.addSearchFragment();
        }
    }

    private static class LoadCache extends AsyncTask<Void, Void, Void> {

        private WeakReference<ResultHandler<Void>> resultHandler;
        private WeakReference<Context> weakContext = new WeakReference(null);

        public LoadCache(Context context, ResultHandler<Void> resultHandler) {
            this.weakContext = new WeakReference<>(context);
            this.resultHandler = new WeakReference<>(resultHandler);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DataStore.create(weakContext.get(), R.raw.cities);
            return null;
        }

        @Override
        protected void onPostExecute(Void nothing) {
            if (!isCancelled() && resultHandler.get()!= null) {
                resultHandler.get().onResult(null);
            }
        }

    }
}