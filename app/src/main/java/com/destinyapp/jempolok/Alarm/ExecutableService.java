package com.destinyapp.jempolok.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.destinyapp.jempolok.Services;

public class ExecutableService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Check Database", Toast.LENGTH_SHORT).show();

        try {
            final Intent serviceIntent = new Intent(context, Services.class);
            serviceIntent.putExtra("MESSAGE","Terdapat laporan baru yang harus dikerjakan");
            context.startService(serviceIntent);
        }catch (Exception ignored){

        }

    }
}
