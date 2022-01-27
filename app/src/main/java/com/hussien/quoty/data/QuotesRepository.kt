package com.hussien.quoty.data

import com.hussien.quoty.annotations.OpenForTesting
import com.hussien.quoty.di.IODispatcher
import com.hussien.quoty.models.Quote
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class QuotesRepository @Inject constructor(
    private val quotesDao: QuotesDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {

    fun getQuotes(quotesIds: List<Long>): Flow<List<Quote>> {
        return quotesDao.getQuotes(quotesIds)
    }

    suspend fun getQuote(quoteId: Long) = withContext(ioDispatcher) {
        quotesDao.get(quoteId)
    }

    suspend fun quotesCount(): Long = withContext(ioDispatcher) {
        quotesDao.count()
    }

    suspend fun upsertQuotes(quoteList: List<Quote>): List<Long> = withContext(ioDispatcher) {
        quotesDao.upsertQuotes(quoteList)
    }

    val allTags: Flow<List<String>>
        get() = quotesDao.allTags

    val favoriteQuotes: Flow<List<Quote>>
        get() = quotesDao.favorites

    suspend fun getRandomQuote(tags: Set<String>, lang: Set<String>): Quote =
        withContext(ioDispatcher) {
            quotesDao.getRandomQuote(tags, lang)
        }

    suspend fun addToQuoteFavourite(quoteId: Long) {
        withContext(ioDispatcher) {
            quotesDao.addToFavourite(quoteId)
        }
    }

    suspend fun removeQuoteFromFavourite(quoteId: Long) {
        withContext(ioDispatcher) {
            quotesDao.removeQuoteFromFavourite(quoteId)
        }
    }

    fun getRandomQuoteIds(
        numberOfQuotes: Int,
        languages: Set<String>,
        tags: Set<String>
    ): Flow<List<Long>?> {
        return quotesDao.getRandomQuoteIds(
            numberOfQuotes,
            languages,
            tags
        )
    }
}