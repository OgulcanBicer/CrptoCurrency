package com.definex.retrofitjava.model;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {

    @SerializedName("currency")//gelecek datayı direkt okumak için. Json daki ile aynı olmalı
    public String currency;

    @SerializedName("price")
    public String price;


}
