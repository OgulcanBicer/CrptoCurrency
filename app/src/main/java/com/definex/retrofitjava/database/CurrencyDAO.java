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

    @Query("SELECT * FROM cryptomodel limit 60")
    List<CryptoModel> getAllCurrencies();

    @Query("SELECT * FROM cryptomodel where  currency like '%' || :search || '%'")
    List<CryptoModel> getCurrencies(String search);

    @Query("DELETE FROM cryptomodel WHERE currency = :currency")
    void deleteCurrency(String currency);

    @Query("Delete FROM cryptomodel")
    void deleteAllCurrency();

    @Query("UPDATE cryptomodel SET price = :price  WHERE currency = :currency")
    void updateCurrency(String currency, String price);

    @Query("UPDATE cryptomodel SET oldPrice = price WHERE currency = :currency")
    void updateOldCurrency(String currency);


    @Query("UPDATE cryptomodel SET oldPrice = price, price = :newPrice WHERE currency = :currency")
    void updateTable(String currency, String newPrice);
}
