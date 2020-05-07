package com.ecommerce.myapplication;

import com.ecommerce.myapplication.api.ApiModule;
import com.ecommerce.myapplication.movie.MovieComponent;
import com.ecommerce.myapplication.movie.MovieModule;
import com.ecommerce.myapplication.movie.detail.MovieDetailComponent;
import com.ecommerce.myapplication.movie.detail.MovieDetailModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by abhishek
 * on 14/12/17.
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {
    void inject(BaseApplication baseApplication);

    MovieComponent newMovieComponent(MovieModule movieModule);

    MovieDetailComponent newMovieDetailComponent(MovieDetailModule movieDetailModule);
}