package com.definex.retrofitjava.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.definex.retrofitjava.model.CryptoModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CurrencyDAO {

    @Insert
    void insertCurrencies(ArrayList<CryptoModel> cryptoModels);

    @Query("SELECT * FROM cryptomodel")
    List<CryptoModel> getAllCurrencies();

    @Query("Delete FROM cryptomodel")
    public void nukeTable();


    @Update
    public int updateCurrency(CryptoModel cryptoModel);

    @Update
    public void updateCurr(CryptoModel... cryptoModel);

    @Delete
    void deleteAllCurrencies(CryptoModel cryptoModel);

    @Delete
    void deleteAllCurr(CryptoModel... cryptoModel);




}
