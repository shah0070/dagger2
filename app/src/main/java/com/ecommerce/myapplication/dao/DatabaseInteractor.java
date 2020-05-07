package com.ecommerce.myapplication.dao;


import androidx.annotation.NonNull;

import com.ecommerce.myapplication.models.MovieData;

import io.reactivex.Single;

/**
 * @author shishank
 */

public interface DatabaseInteractor {

    void setMovieData(MovieData data);

    Single<MovieData> getMovieData(@NonNull int id);

    void setCallbacks(DatabaseCallbacks callbacks);

}
