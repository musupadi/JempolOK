package com.destinyapp.jempolok.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.destinyapp.jempolok.API.ApiRequest;
import com.destinyapp.jempolok.API.RetroServer;
import com.destinyapp.jempolok.Adapter.AdapterRateTeknisi;
import com.destinyapp.jempolok.Adapter.AdapterTeknisi;
import com.destinyapp.jempolok.BuildConfig;
import com.destinyapp.jempolok.Model.DataModel;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.Model.ResponseModel;
import com.destinyapp.jempolok.Model.Teknisi;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

import java.io.File;
import java.io.IOException;
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

public class AssignFinishActivity extends AppCompatActivity {
    Teknisi teknisi;
    RelativeLayout back;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataModel> mItems = new ArrayList<>();
    Musupadi musupadi = new Musupadi();
    DB_Helper dbHelper;
    String user,password,token,nama,foto,level,status;
    LottieAnimationView lottie;
    LinearLayout loading;
    TextView tvLoading;
    String idReport;
    Musupadi method;
    Button Submit;
    ArrayList<String> ID_TEKNISI = new ArrayList<String>();
    ArrayList<String> BINTANG = new ArrayList<String>();
    ArrayList<String> REVIEW = new ArrayList<String>();
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
    Button upload,upload2,upload3,upload4,hapus,hapus2,hapus3,hapus4,submit;
    Button Tambah1,Tambah2,Tambah3;
    //ONCLICK
    Boolean Gambar = false;
    Boolean Gambar2 = false;
    Boolean Gambar3 = false;
    Boolean Gambar4 = false;
    ImageView gambar,gambar2,gambar3,gambar4;
    TextView tvGambar,tvGambar2,tvGambar3,tvGambar4;
    CardView card1,card2,card3,card4;
    int u = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_finish);
        recyclerView = findViewById(R.id.recycler);
        back = findViewById(R.id.relativeBack);
        upload = findViewById(R.id.btnUpload);
        upload2 = findViewById(R.id.btnUpload2);
        upload3 = findViewById(R.id.btnUpload3);
        upload4 = findViewById(R.id.btnUpload4);
        hapus = findViewById(R.id.btnHapusGambar);
        hapus2 = findViewById(R.id.btnHapusGambar2);
        hapus3 = findViewById(R.id.btnHapusGambar3);
        hapus4 = findViewById(R.id.btnHapusGambar4);
        gambar = findViewById(R.id.ivGambar);
        gambar2 = findViewById(R.id.ivGambar2);
        gambar3 = findViewById(R.id.ivGambar3);
        gambar4 = findViewById(R.id.ivGambar4);
        tvGambar = findViewById(R.id.tvGambar);
        tvGambar2 = findViewById(R.id.tvGambar2);
        tvGambar3 = findViewById(R.id.tvGambar3);
        tvGambar4 = findViewById(R.id.tvGambar4);
        Submit = findViewById(R.id.btnSubmit);
        Tambah1 = findViewById(R.id.btnTambah);
        Tambah2 = findViewById(R.id.btnTambah2);
        Tambah3 = findViewById(R.id.btnTambah3);
        card2 = findViewById(R.id.cardUpload2);
        card3 = findViewById(R.id.cardUpload3);
        card4 = findViewById(R.id.cardUpload4);
        Intent intent = getIntent();
        dbHelper = new DB_Helper(AssignFinishActivity.this);
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
        idReport = intent.getExtras().getString("id_report");
        method=new Musupadi();
        Tambah1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card2.setVisibility(View.VISIBLE);
                u=2;
                Tambah1.setVisibility(View.GONE);
            }
        });
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvGambar.setText("");
                Gambar = false;
                gambar.setVisibility(View.GONE);
                tvGambar.setVisibility(View.GONE);
            }
        });
        Tambah2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card3.setVisibility(View.VISIBLE);
                u=3;
                Tambah2.setVisibility(View.GONE);
            }
        });
        hapus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u=1;
                tvGambar2.setText("");
                Gambar2 = false;
                gambar2.setVisibility(View.GONE);
                tvGambar2.setVisibility(View.GONE);
                card2.setVisibility(View.GONE);
                Tambah1.setVisibility(View.VISIBLE);
            }
        });
        Tambah3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card4.setVisibility(View.VISIBLE);
                u=4;
                Tambah3.setVisibility(View.GONE);
            }
        });
        hapus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u=2;
                tvGambar3.setText("");
                Gambar3 = false;
                gambar3.setVisibility(View.GONE);
                tvGambar3.setVisibility(View.GONE);
                card3.setVisibility(View.GONE);
                Tambah2.setVisibility(View.VISIBLE);
            }
        });
        hapus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u=3;
                tvGambar4.setText("");
                Gambar4 = false;
                gambar4.setVisibility(View.GONE);
                tvGambar4.setVisibility(View.GONE);
                card4.setVisibility(View.GONE);
                Tambah3.setVisibility(View.VISIBLE);
            }
        });
        Logic();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UPLOAD("1");
            }
        });
        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UPLOAD("2");
            }
        });
        upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UPLOAD("3");
            }
        });
        upload4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UPLOAD("4");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursors = dbHelper.checkAssign();
                String ID,Bintang,Review;
                if (cursors.getCount()>0){
                    while (cursors.moveToNext()){
                        ID_TEKNISI.add(cursors.getString(0));
                        BINTANG.add(cursors.getString(1));
                        REVIEW.add(cursors.getString(2));
                    }
                }
                if (u == 1){
                    final ProgressDialog pd = new ProgressDialog(AssignFinishActivity.this);
                    pd.setMessage("Sedang Menyimpan data ke Server");
                    pd.setCancelable(false);
                    pd.show();
                    File file = new File(postBukti);
                    RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part partPhoto = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody);
                    ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
                    Call<ResponseModel> Data = api.AssignSucces(
                            method.AUTH(token),
                            RequestBody.create(MediaType.parse("text/plain"),idReport),
                            RequestBody.create(MediaType.parse("text/plain"),"2"),
                            partPhoto,
                            ID_TEKNISI,
                            BINTANG,
                            REVIEW);
                    Data.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            RESPONSES(response.body().getStatusCode(),pd,response.body().getStatusMessage());
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(AssignFinishActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if (u==2){
                    final ProgressDialog pd = new ProgressDialog(AssignFinishActivity.this);
                    pd.setMessage("Sedang Menyimpan data ke Server");
                    pd.setCancelable(false);
                    pd.show();
                    File file = new File(postBukti);
                    RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part partPhoto = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody);

                    File file2 = new File(postBukti2);
                    RequestBody fileReqBody2 = RequestBody.create(MediaType.parse("image/*"), file2);
                    MultipartBody.Part partPhoto2 = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody2);

                    ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
                    Call<ResponseModel> Data = api.AssignSucces(
                            method.AUTH(token),
                            RequestBody.create(MediaType.parse("text/plain"),idReport),
                            RequestBody.create(MediaType.parse("text/plain"),"2"),
                            partPhoto,
                            partPhoto2,
                            ID_TEKNISI,
                            BINTANG,
                            REVIEW);
                    Data.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            RESPONSES(response.body().getStatusCode(),pd,response.body().getStatusMessage());
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(AssignFinishActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if (u==3){
                    final ProgressDialog pd = new ProgressDialog(AssignFinishActivity.this);
                    pd.setMessage("Sedang Menyimpan data ke Server");
                    pd.setCancelable(false);
                    pd.show();

                    File file = new File(postBukti);
                    RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part partPhoto = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody);

                    File file2 = new File(postBukti2);
                    RequestBody fileReqBody2 = RequestBody.create(MediaType.parse("image/*"), file2);
                    MultipartBody.Part partPhoto2 = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody2);

                    File file3 = new File(postBukti3);
                    RequestBody fileReqBody3 = RequestBody.create(MediaType.parse("image/*"), file3);
                    MultipartBody.Part partPhoto3 = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody3);

                    ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
                    Call<ResponseModel> Data = api.AssignSucces(
                            method.AUTH(token),
                            RequestBody.create(MediaType.parse("text/plain"),idReport),
                            RequestBody.create(MediaType.parse("text/plain"),"2"),
                            partPhoto,
                            partPhoto2,
                            partPhoto3,
                            ID_TEKNISI,
                            BINTANG,
                            REVIEW);
                    Data.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            RESPONSES(response.body().getStatusCode(),pd,response.body().getStatusMessage());
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(AssignFinishActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if (u==4){
                    final ProgressDialog pd = new ProgressDialog(AssignFinishActivity.this);
                    pd.setMessage("Sedang Menyimpan data ke Server");
                    pd.setCancelable(false);
                    pd.show();

                    File file = new File(postBukti);
                    RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part partPhoto = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody);

                    File file2 = new File(postBukti2);
                    RequestBody fileReqBody2 = RequestBody.create(MediaType.parse("image/*"), file2);
                    MultipartBody.Part partPhoto2 = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody2);

                    File file3 = new File(postBukti3);
                    RequestBody fileReqBody3 = RequestBody.create(MediaType.parse("image/*"), file3);
                    MultipartBody.Part partPhoto3 = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody3);

                    File file4 = new File(postBukti4);
                    RequestBody fileReqBody4 = RequestBody.create(MediaType.parse("image/*"), file4);
                    MultipartBody.Part partPhoto4 = MultipartBody.Part.createFormData("photo[]", file.getName(), fileReqBody4);

                    ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
                    Call<ResponseModel> Data = api.AssignSucces(
                            method.AUTH(token),
                            RequestBody.create(MediaType.parse("text/plain"),idReport),
                            RequestBody.create(MediaType.parse("text/plain"),"2"),
                            partPhoto,
                            partPhoto2,
                            partPhoto3,
                            partPhoto4,
                            ID_TEKNISI,
                            BINTANG,
                            REVIEW);
                    Data.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            RESPONSES(response.body().getStatusCode(),pd,response.body().getStatusMessage());
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(AssignFinishActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private void RESPONSES(String Code,ProgressDialog pd,String Message){
        try {
            if (Code.equals("000")){
                Toast.makeText(AssignFinishActivity.this, "Data Berhasil Di input", Toast.LENGTH_SHORT).show();
                pd.hide();
                Intent intent  = new Intent(AssignFinishActivity.this,MainActivity.class);
                startActivity(intent);
            }else if(Code.equals("002")){
                musupadi.Login(AssignFinishActivity.this,user,password);
                Toast.makeText(AssignFinishActivity.this, "Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(AssignFinishActivity.this, Message, Toast.LENGTH_SHORT).show();
                pd.hide();
            }
        }catch (Exception e){
            Toast.makeText(AssignFinishActivity.this, "Terjadi kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
            pd.hide();
        }
    }
    private void UPLOAD(String U){
        if (U.equals("1")){
            new MaterialDialog.Builder(AssignFinishActivity.this)
                    .title("Pilih Gambar")
                    .items(R.array.uploadImages)
                    .itemsIds(R.array.itemIds)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            switch (which) {
                                case 0:
                                    Gambar = true;
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
                                    gambar.setVisibility(View.VISIBLE);
                                    tvGambar.setVisibility(View.VISIBLE);
                                    break;
                                case 1:
                                    Gambar = true;
                                    captureImage();
                                    gambar.setVisibility(View.VISIBLE);
                                    tvGambar.setVisibility(View.VISIBLE);
                                    break;
                                case 2:
                                    gambar.setImageResource(R.drawable.ic_launcher_background);
                                    gambar.setVisibility(View.GONE);
                                    tvGambar.setVisibility(View.GONE);
                                    Gambar = false;
                                    break;
                            }
                        }
                    })
                    .show();
        }else if (U.equals("2")){
            new MaterialDialog.Builder(AssignFinishActivity.this)
                    .title("Pilih Gambar")
                    .items(R.array.uploadImages)
                    .itemsIds(R.array.itemIds)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            switch (which) {
                                case 0:
                                    Gambar2 = true;
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
                                    gambar2.setVisibility(View.VISIBLE);
                                    tvGambar2.setVisibility(View.VISIBLE);
                                    break;
                                case 1:
                                    Gambar2 = true;
                                    captureImage();
                                    gambar2.setVisibility(View.VISIBLE);
                                    tvGambar2.setVisibility(View.VISIBLE);
                                    break;
                                case 2:
                                    gambar2.setImageResource(R.drawable.ic_launcher_background);
                                    gambar2.setVisibility(View.GONE);
                                    tvGambar2.setVisibility(View.GONE);
                                    Gambar2 = false;
                                    break;
                            }
                        }
                    })
                    .show();
        }else if (U.equals("3")){
            new MaterialDialog.Builder(AssignFinishActivity.this)
                    .title("Pilih Gambar")
                    .items(R.array.uploadImages)
                    .itemsIds(R.array.itemIds)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            switch (which) {
                                case 0:
                                    Gambar3 = true;
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
                                    gambar3.setVisibility(View.VISIBLE);
                                    tvGambar3.setVisibility(View.VISIBLE);
                                    break;
                                case 1:
                                    Gambar3 = true;
                                    captureImage();
                                    gambar3.setVisibility(View.VISIBLE);
                                    tvGambar3.setVisibility(View.VISIBLE);
                                    break;
                                case 2:
                                    gambar3.setImageResource(R.drawable.ic_launcher_background);
                                    gambar3.setVisibility(View.GONE);
                                    tvGambar3.setVisibility(View.GONE);
                                    Gambar3 = false;
                                    break;
                            }
                        }
                    })
                    .show();
        }else if (U.equals("4")){
            new MaterialDialog.Builder(AssignFinishActivity.this)
                    .title("Pilih Gambar")
                    .items(R.array.uploadImages)
                    .itemsIds(R.array.itemIds)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            switch (which) {
                                case 0:
                                    Gambar4 = true;
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
                                    gambar4.setVisibility(View.VISIBLE);
                                    tvGambar4.setVisibility(View.VISIBLE);
                                    break;
                                case 1:
                                    Gambar4 = true;
                                    captureImage();
                                    gambar4.setVisibility(View.VISIBLE);
                                    tvGambar4.setVisibility(View.VISIBLE);
                                    break;
                                case 2:
                                    gambar4.setImageResource(R.drawable.ic_launcher_background);
                                    gambar4.setVisibility(View.GONE);
                                    tvGambar4.setVisibility(View.GONE);
                                    Gambar4 = false;
                                    break;
                            }
                        }
                    })
                    .show();
        }

    }
    private void Logic(){
        mManager = new LinearLayoutManager(AssignFinishActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mManager);
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Data = api.ReportIDS(method.AUTH(token),idReport);
        final LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(AssignFinishActivity.this,R.anim.layout_animation2);
        Data.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getStatusCode().equals("000")){
                    mItems=response.body().getData();
                    mAdapter = new AdapterRateTeknisi(AssignFinishActivity.this,mItems.get(0).getTeknisi(),recyclerView);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setLayoutAnimation(layoutAnimationController);
                    recyclerView.scheduleLayoutAnimation();
                    mAdapter.notifyDataSetChanged();
                }else if(response.body().getStatusCode().equals("002")){
                    method.Login(AssignFinishActivity.this,user,password);
                    Intent intent = new Intent(AssignFinishActivity.this,AssignFinishActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(AssignFinishActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(AssignFinishActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Dellaroy Logic
    private void captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            Intent callCameraApplicationIntent = new Intent();
            callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            // We give some instruction to the intent to save the image
            File photoFile = null;

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile();
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (IOException e) {
                Logger.getAnonymousLogger().info("Exception error in generating the file");
                e.printStackTrace();
            }
            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            Uri outputUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photoFile);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);

            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Logger.getAnonymousLogger().info("Calling the camera App by intent");

            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_PIC_REQUEST);
        }


    }

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
        }else if (requestCode == CAMERA_PIC_REQUEST){
            if (Build.VERSION.SDK_INT > 21) {
                Glide.with(this).load(mImageFileLocation).into(gambar);
                postBukti = mImageFileLocation;
            }else{
                Glide.with(this).load(fileUri).into(gambar);
                postBukti = fileUri.getPath();
            }
            if(Gambar){
                if (Build.VERSION.SDK_INT > 21) {
                    Glide.with(this).load(mImageFileLocation).into(gambar);
                    postBukti = mImageFileLocation;
                }else{
                    Glide.with(this).load(fileUri).into(gambar);
                    postBukti = fileUri.getPath();
                }
                String filename=postBukti.substring(postBukti.lastIndexOf("/")+1);
                gambar.setVisibility(View.VISIBLE);
                tvGambar.setVisibility(View.VISIBLE);
                tvGambar.setText(filename);
                Gambar=false;
            }else if(Gambar2){
                if (Build.VERSION.SDK_INT > 21) {
                    Glide.with(this).load(mImageFileLocation).into(gambar2);
                    postBukti2 = mImageFileLocation;
                }else{
                    Glide.with(this).load(fileUri).into(gambar2);
                    postBukti2 = fileUri.getPath();
                }
                String filename=postBukti.substring(postBukti2.lastIndexOf("/")+1);
                gambar2.setVisibility(View.VISIBLE);
                tvGambar2.setVisibility(View.VISIBLE);
                tvGambar2.setText(filename);
                Gambar2=false;
            }else if(Gambar3){
                if (Build.VERSION.SDK_INT > 21) {
                    Glide.with(this).load(mImageFileLocation).into(gambar3);
                    postBukti3 = mImageFileLocation;
                }else{
                    Glide.with(this).load(fileUri).into(gambar3);
                    postBukti3 = fileUri.getPath();
                }
                String filename=postBukti3.substring(postBukti3.lastIndexOf("/")+1);
                gambar3.setVisibility(View.VISIBLE);
                tvGambar3.setVisibility(View.VISIBLE);
                tvGambar3.setText(filename);
                Gambar3=false;
            }else if(Gambar4){
                if (Build.VERSION.SDK_INT > 21) {
                    Glide.with(this).load(mImageFileLocation).into(gambar4);
                    postBukti4 = mImageFileLocation;
                }else{
                    Glide.with(this).load(fileUri).into(gambar4);
                    postBukti4 = fileUri.getPath();
                }
                String filename=postBukti4.substring(postBukti4.lastIndexOf("/")+1);
                gambar4.setVisibility(View.VISIBLE);
                tvGambar4.setVisibility(View.VISIBLE);
                tvGambar4.setText(filename);
                Gambar=false;
            }
        }
    }
}