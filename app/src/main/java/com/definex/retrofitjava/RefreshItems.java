package com.definex.retrofitjava;

import android.content.Context;
import android.os.Handler;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.definex.retrofitjava.adapter.RecyclerViewAdapter;
import com.definex.retrofitjava.database.AppDataBase;
import com.definex.retrofitjava.model.CryptoModel;
import com.definex.retrofitjava.model.Singelton;
import com.definex.retrofitjava.view.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class RefreshItems extends Worker {

    AppDataBase db;
    Context myContext;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<CryptoModel> cryptoModel;

    public void setCryptoModel(ArrayList<CryptoModel> cryptoModel) {
        this.cryptoModel = cryptoModel;
    }
//indireceğimiz data için


    public RefreshItems(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);

//        this.myContext = context;
//        db = AppDataBase.getDbInstance(myContext);
    }
    public void insertDatas(ArrayList<CryptoModel> cryptoModels) {
        db.currencyDAO().nukeTable();
        db.currencyDAO().insertCurrencies(cryptoModels);
        setAdapterDatabase();
    }
    private void setAdapterDatabase(){
        recyclerViewAdapter.setDbCryptoList((ArrayList<CryptoModel>) db.currencyDAO().getAllCurrencies());
        recyclerViewAdapter.notifyDataSetChanged();
//        getResponceAfterInterval.run();


    }





    @NonNull
    @NotNull
    @Override
    //singelton ile oluşturup çağır
    public Result doWork() {
        System.out.println("doWork çalışıyor");
//        long millis = System.currentTimeMillis();
//        java.util.Date date = new java.util.Date(millis);
//        System.out.print(date);
//        System.out.println(" : yenileniyor2");
//        insertDatas(cryptoModel);
//            Singelton singelton =Singelton.getInstance();
//        try {
//
//            singelton.insertDatas();
//            System.out.println("worker inserting datas");
//
//        } catch (Exception e) {
//            System.out.println(e + "***********hata");
//        }
        return Result.success();
    }
}
