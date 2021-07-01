package com.definex.retrofitjava;

public class Constant {

    private static Constant instance = null;

    public static Constant getInstance() {

        if (instance == null) {
            instance = new Constant();
        }

        return instance;
    }

    public String getBase_Url() {
        return BASE_URL;
    }


    //Normalde BaseUrl burada tanımlanmaz. productFlavors içerisnde ortam bazlı yapılmalı
    private String BASE_URL = "https://api.nomics.com/v1/";


}