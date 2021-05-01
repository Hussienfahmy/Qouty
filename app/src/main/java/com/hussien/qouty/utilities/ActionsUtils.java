package com.hussien.qouty.utilities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.hussien.qouty.AppExecutor;
import com.hussien.qouty.data.Quote;
import com.hussien.qouty.data.QuotesRepository;
import com.hussien.qouty.databinding.QuoteLayoutBinding;

import java.io.File;
import java.io.FileOutputStream;

public class ActionsUtils {
    private static final String TAG = "ActionsUtils";
    public static void updateQuoteLoved(Context context, Quote quote) {
        quote.setLoved(!quote.isLoved());
        QuotesRepository quotesRepository = QuotesRepository.getRepo_instance(context);

        Runnable runnable = () -> quotesRepository.updateQuote(quote);
        AppExecutor.getInstance().getDiskIo().execute(runnable);
    }

    public static void shareText(Context context, Quote quote) {
        String shareContent = getContentTextToShare(quote);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,  shareContent);

        Intent chooser = Intent.createChooser(shareIntent, "Share via");
        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(chooser);
    }



    public static String getContentTextToShare(Quote quote){
        return '\u201D'+ quote.getText() +'\u201C' + "\n" + quote.getAuthor();
    }

    public static void copyText(Context context, Quote quote) {
        String shareContent = ActionsUtils.getContentTextToShare(quote);
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(shareContent, shareContent);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show();
    }

    public static void shareImg(Context context, Quote quote) {
        sendImgIntent(context, generateQuoteLayout(context, quote));
    }

    public static Bitmap generateQuoteLayout(Context context, Quote quote) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(displayMetrics.widthPixels,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        QuoteLayoutBinding quoteLayoutBinding = QuoteLayoutBinding.inflate(LayoutInflater.from(context));
        quoteLayoutBinding.getRoot().setBackgroundColor(Color.TRANSPARENT);
        quoteLayoutBinding.getRoot().setLayoutParams(params);

        quoteLayoutBinding.quoteText.setLayoutParams(params);

        quoteLayoutBinding.quoteText.setText(quote.getText());
        quoteLayoutBinding.quoteAuthor.setText(quote.getAuthor());
        Typeface typeface = SharedPreferenceUtils.getUserChoiceFontFamily(context);
        quoteLayoutBinding.quoteText.setTypeface(typeface);
        quoteLayoutBinding.quoteAuthor.setTypeface(typeface);

        View view = quoteLayoutBinding.getRoot();
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.draw(canvas);
        return bitmap;
    }

    private static void sendImgIntent(Context context, Bitmap bitmap) {
        try {
            File outputFile = new File(context.getExternalFilesDir(null), "quote.png");
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

            fileOutputStream.flush();
            fileOutputStream.close();

            Uri uri = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".provider",
                    outputFile);

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setDataAndType(uri, "image/*");
            intent.putExtra(Intent.EXTRA_STREAM, uri);

            Intent chooser = Intent.createChooser(intent, "Share Image Via");
            chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(chooser);
        }catch (Exception e){
            Log.e(TAG, "sendImgIntent: " + e.toString());
        }
    }
}