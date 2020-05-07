package com.ecommerce.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ecommerce.myapplication.models.MovieData;

import io.reactivex.Single;

/**
 * @author shishank
 */

@Dao
public interface MovieDataDao {

    @Query("SELECT * FROM MovieData WHERE id = :id")
    Single<MovieData> getMovieData(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovieData(MovieData movieData);

    @Update
    void updateMovieData(MovieData movieData);

}
