package com.hussien.quoty.sync

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hussien.quoty.utilities.ActionsUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Quotes broad cast receiver.
 *
 * receiver to receive the actions from notifications
 * @constructor Create empty constructor for quotes broad cast receiver
 */
@AndroidEntryPoint
class QuotesBroadCastReceiver: BroadcastReceiver() {

    @Inject
    lateinit var actionsUtils: ActionsUtils

    override fun onReceive(context: Context, intent: Intent) {
        val quoteId = intent.getLongExtra(EXTRA_QUOTE_ID, -1)
        if (quoteId == -1L) return

        when (intent.action) {
            ACTION_QUOTE_LOVE -> actionsUtils.addQuoteToFavourites(quoteId)
            ACTION_QUOTE_SHARE_TXT -> actionsUtils.shareText(quoteId)
            ACTION_QUOTE_SHARE_IMG -> actionsUtils.shareImg(quoteId)
        }
    }

    companion object {
        const val EXTRA_QUOTE_ID = "quote_id"
        const val REQUEST_CODE_LOVE = 1
        const val REQUEST_CODE_SHARE_TEXT = 2
        const val REQUEST_CODE_SHARE_IMG = 3

        const val ACTION_QUOTE_SHARE_IMG = "com.hussien.quoty.share_Img"
        const val ACTION_QUOTE_SHARE_TXT = "com.hussien.quoty.share_text"
        const val ACTION_QUOTE_LOVE = "com.hussien.quoty.love_quote"
    }
}