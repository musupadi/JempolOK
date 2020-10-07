package com.destinyapp.jempolok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.destinyapp.jempolok.API.ApiRequest;
import com.destinyapp.jempolok.API.RetroServer;
import com.destinyapp.jempolok.Adapter.AdapterReport;
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

public class CheckLaporanActivity extends AppCompatActivity {
    RelativeLayout back;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataModel> mItems = new ArrayList<>();
    Musupadi musupadi = new Musupadi();
    DB_Helper dbHelper;
    String user,password,token,nama,foto,level,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_laporan);
        back = findViewById(R.id.relativeBack);
        recyclerView = findViewById(R.id.recycler);
        dbHelper = new DB_Helper(CheckLaporanActivity.this);
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
        Logic();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void Logic(){
        musupadi = new Musupadi();
        mManager = new LinearLayoutManager(CheckLaporanActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mManager);
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Data = api.Report(musupadi.AUTH(token));
        Data.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getStatusCode().equals("000")){
                    mItems=response.body().getData();
                    mAdapter = new AdapterReport(CheckLaporanActivity.this,mItems);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }else if(response.body().getStatusCode().equals("002")){
                    musupadi.Login(CheckLaporanActivity.this,user,password);
                    Logic();
                }else{
                    Toast.makeText(CheckLaporanActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(CheckLaporanActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}