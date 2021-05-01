package com.hussien.qouty.sync;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.hussien.qouty.data.Quote;


public class QuotesService extends JobIntentService {
    public static final String EXTRA_QUOTE = "extra_quote";

    static void enqueueWork(Context context, Intent work){
        enqueueWork(context, QuotesService.class, 4211, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String action = intent.getAction();
        Quote quote;
        if (intent.hasExtra(EXTRA_QUOTE)) {
            quote = intent.getExtras().getParcelable(EXTRA_QUOTE);
        }else {
            quote = null;
        }
        ServiceTasks.executeTask(getApplicationContext(), action, quote);
    }
}