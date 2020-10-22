package com.destinyapp.jempolok.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.destinyapp.jempolok.Activity.MainActivity;
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
    String idTeknisi;
    Dialog myDialog;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataModel> mItems = new ArrayList<>();
    Button Submit;
    ArrayList<String> IDTeknisi = new ArrayList<String>();
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
        Animation animation = AnimationUtils.loadAnimation(ctx,R.anim.fadein);
        holderData.LayoutCardView.startAnimation(animation);
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
        final String idReport= dm.getId_report();
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
        if (level.equals("pelaksana")){
            holderData.LayoutCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dm.getStatus_report().equals("progress 2")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                        builder.setMessage("Laporan Selesai ?")
                                .setCancelable(false)
                                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
                                        Call<ResponseModel> Data = api.Asign(method.AUTH(token),dm.getId_report(),"2");
                                        Data.enqueue(new Callback<ResponseModel>() {
                                            @Override
                                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                                if (response.body().getStatusCode().equals("000")){
                                                    Intent intent = new Intent(ctx,MainActivity.class);
                                                    ctx.startActivity(intent);
                                                }else if(response.body().getStatusCode().equals("002") || response.body().getStatusCode().equals("001")){
                                                    method.Login(ctx,user,password);
                                                    Logic(idReport);
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
                                })
                                .setNegativeButton("Belum", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                                //Set your icon here
                                .setTitle("Perhatian !!!")
                                .setIcon(R.drawable.ic_baseline_close_24_red);
                        AlertDialog alert = builder.create();
                        alert.show();
                    }else if (dm.getStatus_report().equals("progress 3")){
                        Toast.makeText(ctx, "Laporan Sudah Selesai", Toast.LENGTH_SHORT).show();
                    }else{
                        Logic(idReport);
                    }
                }
            });
        }
        holderData.Laporan.setText(dm.getNama_report());
        holderData.DetailLaporan.setText(dm.getDeskripsi_report());
        holderData.Kegiatan.setText(dm.getKegiatan_pemeliharaan());
        holderData.Lokasi.setText(dm.getLokasi());
        holderData.Kecamatan.setText(dm.getKecamatan_report());
        holderData.DetailLokasi.setText(dm.getDetail_lokasi());
        if (dm.getStatus_report().equals("progress 1")){
            holderData.Status.setText("On Progress - 1");
            holderData.LinearBGStatus.setBackgroundResource(R.drawable.round_background_yellow);
            holderData.tvStatus.setVisibility(View.VISIBLE);
            holderData.ivStatus.setVisibility(View.GONE);
        }else if(dm.getStatus_report().equals("progress 2")){
            holderData.Status.setText("On Progress - 2");
            holderData.LinearBGStatus.setBackgroundResource(R.drawable.round_background_yellow);
            holderData.tvStatus.setVisibility(View.GONE);
            holderData.ivStatus.setVisibility(View.GONE);
        }else if(dm.getStatus_report().equals("progress 3")){
            holderData.Status.setText("On Progress - 3");
            holderData.LinearBGStatus.setBackgroundResource(R.drawable.round_background_yellow);
            holderData.tvStatus.setVisibility(View.GONE);
            holderData.ivStatus.setVisibility(View.VISIBLE);
        }else if(dm.getStatus_report().equals("pending 1")){
            holderData.Status.setText("Pending");
            holderData.LinearBGStatus.setBackgroundResource(R.drawable.round_background_red);
            holderData.tvStatus.setVisibility(View.GONE);
            holderData.ivStatus.setVisibility(View.GONE);
        }else if(dm.getStatus_report().equals("reject")){
            holderData.Status.setText("Reject");
            holderData.LinearBGStatus.setBackgroundResource(R.drawable.round_background_black);
            holderData.tvStatus.setVisibility(View.GONE);
            holderData.ivStatus.setVisibility(View.GONE);
        }else if(dm.getStatus_report().equals("selesai")){
            holderData.Status.setText("Selesai");
            holderData.LinearBGStatus.setBackgroundResource(R.drawable.round_background_green);
            holderData.tvStatus.setVisibility(View.GONE);
            holderData.ivStatus.setVisibility(View.GONE);
        }
//        holderData.Status.setText(dm.getStatus_report());
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView Tanggal,Laporan,DetailLaporan,Kegiatan,Lokasi,Kecamatan,DetailLokasi,Status,tvStatus;
        DataModel dm;
        ImageView ivStatus;
        LinearLayout LayoutCardView,LinearBGStatus;
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
            tvStatus = v.findViewById(R.id.tvinnerText);
            ivStatus = v.findViewById(R.id.ivInnerText);
            LinearBGStatus = v.findViewById(R.id.linearBGStatus);
        }
    }
    private void Logic(final String idReport){
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
                    Logic(idReport);
                }else{
                    Toast.makeText(ctx, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.checkTeknisi();
                if (cursor.getCount()>0){
                    while (cursor.moveToNext()){
                        idTeknisi = cursor.getString(0);
                        IDTeknisi.add(idTeknisi);
                    }
                }
                dbHelper.resetTeknisi();
                final ProgressDialog pd = new ProgressDialog(ctx);
                pd.setMessage("Sedang Mengupdate data");
                pd.setCancelable(false);
                pd.show();
                ApiRequest api2 = RetroServer.getClient().create(ApiRequest.class);
                Call<ResponseModel> Data2 = api2.AsignTechnician(method.AUTH(token),idReport,"1",IDTeknisi);
                Data2.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        try {
                            if (response.body().getStatusCode().equals("000")){
                                Toast.makeText(ctx, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            }else if(response.body().getStatusCode().equals("002")){
                                method.Login(ctx,user,password);
                                Logic(idReport);
                            }else{
                                Toast.makeText(ctx, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(ctx, CheckLaporanActivity.class);
                            ctx.startActivity(intent);
                            pd.hide();
                        }catch (Exception e){
                            Toast.makeText(ctx, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                            pd.hide();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                        pd.hide();
                    }
                });

            }
        });
    }
}



