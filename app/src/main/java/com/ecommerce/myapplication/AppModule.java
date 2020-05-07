package com.ecommerce.myapplication;


import androidx.room.Room;

import com.ecommerce.myapplication.dao.DatabaseInteractor;
import com.ecommerce.myapplication.dao.DatabaseWrapper;
import com.ecommerce.myapplication.database.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abhishek
 * on 14/12/17.
 *
 * Provides application class
 */

@Module
public class AppModule {

    private final BaseApplication application;

    public AppModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    DatabaseInteractor providesDatabaseInteractor(AppDatabase appDatabase) {
        return new DatabaseWrapper(appDatabase);
    }

    @Provides
    @Singleton
    BaseApplication providesApplication () {
        return application;
    }

    @Provides
    @Singleton
    AppDatabase providesAppDatabase() {
        return Room.databaseBuilder(application, AppDatabase.class, "movieData").allowMainThreadQueries().build();
    }
}
