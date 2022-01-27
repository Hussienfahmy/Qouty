package com.hussien.quoty.utilities

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.VisibleForTesting
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.hussien.quoty.R
import com.hussien.quoty.ext.toBitmap
import com.hussien.quoty.models.Quote
import com.hussien.quoty.sync.QuotesBroadCastReceiver
import com.hussien.quoty.ui.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Notification utils.
 *
 * util class to send the notification
 */
class NotificationUtils @Inject constructor(
    @ApplicationContext private val context: Context,
    private val actionsUtils: ActionsUtils?
) {

    suspend fun sendQuoteNotification(quote: Quote) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                QUOTES_REMINDER_NOTIFICATION_CHANNEL_ID,
                "Quotes Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val bitmap = actionsUtils?.generateQuoteLayout(quote)?.toBitmap()
        val notificationBuilder =
            NotificationCompat.Builder(context, QUOTES_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.true_color))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .addAction(loveAction(quote))
                .addAction(shareTextAction(quote))
                .addAction(shareImgAction(quote))
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setContentIntent(generateStartAppIntent())
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        }
        notificationManager.notify(QUOTES_NOTIFICATION_ID, notificationBuilder.build())
    }

    private val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    else PendingIntent.FLAG_UPDATE_CURRENT

    private fun generateStartAppIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(context, 31, intent, flags)
    }

    private fun shareImgAction(quote: Quote): NotificationCompat.Action {
        val shareImgIntent = Intent(context, QuotesBroadCastReceiver::class.java)
        shareImgIntent.putExtra(QuotesBroadCastReceiver.EXTRA_QUOTE_ID, quote.id)
        shareImgIntent.action = QuotesBroadCastReceiver.ACTION_QUOTE_SHARE_IMG
        val shareImgPendingIntent = PendingIntent.getBroadcast(
            context,
            QuotesBroadCastReceiver.REQUEST_CODE_SHARE_IMG,
            shareImgIntent,
            flags
        )
        return NotificationCompat.Action(R.drawable.ic_image, "Share Image", shareImgPendingIntent)
    }

    private fun shareTextAction(quote: Quote): NotificationCompat.Action {
        val shareTextIntent = Intent(context, QuotesBroadCastReceiver::class.java)
        shareTextIntent.putExtra(QuotesBroadCastReceiver.EXTRA_QUOTE_ID, quote.id)
        shareTextIntent.action = QuotesBroadCastReceiver.ACTION_QUOTE_SHARE_TXT
        val shareImgPendingIntent = PendingIntent.getBroadcast(
            context,
            QuotesBroadCastReceiver.REQUEST_CODE_SHARE_TEXT,
            shareTextIntent,
            flags
        )
        return NotificationCompat.Action(
            R.drawable.ic_baseline_share_24,
            "Share Text",
            shareImgPendingIntent
        )
    }

    private fun loveAction(quote: Quote): NotificationCompat.Action {
        val loveIntent = Intent(context, QuotesBroadCastReceiver::class.java)
        loveIntent.putExtra(QuotesBroadCastReceiver.EXTRA_QUOTE_ID, quote.id)
        loveIntent.action = QuotesBroadCastReceiver.ACTION_QUOTE_LOVE
        val lovePendingIntent = PendingIntent.getBroadcast(
            context,
            QuotesBroadCastReceiver.REQUEST_CODE_LOVE,
            loveIntent,
            flags,
        )
        return NotificationCompat.Action(R.drawable.ic_heart, "Loved it !", lovePendingIntent)
    }

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        const val QUOTES_NOTIFICATION_ID = 312
        private const val QUOTES_REMINDER_NOTIFICATION_CHANNEL_ID = "quotes_reminder"
    }
}