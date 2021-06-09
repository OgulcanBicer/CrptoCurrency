package com.definex.retrofitjava.adapter;

import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.definex.retrofitjava.R;
import com.definex.retrofitjava.model.CryptoModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {


    private ArrayList<CryptoModel> cryptoList;
    private String[] colors = {"#FF0000","#00FF00","#0000FF","#FFFF00","#008080","#cdaa7d","#69b00b","#393e3f"};

    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoList) {
        this.cryptoList = cryptoList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());//xml leri sınıfa bağlamak

        View view = layoutInflater.inflate(R.layout.row_layout,parent,false);

        return new RowHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerViewAdapter.RowHolder holder, int position) {

        holder.bind(cryptoList.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }



    public class RowHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textPrice;

        public RowHolder(@NonNull @NotNull View itemView) {
            super(itemView);


        }
        public void bind(CryptoModel cryptoModel,String[] colors,Integer position){
            itemView.setBackgroundColor(Color.parseColor(colors[position%8]));
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);

            textName.setText(cryptoModel.currency);
            textPrice.setText(cryptoModel.price+"$");


        }





    }
}
