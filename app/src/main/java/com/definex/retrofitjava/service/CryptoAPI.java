package com.definex.retrofitjava.service;

import com.definex.retrofitjava.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    //get işlemi

    //https://api.nomics.com/v1/prices?key=2e23f67e1188e942f871d816a21625cd60b7757b


    @GET("prices?key=2e23f67e1188e942f871d816a21625cd60b7757b")//
    //neyi ve hangi metod ile istediğimiz :


    Observable<List<CryptoModel>> getData();//gözlemlenebilir obje oluşturup yayın yapıcak.

//    Call<List<CryptoModel>> getData();//url si verilen adrese get isteği yollar. sonunda da call yap, Liste içinde Crypto model gelicek.




}
