package com.ecommerce.myapplication.movie;

import com.ecommerce.myapplication.api.ApiService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abhishek
 * on 14/12/17.
 */

@Module
public class MovieModule {

    private final Contracts.View movieView;

    public MovieModule(Contracts.View movieView) {
        this.movieView = movieView;
    }

    @Provides
    @MovieScope
    MovieListPresenter provideMovieListPresenter(ApiService apiService) {
        return new MovieListPresenter(movieView, apiService);
    }
}
