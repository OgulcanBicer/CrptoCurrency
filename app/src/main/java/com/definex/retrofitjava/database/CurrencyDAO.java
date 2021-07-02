package com.definex.retrofitjava.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.definex.retrofitjava.model.CryptoModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CurrencyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrencies(ArrayList<CryptoModel> cryptoModels);

    @Query("SELECT * FROM cryptomodel")
    List<CryptoModel> getAllCurrencies();

    @Query("SELECT * FROM cryptomodel where  currency like '%' || :search || '%'")
    List<CryptoModel> getCurrencies(String search);

    @Query("DELETE FROM cryptomodel WHERE currency = :currency")
    public void deleteCurrency(String currency);

    @Query("Delete FROM cryptomodel")
    public void deleteAllCurrency();

    @Query("UPDATE cryptomodel SET price = :price  WHERE currency = :currency")
    public void updateCurrency(String currency, String price);
}
