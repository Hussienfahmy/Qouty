package com.hussien.qouty.ui.quotes

import android.content.res.AssetManager
import android.graphics.Typeface
import androidx.lifecycle.*
import com.hussien.qouty.data.QuotesRepository
import com.hussien.qouty.ext.inverse
import com.hussien.qouty.models.Quote
import com.hussien.qouty.ui.quotes.OnQuoteActionClickListener.QuoteAction
import com.hussien.qouty.ui.settings.SettingsDataStoreManager
import com.hussien.qouty.utilities.ActionsUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val repository: QuotesRepository,
    private val actionsUtils: ActionsUtils,
    settingsDataStoreManager: SettingsDataStoreManager,
    private val assets: AssetManager
) : ViewModel() {

    // if true the all quotes saved in the favourites will display on the screen
    // otherwise a random quotes will displayed
    private val _showOnlyFavourites = MutableStateFlow(false)
    val showOnlyFavourites: StateFlow<Boolean> = _showOnlyFavourites

    // if true a loading indicator will show up
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // flow of favourite quotes
    private val favourites = repository.favoriteQuotes

    // flow of ids of the displayed quotes
    // changing when the user refresh
    private val displayedQuotesIds = MutableStateFlow<List<Long>?>(null)

    // the randomly quotes of the displayedQuotesIds
    // the concept to separate as i want to observe changes to this specific ids in case the user click on love
    // and i can't observe it if am getting randomly quotes
    private val activeQuotes: Flow<List<Quote>> = displayedQuotesIds.flatMapLatest { ids ->
        if (ids == null) return@flatMapLatest flowOf(listOf())
        // to get new data every refresh
        val quotes = repository.getQuotes(ids)
        _isLoading.value = false
        // to be able to set isLoading to false after actually getting the data
        quotes
    }

    // the settings flow of the user preferences
    private val settings = settingsDataStoreManager.settingsPreferencesFlow
    private val fontName = settings.map { it.font }
    val typeFace = fontName.map { fontName ->
        if (fontName == "Default") return@map null
        Typeface.createFromAsset(assets,"fonts/$fontName")
    }.asLiveData()


    // the quotes displayed on the screen can be random or the favourites
    val quotes = _showOnlyFavourites.flatMapLatest {
        if (it) favourites else activeQuotes
    }.asLiveData()

    init {
        // to set an initial value to randomQuoteIds
        refresh()
    }

    fun refresh() {
        _isLoading.value = true
        viewModelScope.launch {
            // only refresh if a random quotes are showing NOT the favourites
            if (_showOnlyFavourites.value) {
                _isLoading.value = false
            } else {
                val setting = settings.first()
                displayedQuotesIds.emit(
                    repository.getRandomQuoteIds(
                        setting.numberOfQuotes,
                        setting.languages,
                        setting.tags
                    ).first()
                )
            }
        }
    }

    // triggers when the user click on any quote action
    fun onQuoteAction(action: QuoteAction, quoteId: Long) {
        when (action) {
            QuoteAction.SHARE_IMG -> actionsUtils.shareImg(quoteId)
            QuoteAction.SHARE_TXT -> actionsUtils.shareText(quoteId)
            QuoteAction.COPY_TXT -> actionsUtils.copyText(quoteId)
            QuoteAction.LOVE -> viewModelScope.launch {
                val quote = repository.getQuote(quoteId)
                if (quote.isLoved) {
                    repository.removeQuoteFromFavourite(quoteId)
                } else {
                    actionsUtils.addQuoteToFavourites(quoteId)
                }
            }
        }
    }

    // to show the favourites on randoms
    fun onFavouritesClick() {
        _isLoading.value = true
        _showOnlyFavourites.inverse()
        _isLoading.value = false
    }
}