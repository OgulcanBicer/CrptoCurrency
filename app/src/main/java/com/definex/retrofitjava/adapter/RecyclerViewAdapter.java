package com.definex.retrofitjava.adapter;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.definex.retrofitjava.R;
import com.definex.retrofitjava.model.CryptoModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private final String[] colors = {"#364f36", "#205a86", "#515d5f", "#2c404c"};
//    public ArrayList<CryptoModel> cryptoList = new ArrayList<>();
    public ArrayList<CryptoModel> dbCryptoList = new ArrayList<>();
    TextView textName;
    TextView textPrice;
    TextView textPriceFromDB;
    String textArrow;





//    public void setCryptoList(ArrayList<CryptoModel> cryptoList) {
//        this.cryptoList = cryptoList;
//    }

    public void setDbCryptoList(ArrayList<CryptoModel> dbCryptoList) {
        this.dbCryptoList = dbCryptoList;
    }


//
//    public void setDbCryptoListWork(ArrayList<CryptoModel> cryptoList) {
//        this.dbCryptoList = cryptoList;
//        System.out.println("seter ici");
//        notifyDataSetChanged();
//    }
    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());//xml leri sınıfa bağlamak
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new RowHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RowHolder holder, int position) {
        holder.bind(dbCryptoList.get(position), colors, position);
    }

    @Override
    public int getItemCount() {
        return dbCryptoList.size();
    }//row say

    public class RowHolder extends RecyclerView.ViewHolder//textviewleri tanımlar. set eder
    {

        public RowHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
        public void bind(CryptoModel dbmodel, String[] colors, Integer position) {
            itemView.setBackgroundColor(Color.parseColor(colors[position % 4]));
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            textPriceFromDB = itemView.findViewById(R.id.textPriceFromDB);
            setColor(dbmodel);

            textName.setText(dbmodel.currency);
            textPrice.setText(textArrow + dbmodel.price + "$" + "  %" + (double)  (int) (((Double.parseDouble(dbmodel.price) - Double.parseDouble(dbmodel.oldPrice)) / Double.parseDouble(dbmodel.price) / 100) * 10000000) / 100);
            textPriceFromDB.setText(dbmodel.oldPrice + "$");
        }

        public void setColor(CryptoModel dbmodel) {

            textArrow = "";
            if (Double.parseDouble(dbmodel.price) > Double.parseDouble(dbmodel.oldPrice)) {
                textPrice.setTextColor(Color.GREEN);
                textArrow = ("↑ ");
            }

            if (Double.parseDouble(dbmodel.price) < Double.parseDouble(dbmodel.oldPrice)) {
                textPrice.setTextColor(Color.RED);
                textArrow = ("↓ ");
            }
            if (Double.parseDouble(dbmodel.price) == Double.parseDouble(dbmodel.oldPrice)) {
                textPrice.setTextColor(Color.WHITE);
            }
        }
    }
}


