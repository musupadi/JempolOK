package com.destinyapp.jempolok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapp.jempolok.API.ApiRequest;
import com.destinyapp.jempolok.API.RetroServer;
import com.destinyapp.jempolok.Activity.ui.HomeFragment;
import com.destinyapp.jempolok.Activity.ui.SettingFragment;
import com.destinyapp.jempolok.Activity.ui.UserFragment;
import com.destinyapp.jempolok.Alarm.AlarmHandler;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.Model.ResponseModel;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.Receiver;
import com.destinyapp.jempolok.Services;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;
import com.luseen.spacenavigation.SpaceNavigationView;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    String user,password,token,nama,foto,level,status;
    SpaceNavigationView spaceNavigationView;
    TextView item;
    Fragment fragment;
    LinearLayout LBeranda,LAkun,LSetting;
    ImageView IBeranda,IAkun,ISetting,IBBeranda,IBAkun,IBSetting;
    TextView TBeranda,TAkun,TSetting;
    Musupadi musupadi;
    private PendingIntent pendingIntent;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent data = getIntent();
        String message = data.getStringExtra("NOTIF");
        musupadi = new Musupadi();
        //Deklarasi Variabel
        LBeranda = findViewById(R.id.linearBeranda);
        LAkun = findViewById(R.id.linearAkun);
        LSetting = findViewById(R.id.linearSetting);
        IBeranda = findViewById(R.id.ivBeranda);
        IAkun = findViewById(R.id.ivAkun);
        ISetting = findViewById(R.id.ivSetting);
        IBBeranda = findViewById(R.id.ivBackBeranda);
        IBAkun = findViewById(R.id.ivBackAkun);
        IBSetting = findViewById(R.id.ivBackSetting);
        TBeranda = findViewById(R.id.tvBeranda);
        TAkun = findViewById(R.id.tvAkun);
        TSetting = findViewById(R.id.tvSetting);

        final DB_Helper dbHelper = new DB_Helper(HomeActivity.this);
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
        }else{
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(EasyPermissions.hasPermissions(HomeActivity.this, galleryPermissions)) {

        }else{
            EasyPermissions.requestPermissions(HomeActivity.this, "Access for storage",
                    101, galleryPermissions);
        }
//        Toast.makeText(this, "Username : "+user+" password : "+password+" Token : "+token, Toast.LENGTH_SHORT).show();
        Beranda();
        LBeranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Beranda();
            }
        });
        LAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account();
            }
        });
        LSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting();
            }
        });
        start();
        final Intent serviceIntent = new Intent(this, Services.class);
        serviceIntent.putExtra("MESSAGE","Pemberitahuan");
        startService(serviceIntent);
        if (message != null){
            ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
            Call<ResponseModel> Data = api.PostNotif(musupadi.AUTH(token),message);
            Data.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    try {
                        if (response.body().getStatusCode().equals("000")){
                            stopService(serviceIntent);
                            startService(serviceIntent);
                        }else{
                            musupadi.Login(HomeActivity.this,user,password);
                        }

                    }catch (Exception e){
                        Log.i("ERROR : ",e.toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });
        }
    }
    public void start() {


        /* Retrieve a PendingIntent that will perform a broadcast */
//        Intent alarmIntent = new Intent(HomeActivity.this, Receiver.class);
//        pendingIntent = PendingIntent.getBroadcast(HomeActivity.this, 0, alarmIntent, 0);
//
//        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        int interval = 8000;
//
//        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
//        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }
    private void DFault(){
        IBeranda.setImageResource(R.drawable.ic_baseline_home_24);
        IAkun.setImageResource(R.drawable.ic_baseline_person_24);
        ISetting.setImageResource(R.drawable.ic_baseline_settings_24);
        IBBeranda.setImageResource(R.drawable.btn_bottom_nav);
        IBAkun.setImageResource(R.drawable.btn_bottom_nav);
        IBSetting.setImageResource(R.drawable.btn_bottom_nav);
        TBeranda.setTextColor(Color.BLACK);
        TSetting.setTextColor(Color.BLACK);
        TAkun.setTextColor(Color.BLACK);
    }
    private void Beranda(){
        DFault();
        IBeranda.setImageResource(R.drawable.ic_baseline_home_24_white);
        IAkun.setImageResource(R.drawable.ic_baseline_person_24);
        ISetting.setImageResource(R.drawable.ic_baseline_settings_24);
        IBBeranda.setImageResource(R.drawable.btn_bottom_nav_primary);
        IBAkun.setImageResource(R.drawable.btn_bottom_nav);
        IBSetting.setImageResource(R.drawable.btn_bottom_nav);
        TBeranda.setTextColor(Color.rgb(142,151,255));
        TSetting.setTextColor(Color.BLACK);
        TAkun.setTextColor(Color.BLACK);
        fragment = new HomeFragment();
        ChangeFragment(fragment);
    }
    private void Account(){
        DFault();
        IBeranda.setImageResource(R.drawable.ic_baseline_home_24);
        IAkun.setImageResource(R.drawable.ic_baseline_person_24_white);
        ISetting.setImageResource(R.drawable.ic_baseline_settings_24);
        IBBeranda.setImageResource(R.drawable.btn_bottom_nav);
        IBAkun.setImageResource(R.drawable.btn_bottom_nav_primary);
        IBSetting.setImageResource(R.drawable.btn_bottom_nav);
        TBeranda.setTextColor(Color.BLACK);
        TAkun.setTextColor(Color.rgb(142,151,255));
        TSetting.setTextColor(Color.BLACK);
        fragment = new UserFragment();
        ChangeFragment(fragment);
    }
    private void Setting(){
        DFault();
        IBeranda.setImageResource(R.drawable.ic_baseline_home_24);
        IAkun.setImageResource(R.drawable.ic_baseline_person_24);
        ISetting.setImageResource(R.drawable.ic_baseline_settings_24_white);
        IBBeranda.setImageResource(R.drawable.btn_bottom_nav);
        IBAkun.setImageResource(R.drawable.btn_bottom_nav);
        IBSetting.setImageResource(R.drawable.btn_bottom_nav_primary);
        TBeranda.setTextColor(Color.BLACK);
        TAkun.setTextColor(Color.BLACK);
        TSetting.setTextColor(Color.rgb(142,151,255));
        fragment = new SettingFragment();
        ChangeFragment(fragment);
    }
    private void ChangeFragment(Fragment fragment){
        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.Container,fragment);
            ft.commit();
        }
    }
}