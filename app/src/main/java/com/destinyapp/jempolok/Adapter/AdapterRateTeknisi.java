package com.destinyapp.jempolok.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.destinyapp.jempolok.Activity.LoginActivity;
import com.destinyapp.jempolok.Activity.SplashScreenActivity;
import com.destinyapp.jempolok.Model.DataModel;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.Model.Teknisi;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

import java.util.List;

public class AdapterRateTeknisi extends RecyclerView.Adapter<AdapterRateTeknisi.HolderData> {
    private List<Teknisi> mList;
    private Context ctx;
    DB_Helper dbHelper;
    String ID,Bintang,Review;
    RecyclerView recyclerView;
    Boolean enter = true;
    Musupadi musupadi;
    public AdapterRateTeknisi(Context ctx, List<Teknisi> mList,RecyclerView recyclerView) {
        this.ctx = ctx;
        this.mList = mList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_rate_teknisi, viewGroup, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderData holderData, int posistion) {
        final Teknisi dm = mList.get(posistion);
        dbHelper=new DB_Helper(ctx);
        musupadi = new Musupadi();
        final Animation animation = AnimationUtils.loadAnimation(ctx,R.anim.fadeout);
        holderData.Nama.setText(dm.nama_teknisi);
        Cursor cursors = dbHelper.checkAssign();
        if (cursors.getCount()>0){
            while (cursors.moveToNext()){
                ID = cursors.getString(0);
                Bintang = cursors.getString(1);
                Review = cursors.getString(2);
            }
        }
        dbHelper.saveTeknisiAssign(dm.id_teknisi,String.valueOf(Math.round(holderData.Rate.getRating())),holderData.Review.getText().toString());
        holderData.Nilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteAssign(dm.id_teknisi);
                dbHelper.saveTeknisiAssign(dm.id_teknisi,String.valueOf(Math.round(holderData.Rate.getRating())),holderData.Review.getText().toString());
//                dbHelper.updateAssign(dm.id_teknisi,String.valueOf(Math.round(holderData.Rate.getRating())),holderData.Review.getText().toString());
                holderData.LayoutCardView.startAnimation(animation);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        holderData.LayoutCardView.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder {
        TextView Nama;
        EditText Review;
        RatingBar Rate;
        DataModel dm;
        Button Nilai;
        CardView LayoutCardView;

        public HolderData(View v) {
            super(v);
            Nama = v.findViewById(R.id.tvNama);
            Rate = v.findViewById(R.id.rating);
            Review = v.findViewById(R.id.etIsiReview);
            LayoutCardView = v.findViewById(R.id.LayoutCardView);
            Nilai = v.findViewById(R.id.btnRate);
        }
    }
}
