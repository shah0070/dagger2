package com.ecommerce.myapplication.movie.detail;

import com.ecommerce.myapplication.movie.MovieScope;

import dagger.Subcomponent;

/**
 * Created by abhishek
 * on 14/12/17.
 *
 * Custom component for movie detail screen, this is a child component of App Component
 * and needs to be smaller in size
 */
@MovieScope
@Subcomponent(modules = {MovieDetailModule.class})
public interface MovieDetailComponent {
    void inject(MovieDetailFragment movieDetailFragment);
}
