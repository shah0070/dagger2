package com.ecommerce.myapplication.movie.detail;


import com.ecommerce.myapplication.models.MovieData;

/**
 * @author shishank
 */

public interface Contracts {

    interface View {
        void initView();

        void PopulateData();

        void onCompleted(MovieData movieData);

        void showLoading();

        void hideLoading();
    }

    interface Presenter {
        void init();

        void updateMovie(MovieData movieData);

        void findMovie(int id);
    }
}
