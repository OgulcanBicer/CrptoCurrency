package com.definex.retrofitjava.model;

import android.content.Context;

import com.definex.retrofitjava.adapter.RecyclerViewAdapter;
import com.definex.retrofitjava.database.AppDataBase;

import java.util.ArrayList;

public class Singelton {
    AppDataBase db;
    RecyclerViewAdapter recyclerViewAdapter;

    public void setContext(Context context) {
        this.context = context;
    }

    Context context;


    ArrayList<CryptoModel> cryptoModelArrayList;


    public static Singelton singelton;



    public void setCryptoModelArrayList(ArrayList<CryptoModel> cryptoModelArrayList) {
        this.cryptoModelArrayList = cryptoModelArrayList;

    }

    private Singelton(){

    }
    public static Singelton getInstance(){
        if (singelton==null){
            singelton = new Singelton();
        }
        return singelton;
    }

    public void insertDatas() {
        db.currencyDAO().nukeTable();
        db.currencyDAO().insertCurrencies(cryptoModelArrayList);
        setAdapterDatabase();
        System.out.println("singelton insertDatasCagrildi");
    }
    private void setAdapterDatabase(){
        recyclerViewAdapter.setDbCryptoList((ArrayList<CryptoModel>) db.currencyDAO().getAllCurrencies());
        recyclerViewAdapter.notifyDataSetChanged();
        System.out.println("singelton setAdapter cagrildi");

    }







}
