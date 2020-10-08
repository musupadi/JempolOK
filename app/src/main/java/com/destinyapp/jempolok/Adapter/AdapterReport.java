package com.destinyapp.jempolok.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.destinyapp.jempolok.API.ApiRequest;
import com.destinyapp.jempolok.API.RetroServer;
import com.destinyapp.jempolok.Activity.CheckLaporanActivity;
import com.destinyapp.jempolok.Model.DataModel;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.Model.ResponseModel;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterReport extends RecyclerView.Adapter<AdapterReport.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    Musupadi method;
    boolean seen = false;
    String user,password,token,nama,foto,level,status;
    DB_Helper dbHelper;
    Dialog myDialog;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataModel> mItems = new ArrayList<>();
    Button Submit;
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
        myDialog = new Dialog(ctx);
        myDialog.setContentView(R.layout.dialog_teknisi);
        if (dm.getTgl_report()!=null){
            holderData.Tanggal.setText(method.MagicDateChange(dm.getTgl_report()));
        }else{
            holderData.Tanggal.setText("Tanggal Kosong");
        }
        dbHelper = new DB_Helper(ctx);
        Cursor cursor = dbHelper.checkUser();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                user = cursor.getString(0);
                password = cursor.getString(1);
                token = cursor.getString(2);
                nama = cursor.getString(3);
                foto = cursor.getString(4);
                level = cursor.getString(5);
                status = cursor.getString(6);
            }
        }
        if (!level.equals("pelaksana")){
            holderData.LayoutCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logic();
                }
            });
        }
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
    private void Logic(){
        myDialog.show();
        Submit = myDialog.findViewById(R.id.btnSubmit);
        recyclerView = myDialog.findViewById(R.id.recycler);
        mManager = new LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mManager);
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Data = api.Teknisi(method.AUTH(token));
        Data.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getStatusCode().equals("000")){
                    mItems=response.body().getData();
                    mAdapter = new AdapterTeknisi(ctx,mItems);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }else if(response.body().getStatusCode().equals("002")){
                    method.Login(ctx,user,password);
                    Logic();
                }else{
                    Toast.makeText(ctx, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


