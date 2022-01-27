package com.hussien.qouty.ui.settings

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.preference.PreferenceManager
import com.hussien.qouty.R
import com.hussien.qouty.annotations.OpenForTesting
import com.hussien.qouty.models.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore(name = "settings")

@OpenForTesting
@Singleton
class SettingsDataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
){

    private val dataStore = context.dataStore

    val settingsPreferencesFlow = dataStore.data
        .map { preference ->
            val numberOfQuotes = preference[PreferencesKeys.NUMBER_OF_QUOTES]?: DEFAULT_NUMBER_OF_QUOTES
            val tags = preference[PreferencesKeys.TAGS]?: DEFAULT_TAGS
            val languages = preference[PreferencesKeys.LANGUAGES]?: DEFAULT_LANGUAGES
            val font = preference[PreferencesKeys.FONT]?: DEFAULT_FONT
            val notificationsEnabled = preference[PreferencesKeys.NOTIFICATIONS_ENABLED]?: DEFAULT_NOTIFICATIONS_ENABLED
            val notifyEveryMinute = preference[PreferencesKeys.NOTIFY_EVERY_MINUTES]?: DEFAULT_NOTIFY_EVERY_MINUTES
            val firstTimeAppOpen = preference[PreferencesKeys.FIRST_TIME_APP_OPEN] ?: DEFAULT_FIRST_TIME_APP_OPEN
            Settings(
                numberOfQuotes,
                tags,
                languages,
                font,
                notificationsEnabled,
                notifyEveryMinute,
                firstTimeAppOpen
            )
        }

    suspend fun updateNumberOfQuotes(numberOfQuotes: Int?) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.NUMBER_OF_QUOTES] = numberOfQuotes?: DEFAULT_NUMBER_OF_QUOTES
        }
    }

    suspend fun updateTags(tags: Set<String>?) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.TAGS] = tags?: DEFAULT_TAGS
        }
    }

    suspend fun updateLanguages(languages: Set<String>?) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.LANGUAGES] = languages?: DEFAULT_LANGUAGES
        }
    }

    suspend fun updateFont(font: String?) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.FONT] = font ?: DEFAULT_FONT
        }
    }

    suspend fun updateFirstTimeAppOpen(b: Boolean?) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.FIRST_TIME_APP_OPEN] = b ?: DEFAULT_FIRST_TIME_APP_OPEN
        }
    }

    suspend fun updateNotification(enable: Boolean?) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.NOTIFICATIONS_ENABLED] = enable?: DEFAULT_NOTIFICATIONS_ENABLED
        }
    }

    suspend fun updateNotifyEvery(notifyEveryMinute: Int?) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.NOTIFY_EVERY_MINUTES] = notifyEveryMinute?: DEFAULT_NOTIFY_EVERY_MINUTES
        }
    }


    suspend fun writeToSharedPref() {
        withContext(Dispatchers.IO) {
            val settings = settingsPreferencesFlow.first()
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            with(settings) {
                sharedPreferences.edit().run {
                    putString(
                        context.getString(R.string.key_number_quotes),
                        numberOfQuotes.toString()
                    )
                    putStringSet(context.getString(R.string.key_tags), tags)
                    putStringSet(context.getString(R.string.key_lang), languages)
                    putString(context.getString(R.string.key_font), font)
                    putBoolean(context.getString(R.string.key_notification), notificationsEnabled)
                    putString(
                        context.getString(R.string.key_notify_every),
                        (notifyEveryMinutes/60).toString()
                    )
                }
            }
        }
    }

    private object PreferencesKeys {
        val NUMBER_OF_QUOTES = intPreferencesKey("number_of_quotes")
        val TAGS = stringSetPreferencesKey("tags")
        val LANGUAGES = stringSetPreferencesKey("languages")
        val FONT = stringPreferencesKey("font")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val NOTIFY_EVERY_MINUTES = intPreferencesKey("notify_every")
        val FIRST_TIME_APP_OPEN = booleanPreferencesKey("first_time_app_open")
    }

    companion object {
        const val DEFAULT_NUMBER_OF_QUOTES = 10
        private val DEFAULT_LANGUAGES = setOf("en")
        private val DEFAULT_TAGS = setOf("love")
        private const val DEFAULT_FONT = "Default"
        const val DEFAULT_NOTIFICATIONS_ENABLED = true
        const val DEFAULT_NOTIFY_EVERY_MINUTES = 60
        private const val DEFAULT_FIRST_TIME_APP_OPEN = false
    }
}