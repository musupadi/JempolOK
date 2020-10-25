package com.destinyapp.jempolok.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.destinyapp.jempolok.API.ApiRequest;
import com.destinyapp.jempolok.API.RetroServer;
import com.destinyapp.jempolok.Activity.AssignFinishActivity;
import com.destinyapp.jempolok.Activity.CheckLaporanActivity;
import com.destinyapp.jempolok.Activity.FormLaporanActivity;
import com.destinyapp.jempolok.Activity.MainActivity;
import com.destinyapp.jempolok.BuildConfig;
import com.destinyapp.jempolok.Model.DataModel;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.Model.ResponseModel;
import com.destinyapp.jempolok.Model.Teknisi;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

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
    Dialog myDialog,TeknisiDialog;
    RecyclerView recyclerView,rv;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataModel> mItems = new ArrayList<>();
    private List<Teknisi> mItem = new ArrayList<>();
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
//        Animation animation = AnimationUtils.loadAnimation(ctx,R.anim.fadein);
//        holderData.LayoutCardView.startAnimation(animation);
        method=new Musupadi();
        myDialog = new Dialog(ctx);
        TeknisiDialog = new Dialog(ctx);
        myDialog.setContentView(R.layout.dialog_teknisi);
        TeknisiDialog.setContentView(R.layout.dialog_asign_finish);
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
                        dbHelper.resetAssign();
                        Intent intent = new Intent(ctx, AssignFinishActivity.class);
                        intent.putExtra( "id_report", dm.getId_report());
                        ctx.startActivity(intent);
                    }else if (dm.getStatus_report().equals("progress 3")){
                        Toast.makeText(ctx, "Laporan Sudah Selesai Menunggu Penilaian Admin", Toast.LENGTH_SHORT).show();
                    }else if(dm.getStatus_report().equals("selesai")){
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
        String URL = method.BASE_URL();
        Glide.with(ctx)
                .load(URL+"/"+dm.getGambar())
                .into(holderData.gambar);
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
        ImageView gambar;
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
            gambar = v.findViewById(R.id.ivBukti);
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



