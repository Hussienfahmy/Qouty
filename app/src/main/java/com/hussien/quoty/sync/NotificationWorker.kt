package com.hussien.quoty.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hussien.quoty.data.QuotesRepository
import com.hussien.quoty.di.UIDispatcher
import com.hussien.quoty.ui.settings.SettingsDataStoreManager
import com.hussien.quoty.utilities.NotificationUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

/**
 * Notification worker.
 *
 * worker to send regular random notification
 */
@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: QuotesRepository,
    private val settingsDataStoreManager: SettingsDataStoreManager,
    private val notificationUtils: NotificationUtils,
    @UIDispatcher private val dispatcherMain: MainCoroutineDispatcher
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val settings = settingsDataStoreManager.settingsPreferencesFlow.first()
        val randomQuote = repository.getRandomQuote(settings.tags, settings.languages)
        withContext(dispatcherMain) {
            notificationUtils.sendQuoteNotification(randomQuote)
        }
        return Result.success()
    }

    companion object {
        const val TAG = "notification_worker"
    }
}