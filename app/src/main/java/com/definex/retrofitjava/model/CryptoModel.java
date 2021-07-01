package com.definex.retrofitjava.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity
public class CryptoModel {
    @ColumnInfo(name = "uid")
    @PrimaryKey(autoGenerate = true)
    public int uid=0;

    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    public String currency;


    @ColumnInfo(name = "price")
    @SerializedName("price")
    public String price;
}
