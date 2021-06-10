package com.definex.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.definex.retrofitjava.R;
import com.definex.retrofitjava.adapter.RecyclerViewAdapter;
import com.definex.retrofitjava.model.CryptoModel;
import com.definex.retrofitjava.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CryptoModel> cryptoModels;//indireceğimiz data için
    private String BASE_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    CompositeDisposable compositeDisposable;//yapılan calların temizlenmesi için
    EditText editTextCurrencyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //https://api.nomics.com/v1/prices?key=2e23f67e1188e942f871d816a21625cd60b7757b
        editTextCurrencyName = findViewById(R.id.editTextCurrencyName);
        recyclerView = findViewById(R.id.recyclerView);
        //retrofit & json
        Gson gson = new GsonBuilder().setLenient()//sL : gson aldığını belirtir
                .create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rxjava kullanılacığını belirtmek için
                .addConverterFactory(GsonConverterFactory.create(gson))//gelen veriyi retrofite bildir
                .build();
        loadData();
    }

    public void btnSearchOnClick(View view) {
        loadData();
    }

    private void loadData()
    {
        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);//
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())//hangi thread de gözlemleme işleminin yapılacağı
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::filteredModel)
        );

//butona ata,oncreate içine timer da olur!!!!!!!!!
        //arama için :
   /*
        Call<List<CryptoModel>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()){

                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);
                    //recylerView işlemleri
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);


//                    for (CryptoModel cryptoModel:cryptoModels){
//                        System.out.println(cryptoModel.currency + ": " +cryptoModel.price);
//                    }
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {

                t.printStackTrace();
            }
        });*/
    }

    private void handleResponse(ArrayList<CryptoModel> setModel)
    {

//setAdapter oluştur
        //recylerView işlemleri
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        if (editTextCurrencyName.getText().toString().matches(""))//filter metod
//            recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);//filter !!!!!!! texteditin içinde, buton ekleyerek filtrele
//        else {
//            for (CryptoModel cp : cryptoModels) {
//                if (cp.currency.contains(editTextCurrencyName.getText().toString().toUpperCase())) {
//                    filteredCryptoModels.add(cp);
//                }
//            }
//            recyclerViewAdapter = new RecyclerViewAdapter(filteredCryptoModels);
//        }
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerViewAdapter.setCryptoList(setModel);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void filteredModel(List<CryptoModel> cryptoModelList) {
        //filteredCryptoModels = new ArrayList<>();//////
        if (editTextCurrencyName.getText().toString().matches(""))//filter metod
        {
            cryptoModels = new ArrayList<>(cryptoModelList);

        }
        else {
            cryptoModels.clear();
            for (CryptoModel cp : cryptoModelList) {
                if (cp.currency.contains(editTextCurrencyName.getText().toString().toUpperCase())) {
                    cryptoModels.add(cp);
                }
            }
        }
            handleResponse(cryptoModels);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}


/*package com.definex.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.definex.retrofitjava.R;
import com.definex.retrofitjava.adapter.RecyclerViewAdapter;
import com.definex.retrofitjava.model.CryptoModel;
import com.definex.retrofitjava.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList <CryptoModel> cryptoModels;//indireceğimiz data için

    private String BASE_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;

    RecyclerView recyclerView;

    RecyclerViewAdapter recyclerViewAdapter;

    CompositeDisposable compositeDisposable;//yapılan calların temizlenmesi için






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://api.nomics.com/v1/prices?key=2e23f67e1188e942f871d816a21625cd60b7757b



        recyclerView = findViewById(R.id.recyclerView);
        //retrofit & json
        Gson gson = new GsonBuilder().setLenient()//sL : gson aldığını belirtir
        .create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rxjava kullanılacığını belirtmek için
        .addConverterFactory(GsonConverterFactory.create(gson))//gelen veriyi retrofite bildir
                .build();


        loadData();

    }

    private void loadData()
    {

        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);//

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(cryptoAPI.getData()
        .subscribeOn(Schedulers.io())//hangi thread de gözlemleme işleminin yapılacağı
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        );


//butona ata,oncreate içine timer da olur!!!!!!!!!
        //arama için :
   /*
        Call<List<CryptoModel>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()){

                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);
                    //recylerView işlemleri
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);


//                    for (CryptoModel cryptoModel:cryptoModels){
//                        System.out.println(cryptoModel.currency + ": " +cryptoModel.price);
//                    }
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {

                t.printStackTrace();
            }
        });*/
//}
//
/*
private void handleResponse(List<CryptoModel> cryptoModelList){



        cryptoModels = new ArrayList<>(cryptoModelList);
        //recylerView işlemleri
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);//filter !!!!!!! texteditin içinde, buton ekleyerek filtrele
        recyclerView.setAdapter(recyclerViewAdapter);

        }

@Override
protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();

        }
        }
        */