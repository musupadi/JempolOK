package com.destinyapp.jempolok.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.Model.ResponseModel;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    Button upload,submit;
    //ONCLICK
    Boolean Gambar = false;
    ImageView gambar;
    TextView tvGambar;
    EditText Laporan,Deskripsi,Kegiatan,Alasan,DetailLokasi;
    Spinner Kecamatan,Lokasi;
    TextView Kec;
    String user,password,token,nama,foto,level,status;
    DB_Helper dbHelper;
    RelativeLayout back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_laporan);
        back = findViewById(R.id.relativeBack);
        upload = findViewById(R.id.btnUpload);
        gambar = findViewById(R.id.ivGambar);
        tvGambar = findViewById(R.id.tvGambar);
        submit = findViewById(R.id.btnSubmit);
        Laporan = findViewById(R.id.etNamaLaporan);
        Deskripsi = findViewById(R.id.etDeskripsi);
        Kegiatan = findViewById(R.id.etKegiatanPemeliharaan);
        Alasan = findViewById(R.id.etAlasan);
        DetailLokasi = findViewById(R.id.etDetailLokasi);
        Kecamatan = findViewById(R.id.spKecamataan);
        Kec = findViewById(R.id.tvIdKecamatan);
        Lokasi = findViewById(R.id.spLokasi);
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
                onBackPressed();
            }
        });
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
            Logic();
        }
    }
    private void Logic(){
        final ProgressDialog pd = new ProgressDialog(FormLaporanActivity.this);
        pd.setMessage("Sedang Menyimpan data ke Server");
        pd.setCancelable(false);
        pd.show();
        File file = new File(postBukti);
        Musupadi musupadi = new Musupadi();
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part partPhoto = MultipartBody.Part.createFormData("photo", file.getName(), fileReqBody);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
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
                RequestBody.create(MediaType.parse("text/plain"),Alasan.getText().toString())
        );
        Data.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatusCode().equals("000")){
                        Toast.makeText(FormLaporanActivity.this, "Data Berhasil Di input", Toast.LENGTH_SHORT).show();
                        pd.hide();
                        Intent intent  = new Intent(FormLaporanActivity.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        Login(pd);
                    }
                }catch (Exception e){
                    Toast.makeText(FormLaporanActivity.this, "Terjadi kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                    pd.hide();
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Login(pd);
            }
        });
//        Call<ResponseModel> Upload = api.UploadBukti()
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
                }
            }
        }
    }
}