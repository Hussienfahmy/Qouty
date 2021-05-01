package com.hussien.qouty.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.preference.PreferenceManager;

import com.hussien.qouty.R;
import com.hussien.qouty.sync.MyBroadCastReceiver;
import com.hussien.qouty.sync.ServiceTasks;

import java.util.Calendar;

public class AlarmUtil {

    public static void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }

    public static void startAlarm(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        long afterTime = Long.parseLong(sharedPreferences.getString(context.getString(R.string.key_notify_every), context.getString(R.string.default_notify_every)));
        long triggerTime = Calendar.getInstance().getTimeInMillis() + afterTime;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = getPendingIntent(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    triggerTime, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }
    }

    private static PendingIntent getPendingIntent(Context context){
        Intent intent = new Intent(context.getApplicationContext(), MyBroadCastReceiver.class);
        intent.setAction(ServiceTasks.ACTION_QUOTE_SEND_NOTIFICATION);
        return PendingIntent.getBroadcast(context.getApplicationContext(), 532, intent, 0);
    }
}