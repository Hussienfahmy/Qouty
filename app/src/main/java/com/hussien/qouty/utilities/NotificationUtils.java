package com.hussien.qouty.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.hussien.qouty.R;
import com.hussien.qouty.activity.MainActivity;
import com.hussien.qouty.data.Quote;
import com.hussien.qouty.sync.MyBroadCastReceiver;
import com.hussien.qouty.sync.QuotesService;
import com.hussien.qouty.sync.ServiceTasks;

public class NotificationUtils {

    private static final int QUOTES_NOTIFICATION_REMINDER_ID = 312;
    private static final String QUOTES_REMINDER_NOTIFICATION_CHANNEL_ID = "quotes_reminder";
    private static final int ACTION_QUOTE_LOVE_REQUEST_CODE = 12;
    private static final int ACTION_SHARE_IMG_REQUEST_CODE = 321;
    private static final int ACTION_SHARE_TXT_REQUEST_CODE = 534;

    public static void remindUserByQuote(Context context, Quote quote){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    QUOTES_REMINDER_NOTIFICATION_CHANNEL_ID,
                    "Quotes Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Bitmap bitmap = ActionsUtils.generateQuoteLayout(context, quote);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, QUOTES_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.true_color))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .addAction(loveAction(context, quote))
                .addAction(shareTextAction(context, quote))
                .addAction(shareImgAction(context, quote))
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setContentIntent(startAppIntent(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(QUOTES_NOTIFICATION_REMINDER_ID, notificationBuilder.build());
    }

    private static PendingIntent startAppIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, 31, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static NotificationCompat.Action shareImgAction(Context context, Quote quote) {
        Intent shareImgIntent = new Intent(context, MyBroadCastReceiver.class);
        shareImgIntent.setAction(ServiceTasks.ACTION_QUOTE_SHARE_IMG);
        shareImgIntent.putExtra(QuotesService.EXTRA_QUOTE, quote);
        PendingIntent shareImgPendingIntent = PendingIntent.getBroadcast(
                context,
                ACTION_SHARE_IMG_REQUEST_CODE,
                shareImgIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        return new NotificationCompat.Action(R.drawable.ic_image, "Share Image", shareImgPendingIntent);
    }

    private static NotificationCompat.Action shareTextAction(Context context, Quote quote) {
        Intent shareTextIntent = new Intent(context, MyBroadCastReceiver.class);
        shareTextIntent.setAction(ServiceTasks.ACTION_QUOTE_SHARE_TXT);
        shareTextIntent.putExtra(QuotesService.EXTRA_QUOTE, quote);

        PendingIntent shareImgPendingIntent = PendingIntent.getBroadcast(
                context,
                ACTION_SHARE_TXT_REQUEST_CODE,
                shareTextIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        return new NotificationCompat.Action(R.drawable.ic_baseline_share_24, "Share Text", shareImgPendingIntent);
    }

    private static NotificationCompat.Action loveAction(Context context, Quote quote) {
        Intent loveIntent = new Intent(context, MyBroadCastReceiver.class);
        loveIntent.setAction(ServiceTasks.ACTION_QUOTE_LOVE);
        loveIntent.putExtra(QuotesService.EXTRA_QUOTE, quote);

        PendingIntent lovePendingIntent = PendingIntent.getBroadcast(
                context,
                ACTION_QUOTE_LOVE_REQUEST_CODE,
                loveIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        return new NotificationCompat.Action(R.drawable.ic_heart, "Loved it !", lovePendingIntent);
    }
}