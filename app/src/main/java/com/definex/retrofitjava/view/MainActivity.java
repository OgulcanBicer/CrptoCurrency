package com.definex.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.definex.retrofitjava.R;
import com.definex.retrofitjava.RefreshItems;
import com.definex.retrofitjava.adapter.RecyclerViewAdapter;
import com.definex.retrofitjava.database.AppDataBase;
//import com.definex.retrofitjava.database.RoomDatabase;
import com.definex.retrofitjava.database.RoomDatabase;
import com.definex.retrofitjava.model.CryptoModel;
//import com.definex.retrofitjava.model.Singelton;
import com.definex.retrofitjava.model.Singelton;
import com.definex.retrofitjava.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
//https://api.nomics.com/v1/prices?key=2e23f67e1188e942f871d816a21625cd60b7757b

public class MainActivity extends AppCompatActivity {

    private final String BASE_URL = "https://api.nomics.com/v1/";
    ArrayList<CryptoModel> cryptoModels;//indireceğimiz data için
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    CompositeDisposable compositeDisposable;//yapılan calların temizlenmesi için
    EditText editTextCurrencyName;
    Boolean flag = false;
    CryptoAPI cryptoAPI;
    RoomDatabase roomDatabase;
    AppDataBase db;
    Singelton singelton;
    WorkRequest workRequest;

    Context context;
//    RoomDatabase roomDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextCurrencyName = findViewById(R.id.editTextCurrencyName);
        recyclerView = findViewById(R.id.recyclerView);
//        roomDatabase = new RoomDatabase();
//        roomDatabase.setContext(this.getApplicationContext());
        runCreate();
    }
    public void runCreate(){
        retrofitCreate();
        loadDataCreate();
        loadData();
        recyclerViewCreate();
        workerCreate();
        context = this.getApplicationContext();
        Singelton.getInstance();
//        singelton.setContext(context);


        db = AppDataBase.getDbInstance(context);
//        getLiveData();
    }
    //create
    public void workerCreate() {
        ///////hangi durumda çalışcak
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)//internet tiği
//                .setRequiresBatteryNotLow(false)
                .build();
         workRequest = new PeriodicWorkRequest.Builder(RefreshItems.class, 15, TimeUnit.MINUTES)//hangi periyotta girilceğinin parametresi.min 15 diyor android
                .setConstraints(constraints)
                .build();
        //obje oluşturmak için sıraya alıp başlatır
        WorkManager.getInstance(this).enqueue(workRequest);
//        workerObserver();

    }
//    public void workerObserver(){
//        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(this, new Observer<WorkInfo>() {
//            @Override
//            public void onChanged(WorkInfo workInfo) {
//                if (workInfo.getState()== WorkInfo.State.RUNNING){
//                    System.out.println("worker running");
//                }
//                else if(workInfo.getState()== WorkInfo.State.SUCCEEDED)
//                {
//                    System.out.println("worker succeded");
//                }
//                else if (workInfo.getState()== WorkInfo.State.FAILED)
//                    System.out.println("worker failed");
//            }
//        });
//
//
//    }
    public void retrofitCreate() {
        //retrofit & json
        Gson gson = new GsonBuilder().setLenient()//sL : gson aldığını belirtir
                .create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rxjava kullanılacığını belirtmek için
                .addConverterFactory(GsonConverterFactory.create(gson))//gelen veriyi retrofite bildir
                .build();
    }
    public void loadDataCreate() {
        cryptoAPI = retrofit.create(CryptoAPI.class);//
        compositeDisposable = new CompositeDisposable();
    }
    public void recyclerViewCreate() {
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter();//elimine et, new leme 11/06
        recyclerView.setAdapter(recyclerViewAdapter);//oncreatte bir kere yap. tek instance almayı dene. 11/06
    }

    public void btnSearchOnClick(View view) {
            loadData();
    }
    public void btnInsertDataBaseOnclick(View view){
      insertDatas();
    }

    public void btnGoLiveOnClick(View view){
        getResponceAfterInterval.run();
    }

    //datas
    private void loadData() {
        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())//hangi thread de gözlemleme işleminin yapılacağı
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::filteredModel)
        );
    }


    //filter-search
    public void filteredModel(List<CryptoModel> cryptoModelList) {
        if (editTextCurrencyName.getText().toString().matches("")) {
            cryptoModels = (ArrayList<CryptoModel>) cryptoModelList;
        } else {
            cryptoModels.clear();
            for (CryptoModel cp : cryptoModelList) {
                if (cp.currency.contains(editTextCurrencyName.getText().toString().toUpperCase())) {
                    cryptoModels.add(cp);
                }
            }
        }
//        Singelton singelton = Singelton.getInstance();
//        singelton.setCryptoModelArrayList(cryptoModels);




        setAdapterLiveData(cryptoModels);
    }

    //adapters
    private void setAdapterLiveData(ArrayList<CryptoModel> setModel) {
        recyclerViewAdapter.setCryptoList(setModel);
        recyclerViewAdapter.notifyDataSetChanged();
        if (!flag) {
            recyclerViewAdapter.setDbCryptoList(setModel);
            flag = true;
        }
    }



    public void insertDatas() {
        db.currencyDAO().nukeTable();
        db.currencyDAO().insertCurrencies(cryptoModels);
        setAdapterDatabase();

    }
    private void setAdapterDatabase(){
        recyclerViewAdapter.setDbCryptoList((ArrayList<CryptoModel>) db.currencyDAO().getAllCurrencies());
        recyclerViewAdapter.notifyDataSetChanged();
//        getResponceAfterInterval.run();


    }

    //destroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private final Handler handler = new Handler();
    private Runnable getResponceAfterInterval = new Runnable() {
        public void run() {
            try
            {
                System.out.println("runnig");
                loadData(); //RequestTask() is a AsynchTask requesting and handling response
                recyclerViewAdapter.notifyDataSetChanged();

            } catch (Exception e) {

            }
            handler.postDelayed(this, 5000);
        }
    };

}


//    public void insertDatas() {
//     roomDatabase.setCryptoModels(cryptoModels);
//     roomDatabase.insertDatas();
//    }
//
//
//    private void setAdapterDatabase(){
//        recyclerViewAdapter.setDbCryptoList((ArrayList<CryptoModel>) db.currencyDAO().getAllCurrencies());
//        recyclerViewAdapter.notifyDataSetChanged();
////        getResponceAfterInterval.run();
//    }
