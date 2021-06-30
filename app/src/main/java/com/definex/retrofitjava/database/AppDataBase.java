package com.definex.retrofitjava.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.definex.retrofitjava.model.CryptoModel;

@Database(entities = {CryptoModel.class}, version = 1)
public abstract /*abs zorunlu*/class AppDataBase extends RoomDatabase {

    public abstract CurrencyDAO currencyDAO ();
    private static AppDataBase INSTANCE;
    public static AppDataBase getDbInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "DB_NAME").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}