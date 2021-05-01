package com.hussien.qouty.fragments;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;

import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.hussien.qouty.AppExecutor;
import com.hussien.qouty.R;
import com.hussien.qouty.data.QuotesRepository;
import com.hussien.qouty.utilities.AlarmUtil;
import com.mikepenz.aboutlibraries.LibsBuilder;


public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);

        EditTextPreference numberPreference = findPreference(getString(R.string.key_number_quotes));
        if (numberPreference != null){
            numberPreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
            numberPreference.setOnPreferenceChangeListener(this);
        }

        MultiSelectListPreference tagsPreference = findPreference(getString(R.string.key_tags));
        if (tagsPreference != null) {
            tagsPreference.setEnabled(false);
            setTagsEntriesAndValues(tagsPreference);
        }

        checkNotificationEnabled();

        Preference aboutPreference = findPreference(getString(R.string.key_pref_about));
        if (aboutPreference != null){
            aboutPreference.setOnPreferenceClickListener(preference -> {
                navigateToAboutPage();
                return true;
            });
        }

        Preference creditsPreference = findPreference(getString(R.string.key_pref_credits));
        if (creditsPreference != null){
            creditsPreference.setOnPreferenceClickListener(preference -> {
                showCreditsDialog();
                return true;
            });
        }
    }

    private void showCreditsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Credits");
        builder.setMessage("Thanks to harbi for the quotes collection https://github.com/harbi/short-quotes\nApp Icon Designed by Freepik");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void navigateToAboutPage(){
        LibsBuilder builder = new LibsBuilder()
                .withAboutAppName("Quoty")
                .withAboutDescription("Beautiful quotes App sharing")
                .withAboutIconShown(true)
                .withAboutVersionShown(true);

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", builder);
        NavHostFragment.findNavController(this).navigate(R.id.action_global_about_libraries, bundle);
    }

    private void checkNotificationEnabled() {
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        ListPreference notificationEveryPreference = findPreference(getString(R.string.key_notify_every));
        if (notificationEveryPreference != null) {
            boolean isNotificationEnabled = sharedPreferences.getBoolean(getString(R.string.key_notification), false);
            notificationEveryPreference.setEnabled(isNotificationEnabled);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setTagsEntriesAndValues(MultiSelectListPreference tagsPreference) {
        if (getActivity() == null)return;

        Runnable runnable = () -> {
            QuotesRepository quotesRepository = QuotesRepository.getRepo_instance(getActivity().getApplication());
            String[] tags = quotesRepository.getAllTags();

            getActivity().runOnUiThread(() -> {
                tagsPreference.setEntries(tags);
                tagsPreference.setEntryValues(tags);
                tagsPreference.setEnabled(true);
            });
        };
        AppExecutor.getInstance().getDiskIo().execute(runnable);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.key_lang))
                || key.equals(getString(R.string.key_tags))
                || key.equals(getString(R.string.key_font))
                || key.equals(getString(R.string.key_number_quotes))){

            //get new content according to the new preferences
            AppExecutor.getInstance().getDiskIo().execute(new QuotesFragment.SetNewRowsToShowRunnable(getActivity()));
        }else if (key.equals(getString(R.string.key_notification))
                || key.equals(getString(R.string.key_notify_every))){
            checkNotificationEnabled();
            boolean isNotificationEnabled = sharedPreferences.getBoolean(getString(R.string.key_notification), false);
            if (getContext() != null) AlarmUtil.cancelAlarm(getContext());

            if (isNotificationEnabled) AlarmUtil.startAlarm(getContext());
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == null || newValue == null) return false;
        if (preference.getKey().equals(getString(R.string.key_number_quotes))){
            try {
                int num = Integer.parseInt(newValue.toString());
                //zero is not allowed
                return num != 0;
            }catch (NumberFormatException e){
                return false;
            }
        }
        return true;
    }
}