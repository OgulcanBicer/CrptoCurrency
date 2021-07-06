package com.definex.retrofitjava.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.definex.retrofitjava.Constant;
import com.definex.retrofitjava.R;
import com.definex.retrofitjava.RefreshItems;
import com.definex.retrofitjava.adapter.RecyclerViewAdapter;
import com.definex.retrofitjava.database.DatabaseManager;
import com.definex.retrofitjava.model.CryptoModel;
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


public class MainActivity extends AppCompatActivity {


    ArrayList<CryptoModel> cryptoModels;//indireceğimiz data için
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    CompositeDisposable compositeDisposable;//yapılan calların temizlenmesi için
    EditText editTextCurrencyName;
    Boolean flag = false;
    CryptoAPI cryptoAPI;

    WorkRequest workRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextCurrencyName = findViewById(R.id.editTextCurrencyName);
        recyclerView = findViewById(R.id.recyclerView);


        retrofitCreate();
        loadDataCreate();
        loadData();
        recyclerViewCreate();
        workerCreate();

    }

    //create
    public void workerCreate() {
        ///////hangi durumda çalışcak
//        Constraints constraints = new Constraints.Builder()
//                .setRequiredNetworkType(NetworkType.CONNECTED)//internet tiği
////                .setRequiresBatteryNotLow(false)
//                .build();
//        workRequest = new PeriodicWorkRequest.Builder(RefreshItems.class, 15, TimeUnit.MINUTES)//hangi periyotta girilceğinin parametresi.min 15 diyor android
//                .setConstraints(constraints)
//                .build();
//        //obje oluşturmak için sıraya alıp başlatır
//        WorkManager.getInstance(this).enqueue(workRequest);
////        workerObserver();

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
        retrofit = new Retrofit.Builder().baseUrl(Constant.getInstance().getBase_Url())
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

    public void btnInsertDataBaseOnclick(View view) {


        DatabaseManager.getInstance().replaceAllCurrency(cryptoModels);

        setAdapterDatabase();
    }

    public void btnGoLiveOnClick(View view) {
//        getResponceAfterInterval.run();
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

//save currency,newprice
        //save old price(old = new).
        //update newprice(real-time)


        cryptoModels = (ArrayList<CryptoModel>) cryptoModelList;

        if (!flag) {
            System.out.println("--------------------------------------------------------------------******************");
            try {
                DatabaseManager.getInstance().replaceAllCurrency(cryptoModels);
                DatabaseManager.getInstance().updateOldCurrency(cryptoModels);
                flag = true;

            } catch (Exception e) {
                System.out.println(" hata---------" + e);
            }
        }
//ftu




        refreshDatabase();
        setAdapterDatabase();


    }

    //adapters
//    private void setAdapterLiveData(ArrayList<CryptoModel> setModel) {
//        recyclerViewAdapter.notifyDataSetChanged();
//
//    }

    private void setAdapterDatabase() {

        recyclerViewAdapter.setDbCryptoList((ArrayList<CryptoModel>) DatabaseManager.getInstance().getAllCurrencies());
        recyclerViewAdapter.notifyDataSetChanged();
    }

    //destroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
    setAdapterDatabase();
        compositeDisposable.clear();
    }

    public void refreshDatabase() {


        System.out.println("*********************");
        for (CryptoModel cm : cryptoModels) {
            System.out.println("----------------");


                DatabaseManager.getInstance().updateTablePrice(cm.currency,cm.price);
                System.out.println(cm.oldPrice + cm.currency + cm.price);
        }
    }



}
//st-mc-feature-metinHa
    //    private final Handler handler = new Handler();
//    private Runnable getResponceAfterInterval = new Runnable() {
//        public void run() {
//            try
//            {
//                System.out.println("runnig");
//                loadData(); //RequestTask() is a AsynchTask requesting and handling response
//                recyclerViewAdapter.notifyDataSetChanged();
//
//            } catch (Exception e) {
//
//            }
//            handler.postDelayed(this, 5000);
//        }
//    };
