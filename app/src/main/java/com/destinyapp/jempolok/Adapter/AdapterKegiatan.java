package com.destinyapp.jempolok.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.destinyapp.jempolok.Model.DataModel;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

import java.util.List;

public class AdapterKegiatan extends RecyclerView.Adapter<AdapterKegiatan.HolderData> {
    private List<DataModel> mList;
    private Context ctx;

    DB_Helper dbHelper;
    Boolean onClick=false;
    RecyclerView recyclerView;
    Musupadi musupadi;
    public AdapterKegiatan (Context ctx, List<DataModel> mList,RecyclerView recyclerView){
        this.ctx = ctx;
        this.mList = mList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_kategori,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderData holderData, int posistion) {
        final DataModel dm = mList.get(posistion);
        musupadi = new Musupadi();
        if (posistion % 2 == 0){
            holderData.LayoutCardView.setBackgroundResource(R.drawable.rounded_menu_primary);
        }else{
            holderData.LayoutCardView.setBackgroundResource(R.drawable.rounded_menu_red);
        }
        holderData.LayoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteKegiatan(String.valueOf(dm.getId_kegiatan()));
                Toast.makeText(ctx, dm.getNama_kegiatan()+" Dihapus", Toast.LENGTH_SHORT).show();
                musupadi.ReyclerView3(recyclerView,ctx,"Kegiatan");
            }
        });
        holderData.Nama.setText(dm.getNama_kegiatan());
        dbHelper = new DB_Helper(ctx);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView Nama,Status,Deskripsi;
        LinearLayout LayoutCardView;
        public HolderData(View v){
            super(v);
            Nama = v.findViewById(R.id.tvNama);
            LayoutCardView = v.findViewById(R.id.LayoutCardView);
        }
    }
}



