package com.hussien.quoty.ui.quotes

/**
 * On quote action click listener.
 *
 * supply a listener to handle user click on the quote action
 * @property listener
 * @constructor Create [OnQuoteActionClickListener]
 */
class OnQuoteActionClickListener(
    val listener: (action: QuoteAction, quoteId: Long) -> Unit
) {

    fun onClick(action: QuoteAction, quoteId: Long) = listener(action, quoteId)

    enum class QuoteAction {
        LOVE,
        SHARE_IMG,
        SHARE_TXT,
        COPY_TXT
    }
}