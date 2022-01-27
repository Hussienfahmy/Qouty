package com.hussien.qouty.sync

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.hussien.qouty.utilities.ActionsUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
@SmallTest
class QuotesBroadCastReceiverTest {

    private lateinit var actionsUtilsMocked: ActionsUtils
    private lateinit var receiver: QuotesBroadCastReceiver
    private lateinit var context: Context
    private val quoteId = 123L

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()


        actionsUtilsMocked = Mockito.mock(ActionsUtils::class.java)
        receiver = QuotesBroadCastReceiver()
        // todo we need another readable solution
        // to init the late init var which injected by hilt
        receiver.onReceive( context,
            Intent(QuotesBroadCastReceiver.ACTION_QUOTE_LOVE).apply {
                putExtra(QuotesBroadCastReceiver.EXTRA_QUOTE_ID, quoteId)
            }
        )
        // then replace the injected one by the mock
        receiver.actionsUtils = actionsUtilsMocked
    }

    @Test
    fun addQuoteToFavourite() {
        val intent = Intent(QuotesBroadCastReceiver.ACTION_QUOTE_LOVE).apply {
            putExtra(QuotesBroadCastReceiver.EXTRA_QUOTE_ID, quoteId)
        }

        receiver.onReceive(context, intent)

        Mockito.verify(receiver.actionsUtils).addQuoteToFavourites(quoteId)
    }

    @Test
    fun shareQuoteText() {
        val intent = Intent(QuotesBroadCastReceiver.ACTION_QUOTE_SHARE_TXT).apply {
            putExtra(QuotesBroadCastReceiver.EXTRA_QUOTE_ID, quoteId)
        }

        receiver.onReceive(context, intent)

        Mockito.verify(actionsUtilsMocked).shareText(quoteId)
    }

    @Test
    fun shareQuoteImage() {
        val intent = Intent(QuotesBroadCastReceiver.ACTION_QUOTE_SHARE_IMG).apply {
            putExtra(QuotesBroadCastReceiver.EXTRA_QUOTE_ID, quoteId)
        }

        receiver.onReceive(context, intent)

        Mockito.verify(actionsUtilsMocked).shareImg(quoteId)
    }
}