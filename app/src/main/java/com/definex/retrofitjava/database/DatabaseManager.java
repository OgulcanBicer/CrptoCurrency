package com.definex.retrofitjava.database;

import android.content.Context;

import com.definex.retrofitjava.model.CryptoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static AppDataBase db;

    private static DatabaseManager instance = null;

    public static DatabaseManager getInstance() {

        if (instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    public void setContext(Context context) {
        db = AppDataBase.getDbInstance(context);
    }


    public List<CryptoModel> getAllCurrencies() {
        return db.currencyDAO().getAllCurrencies();
    }

    public void replaceAllCurrency(ArrayList<CryptoModel> cryptoModels) {
        db.currencyDAO().deleteAllCurrency();
        db.currencyDAO().insertCurrencies(cryptoModels);
    }

    public void updateOldCurrency(ArrayList<CryptoModel> cryptoModels) {

        for (CryptoModel cm:cryptoModels) {

        db.currencyDAO().updateOldCurrency(cm.currency);
        }
    }
        public void updatedCurrency(String currency, String newPrice) {




        db.currencyDAO().updateCurrency(currency, newPrice);
    }




    public void updateTable(ArrayList<CryptoModel> cryptoModels) {
        for (CryptoModel cm : cryptoModels) {
            db.currencyDAO().updateTable(cm.currency, cm.price);
        }
    }
    public void updateTablePrice(String currency,String price) {
            db.currencyDAO().updateTable(currency,price);
    }
}