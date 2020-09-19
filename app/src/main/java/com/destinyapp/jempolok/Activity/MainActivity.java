package com.destinyapp.jempolok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapp.jempolok.Activity.ui.HomeFragment;
import com.destinyapp.jempolok.Activity.ui.SettingFragment;
import com.destinyapp.jempolok.Activity.ui.UserFragment;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MainActivity extends AppCompatActivity {
    String user,password,token,nama,foto,level,status;
    SpaceNavigationView spaceNavigationView;
    TextView item;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DB_Helper dbHelper = new DB_Helper(MainActivity.this);
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
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        Toast.makeText(this, "Username : "+user+" password : "+password+" Token : "+token, Toast.LENGTH_SHORT).show();

        spaceNavigationView=findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        fragment = new HomeFragment();
        ChangeFragment(fragment);
        spaceNavigationView.setCentreButtonSelectable(true);
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_account_circle_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_settings_24));
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                fragment = new HomeFragment();
                ChangeFragment(fragment);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                SelectedBottomNavigation(itemIndex);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                SelectedBottomNavigation(itemIndex);
            }
        });
    }
    private void SelectedBottomNavigation(int itemIndex){
        if (itemIndex==0){
            fragment = new UserFragment();
        }else if(itemIndex==1){
            fragment = new SettingFragment();
        }
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