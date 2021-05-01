package com.hussien.qouty.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import androidx.preference.PreferenceManager;

import com.hussien.qouty.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SharedPreferenceUtils {

    public static List<String> getArrayFromSharedPref(Context context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String[] tags_default = context.getResources().getStringArray(R.array.tags_defaults);
        String[] lang_default = context.getResources().getStringArray(R.array.lang_defaults);

        Set<String> tagsSet = sharedPreferences.getStringSet(key, new HashSet<>(Arrays.asList(tags_default)));
        Set<String> langSet = sharedPreferences.getStringSet(key, new HashSet<>(Arrays.asList(lang_default)));
        if (key .equals(context.getString(R.string.key_lang))){
            return new ArrayList<>(langSet);
        }else if (key.equals(context.getString(R.string.key_tags))){
            return new ArrayList<>(tagsSet);
        }
        return null;
    }

    public static Typeface getUserChoiceFontFamily(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String font_name_tff = sharedPreferences.getString(context.getString(R.string.key_font), context.getString(R.string.font_default));
        return Typeface.createFromAsset(context.getAssets(), "fonts/" + font_name_tff);
    }
}