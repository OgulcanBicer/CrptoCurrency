package com.definex.retrofitjava.database;

import android.content.Context;

import com.definex.retrofitjava.adapter.RecyclerViewAdapter;
import com.definex.retrofitjava.model.CryptoModel;

import java.util.ArrayList;

public class RoomDatabase {


    Context context;
    ArrayList<CryptoModel> cryptoModels;//indireceğimiz data için
    AppDataBase db;
    RecyclerViewAdapter recyclerViewAdapter;


    public void setContext(Context context) {
        this.context = context;
        db = AppDataBase.getDbInstance(this.context);
    }

    public void setCryptoModels(ArrayList<CryptoModel> cryptoModels) {
        this.cryptoModels = cryptoModels;
        insertDatas();
    }

    public void insertDatas() {
        db.currencyDAO().nukeTable();
        db.currencyDAO().insertCurrencies(cryptoModels);
        setAdapterDatabase();
    }
    private void setAdapterDatabase(){
        recyclerViewAdapter.setDbCryptoList((ArrayList<CryptoModel>) db.currencyDAO().getAllCurrencies());
        recyclerViewAdapter.notifyDataSetChanged();
//        getResponceAfterInterval.run();
    }





}
