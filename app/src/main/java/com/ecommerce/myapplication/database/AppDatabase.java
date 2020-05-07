package com.ecommerce.myapplication.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ecommerce.myapplication.dao.MovieDataDao;
import com.ecommerce.myapplication.models.MovieData;

/**
 * @author shishank
 */

@Database(entities = {MovieData.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDataDao movieDataDao();
}
