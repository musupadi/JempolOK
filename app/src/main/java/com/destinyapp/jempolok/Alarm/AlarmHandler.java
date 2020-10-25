package com.destinyapp.jempolok.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmHandler {
    private Context context;
    public AlarmHandler(Context context){
        this.context=context;
    }

    public void setAlarmManager(){
        try {
            Intent intent = new Intent(context,ExecutableService.class);
            PendingIntent sender = PendingIntent.getBroadcast(context,2,intent,0);
            AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            if (am != null){
                long After = 10000;
                long every = 600000;
                am.setRepeating(AlarmManager.RTC_WAKEUP,After,every,sender);
            }
        }catch (Exception ignored){

        }
    }

    public void CancelAlarm(){
        Intent intent = new Intent(context,ExecutableService.class);
        PendingIntent sender = PendingIntent.getBroadcast(context,2,intent,0);
        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        if (am != null){
            am.cancel(sender);
        }
    }
}
