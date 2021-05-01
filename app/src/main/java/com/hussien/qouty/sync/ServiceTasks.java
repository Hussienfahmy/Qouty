package com.hussien.qouty.sync;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.hussien.qouty.AppExecutor;
import com.hussien.qouty.R;
import com.hussien.qouty.data.Quote;
import com.hussien.qouty.data.QuotesRepository;
import com.hussien.qouty.utilities.ActionsUtils;
import com.hussien.qouty.utilities.AlarmUtil;
import com.hussien.qouty.utilities.NotificationUtils;
import com.hussien.qouty.utilities.SharedPreferenceUtils;

import java.util.List;

public class ServiceTasks {
    public static final String ACTION_QUOTE_SHARE_IMG = "com.hussien.qouty.share_Img";
    public static final String ACTION_QUOTE_SHARE_TXT = "com.hussien.qouty.share_text";
    public static final String ACTION_QUOTE_LOVE = "com.hussien.qouty.love_quote";
    public static final String ACTION_QUOTE_SEND_NOTIFICATION = "com.hussien.qouty.send_notification";

    public static void executeTask(Context context, String action, Quote quote){
        switch (action){
            case ACTION_QUOTE_LOVE:
                setQuoteToLoved(context, quote);
                break;
            case ACTION_QUOTE_SHARE_IMG:
                shareImg(context, quote);
                break;
            case ACTION_QUOTE_SHARE_TXT:
                shareTxt(context, quote);
            case ACTION_QUOTE_SEND_NOTIFICATION:
                getQuoteAndSendNotification(context);
                break;
        }
    }

    private static void getQuoteAndSendNotification(Context context) {
        AppExecutor.getInstance().getDiskIo().execute(new getQuoteRunnable(context));
    }

    private static void shareImg(Context context, Quote quote) {
        ActionsUtils.shareImg(context, quote);
        closeNotificationStatusBar(context);
    }

    private static void setQuoteToLoved(Context context, Quote quote) {
        ActionsUtils.updateQuoteLoved(context, quote);
        AppExecutor.getInstance().getMainThreadExecutor().execute(() -> Toast.makeText(context, "Saved In Favorites", Toast.LENGTH_SHORT).show());
        closeNotificationStatusBar(context);
    }

    private static void shareTxt(Context context, Quote quote) {
        ActionsUtils.shareText(context, quote);
        closeNotificationStatusBar(context);
    }

    private static void closeNotificationStatusBar(Context context){
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

    private static class getQuoteRunnable implements Runnable{
        private final Context context;

        private getQuoteRunnable(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            List<String> tags = SharedPreferenceUtils.getArrayFromSharedPref(context, context.getString(R.string.key_tags));
            List<String> lang = SharedPreferenceUtils.getArrayFromSharedPref(context, context.getString(R.string.key_lang));
            Quote quote = QuotesRepository.getRepo_instance(context).getRandomQuote(tags, lang);
            NotificationUtils.remindUserByQuote(context, quote);
            AlarmUtil.startAlarm(context);
        }
    }
}