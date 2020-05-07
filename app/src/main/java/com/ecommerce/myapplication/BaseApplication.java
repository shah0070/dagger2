package com.ecommerce.myapplication;

import android.app.Application;

import com.hookedonplay.decoviewlib.BuildConfig;

import timber.log.Timber;


public class BaseApplication extends Application {
    public AppComponent getAppComponent() {

        return appComponent;
    }

    private AppComponent appComponent;

    public void setAppComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }



    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        appComponent.inject(this);
    }
}
