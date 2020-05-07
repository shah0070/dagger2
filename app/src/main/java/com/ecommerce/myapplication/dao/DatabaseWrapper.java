package com.ecommerce.myapplication.dao;

import android.os.AsyncTask;

import com.ecommerce.myapplication.database.AppDatabase;
import com.ecommerce.myapplication.models.MovieData;

import io.reactivex.Single;

/**
 * @author shishank
 */

public class DatabaseWrapper implements DatabaseInteractor {

    private final AppDatabase appDatabase;
    private DatabaseCallbacks callbacks;

    public DatabaseWrapper(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void setMovieData(MovieData data) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.movieDataDao().addMovieData(data);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callbacks.onDataInserted(data);
            }
        }.execute();

    }

    @Override
    public Single<MovieData> getMovieData(int id) {
        return appDatabase.movieDataDao().getMovieData(id);
    }

    @Override
    public void setCallbacks(DatabaseCallbacks callbacks) {
        this.callbacks = callbacks;
    }

}
