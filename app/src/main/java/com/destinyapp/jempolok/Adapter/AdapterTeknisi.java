package com.destinyapp.jempolok.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.media.session.PlaybackState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.destinyapp.jempolok.Model.DataModel;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

import java.util.ArrayList;
import java.util.List;

public class AdapterTeknisi extends RecyclerView.Adapter<AdapterTeknisi.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    String user,password,token,nama,foto,level,status;
    String idTeknisi;
    DB_Helper dbHelper;
    Boolean onClick=false;
    public AdapterTeknisi (Context ctx, List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_teknisi,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderData holderData, int posistion) {
        final DataModel dm = mList.get(posistion);
        holderData.Nama.setText(dm.getNama_teknisi());
        holderData.Status.setText(dm.getStatus_teknisi());
        holderData.Deskripsi.setText(dm.getDeskripsi_teknisi());
        dbHelper = new DB_Helper(ctx);
        Cursor cursor = dbHelper.checkTeknisi();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                idTeknisi = cursor.getString(0);
                if (idTeknisi.equals(dm.getId_teknisi())){
                    onClick=true;
                    holderData.LayoutCardView.setBackgroundColor(Color.RED);
                }
            }

        }
        holderData.LayoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClick){
                    holderData.LayoutCardView.setBackgroundColor(Color.RED);
                    dbHelper.saveIDTeknisi(dm.getId_teknisi());
                    onClick=false;
                }else{
                    holderData.LayoutCardView.setBackgroundColor(Color.WHITE);
                    dbHelper.deleteTeknisi(dm.getId_teknisi());
                    onClick=true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView Nama,Status,Deskripsi;
        DataModel dm;
        LinearLayout LayoutCardView;
        public HolderData(View v){
            super(v);
            Nama = v.findViewById(R.id.tvNama);
            Status = v.findViewById(R.id.tvStatus);
            Deskripsi = v.findViewById(R.id.tvDeskripsi);
            LayoutCardView = v.findViewById(R.id.LayoutCardView);
        }
    }
}

