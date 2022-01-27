package com.hussien.qouty.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hussien.qouty.data.QuotesRepository
import com.hussien.qouty.sync.NotificationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Settings view model.
 *
 * view model to handle the logic of saving and displaying the user preferences to and from preference screen
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    val settingsManager: SettingsDataStoreManager,
    repository: QuotesRepository,
    private val workManager: WorkManager,
) : ViewModel() {

    val allTags = repository.allTags.map { it.toTypedArray() }

    val settings = settingsManager.settingsPreferencesFlow
    val numberOfQuotes = settings.map { it.numberOfQuotes }
    val tags = settings.map { it.tags }
    val languages = settings.map { it.languages }
    val font = settings.map { it.font }
    val notificationsEnabled = settings.map { it.notificationsEnabled }
    val notifyEvery = settings.map { it.notifyEveryMinutes }

    init {
        viewModelScope.launch {
            settings.collect {
                if (it.firstTimeAppOpen) {
                    // to make sure all default values are saved to
                    // shared preferences to see it when the user open a dialog of preferences
                    settingsManager.writeToSharedPref()
                    settingsManager.updateFirstTimeAppOpen(true)
                }
            }
        }

        viewModelScope.launch {
            combine(notificationsEnabled, notifyEvery) { enabled, everyMinute ->
                enabled to everyMinute
            }.collect { (enabled, everyMinute) ->
                Timber.d("Notifications Enabled = $enabled")
                if (enabled) {
                    // enqueue work
                    val request = PeriodicWorkRequestBuilder<NotificationWorker>(
                        everyMinute.toLong(),
                        TimeUnit.MINUTES,
                        10,
                        TimeUnit.MINUTES
                    ).addTag(NotificationWorker.TAG)
                        .build()
                    workManager.enqueueUniquePeriodicWork(
                        "notification",
                        ExistingPeriodicWorkPolicy.REPLACE,
                        request
                    )
                } else {
                    // disable all work
                    workManager.cancelAllWorkByTag(NotificationWorker.TAG)
                }
            }
        }
    }
}