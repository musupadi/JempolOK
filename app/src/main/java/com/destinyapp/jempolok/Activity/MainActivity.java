package com.destinyapp.jempolok.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapp.jempolok.API.ApiRequest;
import com.destinyapp.jempolok.API.RetroServer;
import com.destinyapp.jempolok.Activity.ui.HomeFragment;
import com.destinyapp.jempolok.Activity.ui.SettingFragment;
import com.destinyapp.jempolok.Activity.ui.UserFragment;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.Model.ResponseModel;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.Services;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {
    String user,password,token,nama,foto,level,status;
    SpaceNavigationView spaceNavigationView;
    TextView item;
    Fragment fragment;
    Musupadi musupadi;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        musupadi = new Musupadi();
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
        if(EasyPermissions.hasPermissions(MainActivity.this, galleryPermissions)) {

        }else{
            EasyPermissions.requestPermissions(MainActivity.this, "Access All Apps Feature",
                    101, galleryPermissions);
        }
//        Toast.makeText(this, "Username : "+user+" password : "+password+" Token : "+token, Toast.LENGTH_SHORT).show();

        spaceNavigationView=findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        fragment = new HomeFragment();
        ChangeFragment(fragment);
        spaceNavigationView.setCentreButtonSelectable(true);
        spaceNavigationView.addSpaceItem(new SpaceItem("Akun", R.drawable.ic_baseline_account_circle_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("Setting", R.drawable.ic_baseline_settings_24));
        Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
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
        NOTIFY();
    }

    private void NOTIFY(){
        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        String externalUserId = user; // You will supply the external user id to the OneSignal SDK

// Setting External User Id with Callback Available in SDK Version 3.13.0+
        OneSignal.setExternalUserId(externalUserId, new OneSignal.OSExternalUserIdUpdateCompletionHandler() {
            @Override
            public void onComplete(JSONObject results) {
                // The results will contain push and email success statuses
                OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id done with results: " + results.toString());

                // Push can be expected in almost every situation with a success status, but
                // as a pre-caution its good to verify it exists
                try {
                    if (results.has("push") && results.getJSONObject("push").has("success")) {
                        boolean isPushSuccess = results.getJSONObject("push").getBoolean("success");
                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id for push status: " + isPushSuccess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Verify the email is set or check that the results have an email success status
                try {
                    if (results.has("email") && results.getJSONObject("email").has("success")) {
                        boolean isEmailSuccess = results.getJSONObject("email").getBoolean("success");
                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Sets external user id for email status: " + isEmailSuccess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

//Available with SDK Version 3.12.7-
//OneSignal.setExternalUserId(myCustomUniqueUserId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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