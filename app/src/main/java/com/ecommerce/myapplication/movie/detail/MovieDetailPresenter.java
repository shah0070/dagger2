package com.ecommerce.myapplication.movie.detail;

import com.ecommerce.myapplication.dao.DatabaseCallbacks;
import com.ecommerce.myapplication.dao.DatabaseInteractor;
import com.ecommerce.myapplication.models.MovieData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @author shishank
 */

public class MovieDetailPresenter implements Contracts.Presenter, DatabaseCallbacks {

    private Contracts.View movieDetailView;
    private final DatabaseInteractor databaseInteractor;

    public MovieDetailPresenter(Contracts.View movieDetailView,
                                DatabaseInteractor databaseInteractor) {
        this.movieDetailView = movieDetailView;
        this.databaseInteractor = databaseInteractor;
        databaseInteractor.setCallbacks(this);
    }

    @Override
    public void init() {
        movieDetailView.PopulateData();
    }

    @Override
    public void updateMovie(MovieData movieData) {
        movieDetailView.showLoading();
        databaseInteractor.setMovieData(movieData);
    }

    @Override
    public void findMovie(int id) {
        databaseInteractor.getMovieData(id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(movieDetailView::onCompleted, this::onFailed);
    }

    @Override
    public void onDataInserted(MovieData data) {
        findMovie(data.getId());
    }


    @Override
    public void onFailed(Throwable throwable) {
        Timber.d("data inserted"+throwable.getCause());
    }
}
