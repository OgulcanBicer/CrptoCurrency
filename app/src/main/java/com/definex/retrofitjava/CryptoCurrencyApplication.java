package com.definex.retrofitjava;

import android.app.Application;

import com.definex.retrofitjava.database.DatabaseManager;

public class CryptoCurrencyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseManager.getInstance().setContext(this.getApplicationContext());
    }

    @Override
    public void onTerminate() {

        super.onTerminate();
    }
}
