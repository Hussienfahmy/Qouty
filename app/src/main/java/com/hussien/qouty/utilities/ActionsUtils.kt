package com.hussien.qouty.utilities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Typeface
import android.net.Uri
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.FileProvider
import com.hussien.qouty.annotations.OpenForTesting
import com.hussien.qouty.data.QuotesRepository
import com.hussien.qouty.databinding.QuoteLayoutBinding
import com.hussien.qouty.di.ApplicationScope
import com.hussien.qouty.di.UIDispatcher
import com.hussien.qouty.ext.toBitmap
import com.hussien.qouty.models.Quote
import com.hussien.qouty.ui.settings.SettingsDataStoreManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Actions utils.
 *
 * the logic of execution the user click on any action of the quotes
 */
@Singleton
@OpenForTesting
class ActionsUtils @Inject constructor(
    @ApplicationContext private val context: Context,
    @ApplicationScope private val scope: CoroutineScope,
    @UIDispatcher private val uiDispatcher: MainCoroutineDispatcher = Dispatchers.Main,
    private val repository: QuotesRepository,
    private val settingsDataStoreManager: SettingsDataStoreManager
) {

    fun addQuoteToFavourites(quoteId: Long) {
        scope.launch {
            repository.addToQuoteFavourite(quoteId)
        }
    }

    fun shareText(quoteId: Long) {
        scope.launch {
            val content = getQuoteContentText(quoteId)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, content)
            }
            val chooser: Intent = Intent.createChooser(shareIntent, "Share via")
            chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(chooser)
        }
    }

    private suspend fun getQuoteContentText(quoteId: Long): String {
        val quote = repository.getQuote(quoteId)
        return quote.content
    }

    fun copyText(quoteId: Long) {
        scope.launch {
            val content = getQuoteContentText(quoteId)
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(content, content)
            clipboard.setPrimaryClip(clip)
            withContext(uiDispatcher) {
                Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun shareImg(quoteId: Long) {
        scope.launch {
            // get the quote
            val quote = repository.getQuote(quoteId)
            sendImgIntent(generateQuoteLayout(quote).toBitmap())
        }
    }

    suspend fun generateQuoteLayout(quote: Quote): View {
        // get the current width
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val orientation = context.resources.configuration.orientation
        val width = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            displayMetrics.widthPixels
        } else {
            displayMetrics.heightPixels
        }
        val params = LinearLayout.LayoutParams(
            width,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        // generate the view
        val quoteLayoutBinding = withContext(uiDispatcher) {
            QuoteLayoutBinding.inflate(LayoutInflater.from(context))
        }
        quoteLayoutBinding.quote = quote

        quoteLayoutBinding.root.layoutParams = params
        quoteLayoutBinding.quoteText.layoutParams = params

        val fontName = settingsDataStoreManager.settingsPreferencesFlow.map { it.font }.first()
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "fonts/$fontName")
        quoteLayoutBinding.quoteText.typeface = typeface
        quoteLayoutBinding.quoteAuthor.typeface = typeface

        quoteLayoutBinding.executePendingBindings()
        return quoteLayoutBinding.root
    }

    private fun sendImgIntent(bitmap: Bitmap) {
        try {
            val outputFile = File(context.getExternalFilesDir(null), "quote.png")
            FileOutputStream(outputFile).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            val uri: Uri = FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName + ".provider",
                outputFile
            )
            val intent = Intent(Intent.ACTION_SEND).apply {
                setDataAndType(uri, "image/*")
                putExtra(Intent.EXTRA_STREAM, uri)
            }
            val chooser: Intent = Intent.createChooser(intent, "Share Image Via")
            chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(chooser)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}