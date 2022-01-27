package com.hussien.quoty.utilities

import android.app.NotificationManager
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.hussien.quoty.models.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasItem
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class NotificationUtilsTest {

    private lateinit var notificationUtils: NotificationUtils
    private lateinit var dispatcher: TestDispatcher
    private lateinit var notificationManager: NotificationManager

    @Before
    fun setUp() {
        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)

        val context = ApplicationProvider.getApplicationContext<Context>()

        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationUtils = NotificationUtils(
            context, null
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
    }

    @Test
    fun sendQuoteNotification() = runTest {
        val quote = Quote(1, "T1", "A1", "en", "love", false)

        notificationUtils.sendQuoteNotification(quote)

        assertThat(notificationManager.activeNotifications.map { it.id }, hasItem(NotificationUtils.QUOTES_NOTIFICATION_ID))
    }
}