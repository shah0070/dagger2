package com.ecommerce.myapplication.dao;


import com.ecommerce.myapplication.models.MovieData;

/**
 * @author shishank
 */

public interface DatabaseCallbacks {

    void onDataInserted(MovieData data);

    void onFailed(Throwable throwable);
}
