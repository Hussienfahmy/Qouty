package com.hussien.qouty.ui.settings

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.*
import com.hussien.qouty.R
import com.hussien.qouty.ext.capitalizeEveryFirstChar
import com.hussien.qouty.ext.trimAllExtraSpaces
import com.mikepenz.aboutlibraries.LibsBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener,
    Preference.OnPreferenceChangeListener {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)

        lifecycleScope.launchWhenStarted {
            launch { initNumberOfQuotesPref() }
            launch { initTagsPref() }
            launch { initNotifyEveryPref() }
            launch { initFontPref() }
            launch { initLanguagePref() }
        }

        initAboutPref()

        initCreditsPref()
    }

    private suspend fun initLanguagePref() {
        viewModel.languages.collect {
            findPreference<MultiSelectListPreference>(getString(R.string.key_lang))?.run {
                val languages = it.joinToString()
                summary = languages
            }
        }
    }

    private suspend fun initFontPref() {
        viewModel.font.collect {
            findPreference<ListPreference>(getString(R.string.key_font))?.run {
                summary = it.toFontName()
            }
        }
    }

    private fun initCreditsPref() {
        findPreference<Preference>(getString(R.string.key_pref_credits))?.run {
            onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    showCreditsDialog()
                    true
                }
        }
    }

    private fun initAboutPref() {
        findPreference<Preference>(getString(R.string.key_pref_about))?.run {
            onPreferenceClickListener = Preference.OnPreferenceClickListener {
                navigateToAboutPage()
                true
            }
        }
    }

    private suspend fun initNotifyEveryPref() {
        viewModel.notificationsEnabled.collect {
            findPreference<ListPreference>(getString(R.string.key_notify_every))?.run {
                isEnabled = it
                summary = viewModel.notifyEvery.map { "${it / 60} Hour" }.first()
            }
        }
    }

    private suspend fun initTagsPref() {
        findPreference<MultiSelectListPreference>(getString(R.string.key_tags))?.run {
            val allTags = viewModel.allTags.first()
            isEnabled = false
            entries = allTags
            entryValues = allTags
            isEnabled = true
            viewModel.tags.collect { tags ->
                summary = tags.joinToString(limit = 4)
            }
        }
    }

    private suspend fun initNumberOfQuotesPref() {
        viewModel.numberOfQuotes.collect {
            findPreference<EditTextPreference>(getString(R.string.key_number_quotes))?.run {
                setOnBindEditTextListener { editText ->
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                }
                onPreferenceChangeListener = this@SettingsFragment
                summary = it.toString()
            }
        }
    }

    private fun showCreditsDialog() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Credits")
        builder.setMessage("Thanks to harbi for the quotes collection https://github.com/harbi/short-quotes\nApp Icon Designed by Freepik")
        builder.setPositiveButton("OK") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        builder.create().show()
    }

    private fun navigateToAboutPage() {
        val builder = LibsBuilder()
            .withAboutAppName("Quoty")
            .withAboutDescription("Beautiful quotes App sharing")
            .withAboutIconShown(true)
            .withAboutVersionShown(true)
        val bundle = Bundle()
        bundle.putSerializable("data", builder)
        findNavController().navigate(R.id.action_global_about_libraries, bundle)
    }

    private fun String.toFontName() =
        replace("_regular.ttf", "").split("_")
            .joinToString(separator = " ") { it.trimAllExtraSpaces().capitalizeEveryFirstChar() }


    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        lifecycleScope.launchWhenStarted {
            when (key) {
                getString(R.string.key_lang) -> viewModel.settingsManager.updateLanguages(
                    sharedPreferences.getStringSet(key, null)
                )
                getString(R.string.key_tags) -> viewModel.settingsManager.updateTags(
                    sharedPreferences.getStringSet(key, null)
                )
                getString(R.string.key_number_quotes) -> viewModel.settingsManager.updateNumberOfQuotes(
                    sharedPreferences.getString(key, null)?.toInt()
                        ?: SettingsDataStoreManager.DEFAULT_NUMBER_OF_QUOTES
                )
                getString(R.string.key_notification) -> viewModel.settingsManager.updateNotification(
                    sharedPreferences.getBoolean(
                        key,
                        SettingsDataStoreManager.DEFAULT_NOTIFICATIONS_ENABLED
                    )
                )
                getString(R.string.key_notify_every) -> viewModel.settingsManager.updateNotifyEvery(
                    sharedPreferences.getString(
                        key,
                        null
                    )?.toInt() ?: SettingsDataStoreManager.DEFAULT_NOTIFY_EVERY_MINUTES
                )
                getString(R.string.key_font) -> viewModel.settingsManager.updateFont(
                    sharedPreferences.getString(
                        key,
                        null
                    )
                )
            }
        }
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        if (newValue == null) return false
        return if (preference.key == getString(R.string.key_number_quotes)) {
            try {
                val num = newValue.toString().toInt()
                //zero is not allowed
                num != 0
            } catch (e: NumberFormatException) {
                false
            }
        } else true
    }
}