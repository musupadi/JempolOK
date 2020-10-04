package com.destinyapp.jempolok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.destinyapp.jempolok.Activity.ui.HomeFragment;
import com.destinyapp.jempolok.Activity.ui.UserFragment;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;
import com.luseen.spacenavigation.SpaceNavigationView;

import pub.devrel.easypermissions.EasyPermissions;

public class HomeActivity extends AppCompatActivity {
    String user,password,token,nama,foto,level,status;
    SpaceNavigationView spaceNavigationView;
    TextView item;
    Fragment fragment;
    LinearLayout LBeranda,LAkun,LSetting;
    ImageView IBeranda,IAkun,ISetting,IBBeranda,IBAkun,IBSetting;
    TextView TBeranda,TAkun,TSetting;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        fragment = new UserFragment();
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