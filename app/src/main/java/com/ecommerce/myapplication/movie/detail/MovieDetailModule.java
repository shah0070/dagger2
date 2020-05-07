package com.ecommerce.myapplication.movie.detail;

import com.ecommerce.myapplication.dao.DatabaseInteractor;
import com.ecommerce.myapplication.movie.MovieScope;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by abhishek
 * on 24/12/17.
 */

@Module
public class MovieDetailModule {

    private final Contracts.View movieDetailView;

    public MovieDetailModule(Contracts.View movieDetailView) {
        this.movieDetailView = movieDetailView;
    }

    @Provides
    @MovieScope
    MovieDetailPresenter provideMovieDetailPresenter(DatabaseInteractor databaseInteractor) {
        Timber.d("Creating new presenter for movie detail");
        return new MovieDetailPresenter(movieDetailView, databaseInteractor);
    }
}
