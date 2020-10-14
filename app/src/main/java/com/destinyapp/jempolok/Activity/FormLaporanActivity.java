package com.destinyapp.jempolok.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapp.jempolok.API.ApiRequest;
import com.destinyapp.jempolok.API.RetroServer;
import com.destinyapp.jempolok.Adapter.AdapterKategori;
import com.destinyapp.jempolok.Adapter.AdapterKegiatan;
import com.destinyapp.jempolok.Adapter.Spinner.AdapterSpinnerKategori;
import com.destinyapp.jempolok.Adapter.Spinner.AdapterSpinnerKegiatan;
import com.destinyapp.jempolok.Model.DataModel;
import com.destinyapp.jempolok.Model.Model;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.Model.ResponseModel;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormLaporanActivity extends AppCompatActivity {
    //Dellaroy Logic
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_PICK_PHOTO = 2;
    private Uri mMediaUri;
    private static final int CAMERA_PIC_REQUEST = 1111;

    private static final String TAG =FormLaporanActivity.class.getSimpleName();

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri;

    private String mediaPath;

    private Button btnCapturePicture;

    private String mImageFileLocation = "";
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    ProgressDialog pDialog;
    String postBukti= "";
    String postBukti2= "";
    String postBukti3= "";
    String postBukti4= "";
    Button upload,upload2,upload3,upload4,submit;
    //ONCLICK
    Boolean Gambar = false;
    Boolean Gambar2 = false;
    Boolean Gambar3 = false;
    Boolean Gambar4 = false;
    ImageView gambar,gambar2,gambar3,gambar4;
    TextView tvGambar,tvGambar2,tvGambar3,tvGambar4;
    EditText Laporan,Deskripsi,Kegiatan,Alasan,DetailLokasi;
    Spinner Kecamatan,Lokasi,spKategori,spKegiatan;
    TextView Kec,Kat,Keg,kat,keg;
    String user,password,token,nama,foto,level,status;
    DB_Helper dbHelper;
    RelativeLayout back;
    CardView LinearUpload2,LinearUpload3,LinearUpload4;
    Button Tambah,Tambah2,Tambah3,TambahKategori,TambahKegiatan;
    int u = 1;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataModel> mItems = new ArrayList<>();
    private List<DataModel> mItems2 = new ArrayList<>();
    ArrayList<String> IDKategori = new ArrayList<String>();
    ArrayList<String> IDKegiatan = new ArrayList<String>();
    Musupadi musupadi;
    RecyclerView recyclerKategori,recyclerKegiatan;
    private AdapterSpinnerKategori aKategori;
    private AdapterSpinnerKegiatan aKegiatan;
    String idKategori,idKegiatan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_laporan);
        spKategori = findViewById(R.id.spKategori);
        spKegiatan = findViewById(R.id.spKegiatan);
        TambahKategori = findViewById(R.id.btnTambahKategori);
        TambahKegiatan = findViewById(R.id.btnTambahKegiatan);
        recyclerKategori = findViewById(R.id.recyclerKategori);
        recyclerKegiatan = findViewById(R.id.recyclerKegiatan);
        back = findViewById(R.id.relativeBack);
        upload = findViewById(R.id.btnUpload);
        upload2 = findViewById(R.id.btnUpload2);
        upload3 = findViewById(R.id.btnUpload3);
        upload4 = findViewById(R.id.btnUpload4);
        gambar = findViewById(R.id.ivGambar);
        gambar2 = findViewById(R.id.ivGambar2);
        gambar3 = findViewById(R.id.ivGambar3);
        gambar4 = findViewById(R.id.ivGambar4);
        tvGambar = findViewById(R.id.tvGambar);
        tvGambar2 = findViewById(R.id.tvGambar2);
        tvGambar3 = findViewById(R.id.tvGambar3);
        tvGambar4 = findViewById(R.id.tvGambar4);
        submit = findViewById(R.id.btnSubmit);
        Laporan = findViewById(R.id.etNamaLaporan);
        Deskripsi = findViewById(R.id.etDeskripsi);
        Kegiatan = findViewById(R.id.etKegiatanPemeliharaan);
        Alasan = findViewById(R.id.etAlasan);
        DetailLokasi = findViewById(R.id.etDetailLokasi);
        Kecamatan = findViewById(R.id.spKecamataan);
        Kec = findViewById(R.id.tvIdKecamatan);
        Kat = findViewById(R.id.tvIdKategori);
        Keg = findViewById(R.id.tvIdKegiatan);
        kat = findViewById(R.id.tvNamaKategori);
        keg = findViewById(R.id.tvNamaKegiatan);
        Lokasi = findViewById(R.id.spLokasi);
        Tambah = findViewById(R.id.btnTambah);
        Tambah2 = findViewById(R.id.btnTambah2);
        Tambah3 = findViewById(R.id.btnTambah3);
        LinearUpload2 = findViewById(R.id.linearUpload2);
        LinearUpload3 = findViewById(R.id.linearUpload3);
        LinearUpload4 = findViewById(R.id.linearUpload4);
        getKegiatan();
        aKegiatan = new AdapterSpinnerKegiatan(FormLaporanActivity.this,mItems);
        spKegiatan.setAdapter(aKegiatan);
        spKegiatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataModel clickedItem = (DataModel) parent.getItemAtPosition(position);
                int clickedItems = clickedItem.getId_kegiatan();
                Keg.setText(String.valueOf(clickedItems));
                keg.setText(clickedItem.getNama_kegiatan());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getKategori();
        aKategori = new AdapterSpinnerKategori(FormLaporanActivity.this,mItems);
        spKategori.setAdapter(aKategori);
        spKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataModel clickedItem = (DataModel) parent.getItemAtPosition(position);
                int clickedItems = clickedItem.getId_kategori();
                Kat.setText(String.valueOf(clickedItems));
                kat.setText(clickedItem.getNama_kategori());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TambahKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.saveIDKategori(Kat.getText().toString(),kat.getText().toString());
                ListKategori();
            }
        });
        TambahKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.saveIDKegiatan(Keg.getText().toString(),keg.getText().toString());
                ListKegiatan();
            }
        });
        Tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearUpload2.setVisibility(View.VISIBLE);
                u=2;
                Tambah.setVisibility(View.GONE);
            }
        });
        Tambah2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearUpload3.setVisibility(View.VISIBLE);
                u=3;
                Tambah2.setVisibility(View.GONE);
            }
        });
        Tambah3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearUpload4.setVisibility(View.VISIBLE);
                u=4;
                Tambah3.setVisibility(View.GONE);
            }
        });
        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gambar2 = true;
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
            }
        });
        upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gambar3 = true;
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
            }
        });
        upload4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gambar4 = true;
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
            }
        });
        dbHelper = new DB_Helper(FormLaporanActivity.this);
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
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gambar = true;
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checker();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormLaporanActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FormLaporanActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void Checker(){
        if (Laporan.getText().toString().isEmpty()){
            Toast.makeText(this, "Masukan Laporan", Toast.LENGTH_SHORT).show();
        }else if(Deskripsi.getText().toString().isEmpty()){
            Toast.makeText(this, "Masukan Deskripsi", Toast.LENGTH_SHORT).show();
        }else if(Kegiatan.getText().toString().isEmpty()){
            Toast.makeText(this, "Masukan Kegiatan", Toast.LENGTH_SHORT).show();
        }else if(DetailLokasi.getText().toString().isEmpty()){
            Toast.makeText(this, "Masukan Detail Lokasi", Toast.LENGTH_SHORT).show();
        }else if(Alasan.getText().toString().isEmpty()){
            Toast.makeText(this, "Masukan Alasan", Toast.LENGTH_SHORT).show();
        }else if(postBukti==""){
            Toast.makeText(this, "Masukan Gambar", Toast.LENGTH_SHORT).show();
        }else{
            Cursor cursor = dbHelper.checkKategori();
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    IDKategori.add(cursor.getString(0));
                }
            }
            cursor = dbHelper.checkKegiatan();
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    IDKegiatan.add(cursor.getString(0));
                }
            }
            if (IDKegiatan.size() < 1){
                Toast.makeText(this, "Harap pilih Kegiatan", Toast.LENGTH_SHORT).show();
            }else if(IDKategori.size() <1){
                Toast.makeText(this, "Harap pilih Kategori", Toast.LENGTH_SHORT).show();
            }else{
                Logic();
            }
        }
    }
    private void ListKategori(){
        recyclerKategori.setHasFixedSize(true);
//        recyclerKategori.setLayoutManager(new LinearLayoutManager(FormLaporanActivity.this));
        recyclerKategori.setLayoutManager(new GridLayoutManager(FormLaporanActivity.this, 3));
        dbHelper = new DB_Helper(FormLaporanActivity.this);
        AdapterKategori Adapter = new AdapterKategori(FormLaporanActivity.this,dbHelper.kategoriList(),recyclerKategori);
        recyclerKategori.setAdapter(Adapter);
    }
    private void ListKegiatan(){
        recyclerKegiatan.setHasFixedSize(true);
//        recyclerKategori.setLayoutManager(new LinearLayoutManager(FormLaporanActivity.this));
        recyclerKegiatan.setLayoutManager(new GridLayoutManager(FormLaporanActivity.this, 3));
        dbHelper = new DB_Helper(FormLaporanActivity.this);
        AdapterKegiatan Adapter = new AdapterKegiatan(FormLaporanActivity.this,dbHelper.kegiatanList(),recyclerKategori);
        recyclerKegiatan.setAdapter(Adapter);
    }
    private void Logic(){
        final ProgressDialog pd = new ProgressDialog(FormLaporanActivity.this);
        pd.setMessage("Sedang Menyimpan data ke Server");
        pd.setCancelable(false);
        pd.show();
        Musupadi musupadi = new Musupadi();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        if (u==1){
            File file = new File(postBukti);
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part partPhoto = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody);
            Call<ResponseModel> Data = api.Laporan(
                    musupadi.AUTH(token),
                    RequestBody.create(MediaType.parse("text/plain"),Laporan.getText().toString()),
                    partPhoto,
                    RequestBody.create(MediaType.parse("text/plain"),Deskripsi.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),Kegiatan.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),Lokasi.getSelectedItem().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),DetailLokasi.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),String.valueOf(Kecamatan.getSelectedItemId()+1)),
                    RequestBody.create(MediaType.parse("text/plain"),dateFormat.format(date)),
                    RequestBody.create(MediaType.parse("text/plain"),Alasan.getText().toString()),
                    IDKegiatan,
                    IDKategori
            );
            Data.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    try {
                        if (response.body().getStatusCode().equals("000")){
                            Toast.makeText(FormLaporanActivity.this, "Data Berhasil Di input", Toast.LENGTH_SHORT).show();
                            pd.hide();
                            Intent intent  = new Intent(FormLaporanActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }else if(response.body().getStatusCode().equals("002") || response.body().getStatusCode().equals("001")){
                            Login(pd);
                            Toast.makeText(FormLaporanActivity.this, "Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(FormLaporanActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            pd.hide();
                        }
                    }catch (Exception e){
                        Toast.makeText(FormLaporanActivity.this, "Terjadi kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                        pd.hide();
                    }

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(FormLaporanActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    pd.hide();
                }
            });
        }else if(u==2){
            File file = new File(postBukti);
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part partPhoto = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody);

            File file2 = new File(postBukti2);
            RequestBody fileReqBody2 = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part partPhoto2 = MultipartBody.Part.createFormData("photo[]", file2.getName(), fileReqBody2);
            Call<ResponseModel> Data = api.Laporan2(
                    musupadi.AUTH(token),
                    RequestBody.create(MediaType.parse("text/plain"),Laporan.getText().toString()),
                    partPhoto,
                    partPhoto2,
                    RequestBody.create(MediaType.parse("text/plain"),Deskripsi.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),Kegiatan.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),Lokasi.getSelectedItem().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),DetailLokasi.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),String.valueOf(Kecamatan.getSelectedItemId()+1)),
                    RequestBody.create(MediaType.parse("text/plain"),dateFormat.format(date)),
                    RequestBody.create(MediaType.parse("text/plain"),Alasan.getText().toString()),
                    IDKegiatan,
                    IDKategori
            );
            Data.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    try {
                        if (response.body().getStatusCode().equals("000")){
                            Toast.makeText(FormLaporanActivity.this, "Data Berhasil Di input", Toast.LENGTH_SHORT).show();
                            pd.hide();
                            Intent intent  = new Intent(FormLaporanActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }else if(response.body().getStatusCode().equals("002")){
                            Login(pd);
                            Toast.makeText(FormLaporanActivity.this, "Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(FormLaporanActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            pd.hide();
                        }
                    }catch (Exception e){
                        Toast.makeText(FormLaporanActivity.this, "Terjadi kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                        pd.hide();
                    }

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(FormLaporanActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    pd.hide();
                }
            });
        }else if(u==3){
            File file = new File(postBukti);
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part partPhoto = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody);

            File file2 = new File(postBukti2);
            RequestBody fileReqBody2 = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part partPhoto2 = MultipartBody.Part.createFormData("photo[]", file2.getName(), fileReqBody2);

            File file3 = new File(postBukti3);
            RequestBody fileReqBody3 = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part partPhoto3 = MultipartBody.Part.createFormData("photo[]", file3.getName(), fileReqBody3);

            Call<ResponseModel> Data = api.Laporan3(
                    musupadi.AUTH(token),
                    RequestBody.create(MediaType.parse("text/plain"),Laporan.getText().toString()),
                    partPhoto,
                    partPhoto2,
                    partPhoto3,
                    RequestBody.create(MediaType.parse("text/plain"),Deskripsi.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),Kegiatan.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),Lokasi.getSelectedItem().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),DetailLokasi.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),String.valueOf(Kecamatan.getSelectedItemId()+1)),
                    RequestBody.create(MediaType.parse("text/plain"),dateFormat.format(date)),
                    RequestBody.create(MediaType.parse("text/plain"),Alasan.getText().toString()),
                    IDKegiatan,
                    IDKategori
            );
            Data.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    try {
                        if (response.body().getStatusCode().equals("000")){
                            Toast.makeText(FormLaporanActivity.this, "Data Berhasil Di input", Toast.LENGTH_SHORT).show();
                            pd.hide();
                            Intent intent  = new Intent(FormLaporanActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }else if(response.body().getStatusCode().equals("002")){
                            Login(pd);
                            Toast.makeText(FormLaporanActivity.this, "Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(FormLaporanActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            pd.hide();
                        }
                    }catch (Exception e){
                        Toast.makeText(FormLaporanActivity.this, "Terjadi kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                        pd.hide();
                    }

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(FormLaporanActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    pd.hide();
                }
            });
        }else if(u==4){
            File file = new File(postBukti);
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part partPhoto = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody);

            File file2 = new File(postBukti2);
            RequestBody fileReqBody2 = RequestBody.create(MediaType.parse("image/*"), file2);
            MultipartBody.Part partPhoto2 = MultipartBody.Part.createFormData("photo[]", file2.getName(), fileReqBody2);

            File file3 = new File(postBukti3);
            RequestBody fileReqBody3 = RequestBody.create(MediaType.parse("image/*"), file3);
            MultipartBody.Part partPhoto3 = MultipartBody.Part.createFormData("photo[]", file3.getName(), fileReqBody3);

            File file4 = new File(postBukti4);
            RequestBody fileReqBody4 = RequestBody.create(MediaType.parse("image/*"), file4);
            MultipartBody.Part partPhoto4 = MultipartBody.Part.createFormData("photo[]", file4.getName(), fileReqBody4);

            Call<ResponseModel> Data = api.Laporan4(
                    musupadi.AUTH(token),
                    RequestBody.create(MediaType.parse("text/plain"),Laporan.getText().toString()),
                    partPhoto,
                    partPhoto2,
                    partPhoto3,
                    partPhoto4,
                    RequestBody.create(MediaType.parse("text/plain"),Deskripsi.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),Kegiatan.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),Lokasi.getSelectedItem().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),DetailLokasi.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),String.valueOf(Kecamatan.getSelectedItemId()+1)),
                    RequestBody.create(MediaType.parse("text/plain"),dateFormat.format(date)),
                    RequestBody.create(MediaType.parse("text/plain"),Alasan.getText().toString()),
                    IDKegiatan,
                    IDKategori
            );
            Data.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    try {
                        if (response.body().getStatusCode().equals("000")){
                            Toast.makeText(FormLaporanActivity.this, "Data Berhasil Di input", Toast.LENGTH_SHORT).show();
                            pd.hide();
                            Intent intent  = new Intent(FormLaporanActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }else if(response.body().getStatusCode().equals("002")){
                            Login(pd);
                            Toast.makeText(FormLaporanActivity.this, "Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(FormLaporanActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            pd.hide();
                        }
                    }catch (Exception e){
                        Toast.makeText(FormLaporanActivity.this, "Terjadi kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                        pd.hide();
                    }

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(FormLaporanActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    pd.hide();
                }
            });
        }
//        Call<ResponseModel> Upload = api.UploadBukti()
    }
    private void getKategori(){
        musupadi = new Musupadi();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getProvinsi = api.Kategori(musupadi.AUTH(token));
        getProvinsi.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatusCode().equals("000")){
                        mItems=response.body().getData();
                        AdapterSpinnerKategori adapter = new AdapterSpinnerKategori(FormLaporanActivity.this,mItems);
                        spKategori.setAdapter(adapter);
                    }else if(response.body().getStatusCode().equals("002") || response.body().getStatusCode().equals("001")){
                        musupadi.Login(FormLaporanActivity.this,user,password);
                        getKategori();
                    }else{
                        Toast.makeText(FormLaporanActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(FormLaporanActivity.this, "Terjadi kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(FormLaporanActivity.this,"Koneksi Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getKegiatan(){
        musupadi = new Musupadi();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getProvinsi = api.Kegiatan(musupadi.AUTH(token));
        getProvinsi.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatusCode().equals("000")){
                        mItems=response.body().getData();
                        AdapterSpinnerKegiatan adapter = new AdapterSpinnerKegiatan(FormLaporanActivity.this,mItems);
                        spKegiatan.setAdapter(adapter);
                    }else if(response.body().getStatusCode().equals("002") || response.body().getStatusCode().equals("001")){
                        musupadi.Login(FormLaporanActivity.this,user,password);
                        getKegiatan();
                    }else{
                        Toast.makeText(FormLaporanActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(FormLaporanActivity.this, "Terjadi kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(FormLaporanActivity.this,"Koneksi Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Login(final ProgressDialog pd){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> login =api.login(user,password);
        login.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatusCode().equals("000")){
                        dbHelper.Logout();
                        dbHelper.saveUser(user,password,response.body().getData().get(0).accessToken,response.body().getData().get(0).namaUser,response.body().getData().get(0).fotoUser,response.body().getData().get(0).levelUser,response.body().getData().get(0).statusUser);
                        Logic();
                    }else{
                        Toast.makeText(FormLaporanActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                        pd.hide();
                    }
                }catch (Exception e){
                    Toast.makeText(FormLaporanActivity.this, "Terjadi Kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                    pd.hide();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                Toast.makeText(LoginActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                Toast.makeText(FormLaporanActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                pd.hide();
            }
        });
    }
    //Dellaroy Logic
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }
    File createImageFile() throws IOException {
        Logger.getAnonymousLogger().info("Generating the image - method started");

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp;
        // Here we specify the environment location and the exact path where we want to save the so-created file
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app");
        Logger.getAnonymousLogger().info("Storage directory set");

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir();

        // Here we create the file using a prefix, a suffix and a directory
        File image = new File(storageDirectory, imageFileName + ".jpg");
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation
        Logger.getAnonymousLogger().info("File name and path set");

        mImageFileLocation = image.getAbsolutePath();
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
            if (data != null) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);

                // Set the Image in ImageView for Previewing the Media

//                    imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
                if(Gambar){
                    postBukti = mediaPath;
                    String filename=postBukti.substring(postBukti.lastIndexOf("/")+1);
                    gambar.setVisibility(View.VISIBLE);
                    tvGambar.setVisibility(View.VISIBLE);
                    gambar.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    tvGambar.setText(filename);
                    Gambar=false;
                    Toast.makeText(this, filename, Toast.LENGTH_SHORT).show();
                }else if(Gambar2){
                    postBukti2 = mediaPath;
                    String filename=postBukti2.substring(postBukti2.lastIndexOf("/")+1);
                    gambar2.setVisibility(View.VISIBLE);
                    tvGambar2.setVisibility(View.VISIBLE);
                    gambar2.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    tvGambar2.setText(filename);
                    Gambar2=false;
                    Toast.makeText(this, filename, Toast.LENGTH_SHORT).show();
                }else if(Gambar3){
                    postBukti3 = mediaPath;
                    String filename=postBukti3.substring(postBukti3.lastIndexOf("/")+1);
                    gambar3.setVisibility(View.VISIBLE);
                    tvGambar3.setVisibility(View.VISIBLE);
                    gambar3.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    tvGambar3.setText(filename);
                    Gambar3=false;
                    Toast.makeText(this, filename, Toast.LENGTH_SHORT).show();
                }else if(Gambar4){
                    postBukti4 = mediaPath;
                    String filename=postBukti4.substring(postBukti4.lastIndexOf("/")+1);
                    gambar4.setVisibility(View.VISIBLE);
                    tvGambar4.setVisibility(View.VISIBLE);
                    gambar4.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    tvGambar4.setText(filename);
                    Gambar4=false;
                    Toast.makeText(this, filename, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}