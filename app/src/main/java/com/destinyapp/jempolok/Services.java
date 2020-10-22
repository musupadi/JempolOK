package com.destinyapp.jempolok;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.destinyapp.jempolok.API.ApiRequest;
import com.destinyapp.jempolok.API.RetroServer;
import com.destinyapp.jempolok.Activity.HomeActivity;
import com.destinyapp.jempolok.Activity.LoginActivity;
import com.destinyapp.jempolok.Activity.MainActivity;
import com.destinyapp.jempolok.Activity.SplashScreenActivity;
import com.destinyapp.jempolok.Alarm.AlarmHandler;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.Model.ResponseModel;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.destinyapp.jempolok.App.CHANNEL_ID;


public class Services extends Service {
    String user,password,token,nama,foto,level,status;
    Musupadi musupadi;
    Handler handler = new Handler();
    Runnable runnable;
        String GROUP_KEY = "com.destinyapp.jempolok.NOTIFICATION";
    int i = 0;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        try {
            AlarmHandler alarmHandler = new AlarmHandler(this);
//        alarmHandler.CancelAlarm();
            alarmHandler.setAlarmManager();

            Logic(intent);
        }catch (Exception ignored){

        }
        return START_NOT_STICKY;
    }

    private void Logic(final Intent intent){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        musupadi = new Musupadi();
        final DB_Helper dbHelper = new DB_Helper(this);
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
        if (user!=null){
            Call<ResponseModel> Data = api.GetNotification(musupadi.AUTH(token));
            Data.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    try {
                        if (response.body().getStatusCode().equals("000")){
                            if (response.body().getData().size()>0){
                                int i = 0;
                                while (i<response.body().getData().size()){
                                    if (response.body().getData().get(i).getStatus_notif() == 0){
                                        NOTIFY(intent,String.valueOf(response.body().getData().get(i).getId_notif_report()));
                                    }
                                    i++;
                                }

                            }else{
                                stopService();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        startService(intent);
                                    }
                                }, 60000); //3000 L = 3 detik

                            }
                        }else{
                            musupadi.Login(Services.this,user,password);
                            Logic(intent);
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
    private void NOTIFY(Intent intent,String ID){
        String message = intent.getStringExtra("MESSAGE");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("NOTIF",ID);
        Bitmap myLogo = BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.jempol);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,notificationIntent,0);
        Uri alarmSound = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Si Jempol Apps")
                .setContentText(message)
                .setSmallIcon(R.mipmap.icon_temporary_foreground)
                .setLargeIcon(myLogo)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText(message))
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .setLights(Color.BLUE, 500, 500).setContentText(message)
//                .setAutoCancel(true).setTicker("Notification Si Jempol")
                .setVibrate(new long[] { 100, 250, 100, 250, 100, 250 })
                .setSound(alarmSound)
                .setGroup(GROUP_KEY)
                .setGroupSummary(true)
                .setOngoing(false)
                .setDeleteIntent(pendingIntent)
                .build();
        startForeground(1,notification);
        stopForeground(false);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void stopService(){
        Intent serviceIntent = new Intent(this,Services.class);
        stopService(serviceIntent);
    }
}
