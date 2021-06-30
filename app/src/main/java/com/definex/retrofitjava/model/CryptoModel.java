package com.definex.retrofitjava.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity
public class CryptoModel {






    @ColumnInfo(name = "currency")
    @SerializedName("currency")//gelecek datayı direkt okumak için. Json daki ile aynı olmalı
    public String currency;


    @ColumnInfo(name = "price")
    @SerializedName("price")//json ile aynı isimde olması lazım
    public String price;

    @PrimaryKey(autoGenerate = true)
    public int uid=0;

}
