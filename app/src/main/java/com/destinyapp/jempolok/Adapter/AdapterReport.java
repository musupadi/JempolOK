package com.destinyapp.jempolok.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.destinyapp.jempolok.Model.DataModel;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.R;

import java.util.List;

public class AdapterReport extends RecyclerView.Adapter<AdapterReport.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    Musupadi method;
    boolean seen = false;
    public AdapterReport(Context ctx, List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_report,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterReport.HolderData holderData, int posistion) {
        final DataModel dm = mList.get(posistion);
        method=new Musupadi();
        holderData.Tanggal.setText(method.MagicDateChange(dm.getTgl_report()));
        holderData.Laporan.setText(dm.getNama_report());
        holderData.DetailLaporan.setText(dm.getDeskripsi_report());
        holderData.Kegiatan.setText(dm.getKegiatan_pemeliharaan());
        holderData.Lokasi.setText(dm.getLokasi());
        holderData.Kecamatan.setText(dm.getKecamatan_report());
        holderData.DetailLokasi.setText(dm.getDetail_lokasi());
        holderData.Status.setText(dm.getStatus_report());
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView Tanggal,Laporan,DetailLaporan,Kegiatan,Lokasi,Kecamatan,DetailLokasi,Status;
        DataModel dm;
        LinearLayout LayoutCardView;
        HolderData(View v){
            super(v);
            LayoutCardView = v.findViewById(R.id.LayoutCardView);
            Tanggal = v.findViewById(R.id.tvTanggalReport);
            Laporan = v.findViewById(R.id.tvNamaReport);
            DetailLaporan = v.findViewById(R.id.tvDeskripsiLaporan);
            Kegiatan = v.findViewById(R.id.tvKegiatanPemeliharaan);
            Lokasi = v.findViewById(R.id.tvLokasi);
            Kecamatan = v.findViewById(R.id.tvKecamatan);
            DetailLokasi = v.findViewById(R.id.tvDetailLokasi);
            Status = v.findViewById(R.id.tvStatus);
        }
    }
}



