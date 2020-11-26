package com.destinyapp.jempolok.Activity.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.destinyapp.jempolok.API.ApiRequest;
import com.destinyapp.jempolok.API.RetroServer;
import com.destinyapp.jempolok.Activity.ForgotPasswordActivity;
import com.destinyapp.jempolok.Activity.FormLaporanActivity;
import com.destinyapp.jempolok.Activity.HomeActivity;
import com.destinyapp.jempolok.Activity.LoginActivity;
import com.destinyapp.jempolok.BuildConfig;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.Model.ResponseModel;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

import java.io.File;
import java.io.IOException;
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

public class UserFragment extends Fragment {
    //Dellaroy Logic
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_PICK_PHOTO = 2;
    private Uri mMediaUri;
    private static final int CAMERA_PIC_REQUEST = 1111;

    private static final String TAG = HomeActivity.class.getSimpleName();

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri;

    private String mediaPath;

    private Button btnCapturePicture;

    private String mImageFileLocation = "";
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    ProgressDialog pDialog;
    String postFoto= "";
    Button upload,upload2,upload3,upload4,submit,btnChangePassword;
    //ONCLICK
    Boolean Gambar = false;
    ImageView gambar;
    TextView tvGambar;

    //
    String user,password,token,nama,foto,level,status;
    ImageView Profile;
    TextView Username,Nama,Level;
    Musupadi musupadi;
    Button EditProfile,ChangePassword;
    Dialog DialogChange,DialogChangePassword;
    EditText etNama,LastPassword,NewPassword,ConfirmPassword;
    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Profile = view.findViewById(R.id.ivProfile);
        Username = view.findViewById(R.id.tvUsername);
        Nama = view.findViewById(R.id.tvNama);
        Level = view.findViewById(R.id.tvLevel);
        ChangePassword = view.findViewById(R.id.btnChangePassword);
        EditProfile = view.findViewById(R.id.btnEditProfile);
        musupadi = new Musupadi();
        DialogChange = new Dialog(getActivity());
        DialogChangePassword = new Dialog(getActivity());
        DialogChange.setContentView(R.layout.dialog_edit_profile);
        DialogChangePassword.setContentView(R.layout.dialog_change_password);
        upload = DialogChange.findViewById(R.id.btnUpload);
        gambar = DialogChange.findViewById(R.id.ivGambar);
        tvGambar = DialogChange.findViewById(R.id.tvGambar);
        submit = DialogChange.findViewById(R.id.btnSubmit);
        etNama = DialogChange.findViewById(R.id.etNama);
        LastPassword = DialogChangePassword.findViewById(R.id.etLastPassword);
        NewPassword = DialogChangePassword.findViewById(R.id.etNewPassword);
        ConfirmPassword = DialogChangePassword.findViewById(R.id.etConfirmPassword);
        btnChangePassword = DialogChangePassword.findViewById(R.id.btnChangePassword);
        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogChangePassword.show();
            }
        });
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogChange.show();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
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
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePassword();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeProfile();
            }
        });
        final DB_Helper dbHelper = new DB_Helper(getActivity());
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
            String URL = musupadi.BASE_URL();
            Glide.with(getActivity())
                    .load(URL+foto)
                    .into(Profile);
            Username.setText(user);
            Nama.setText(nama);
            Level.setText(level);
        }
    }

    private void ChangeProfile(){
        File file = new File(postFoto);
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part partPhoto = MultipartBody.Part.createFormData("fotoUser", file.getName(), fileReqBody);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Mengubah Data Profile");
        pd.show();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> email = api.ChangeProfile(
                musupadi.AUTH(token),
                RequestBody.create(MediaType.parse("text/plain"),etNama.getText().toString()),
                partPhoto);
        email.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    pd.hide();
                    if (response.body().getStatusMessage().equals("Success")){
                        DialogChange.hide();
                    }
                    Toast.makeText(getActivity(), response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    pd.hide();
                    Log.i("Error",e.toString());
                    Toast.makeText(getActivity(), "Terjadi Kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.hide();
                Toast.makeText(getActivity(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ChangePassword(){
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Mengubah Data Profile");
        pd.show();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> email = api.ChangePasswordLogin(
                musupadi.AUTH(token),
                LastPassword.getText().toString(),
                NewPassword.getText().toString(),
                ConfirmPassword.getText().toString());
        email.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    pd.hide();
                    if (response.body().getStatusMessage().equals("Success")){
                        DialogChangePassword.hide();
                        musupadi.Login(getActivity(),user,NewPassword.getText().toString());
                    }
                    Toast.makeText(getActivity(), response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    pd.hide();
                    Log.i("Error",e.toString());
                    Toast.makeText(getActivity(), "Terjadi Kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.hide();
                Toast.makeText(getActivity(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
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
                    getActivity(),
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
            if (data != null) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);

                // Set the Image in ImageView for Previewing the Media

//                    imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
                if(Gambar){
                    postFoto = mediaPath;
                    String filename=postFoto.substring(postFoto.lastIndexOf("/")+1);
                    gambar.setVisibility(View.VISIBLE);
                    tvGambar.setVisibility(View.VISIBLE);
                    gambar.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    tvGambar.setText(filename);
                    Gambar=false;
                    Toast.makeText(getActivity(), filename, Toast.LENGTH_SHORT).show();
                }
            }
        }else if (requestCode == CAMERA_PIC_REQUEST){
            if(Gambar){
                if (Build.VERSION.SDK_INT > 21) {
                    Glide.with(this).load(mImageFileLocation).into(gambar);
                    postFoto = mImageFileLocation;
                }else{
                    Glide.with(this).load(fileUri).into(gambar);
                    postFoto = fileUri.getPath();
                }
                String filename=postFoto.substring(postFoto.lastIndexOf("/")+1);
                gambar.setVisibility(View.VISIBLE);
                tvGambar.setVisibility(View.VISIBLE);
                tvGambar.setText(filename);
                Gambar=false;
            }
        }
    }
}