package com.hussien.quoty.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.hussien.quoty.models.Quote
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class QuotesRepositoryTest {

    private lateinit var repository: QuotesRepository
    private lateinit var database: AppDatabase
    private lateinit var quotes: List<Quote>
    private lateinit var dispatcher: TestDispatcher

    @get:Rule
    var instantExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        dispatcher = StandardTestDispatcher()

        Dispatchers.setMain(dispatcher)

        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        repository = QuotesRepository(
            database.quotesDao, dispatcher
        )

        quotes = listOf(
            Quote(id = 1, text = "T1", author = "A1", lang = "en", tag = "love"),
            Quote(id = 2, text = "T2", author = "A2", lang = "en", tag = "love"),
            Quote(id = 3, text = "T3", author = "A3", lang = "ar", tag = "love"),
        )

        runTest {
            repository.upsertQuotes(quotes)
        }
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
        dispatcher.cancel()
    }

    @Test
    fun getQuotes() = runTest {
        val returnedQuotes = repository.getQuotes(listOf(1L,2L,3L)).first()
        assertThat(returnedQuotes, equalTo(quotes))
    }

    @Test
    fun getQuote() = runTest{
        val returnedQuote = repository.getQuote(1L)
        assertThat(returnedQuote, equalTo(quotes.first { it.id == 1L }))
    }

    @Test
    fun quotesCount() = runTest {
        val count = repository.quotesCount()
        assertThat(count, equalTo(quotes.size.toLong()))
    }

    @Test
    fun upsertQuotes() = runTest{
        val quoteToInsert = Quote(id = 4, text = "T4", author = "A4", lang = "ar", tag = "love")
        repository.upsertQuotes(listOf(quoteToInsert))
        val returnedQuote = repository.getQuote(quoteToInsert.id)
        assertThat(returnedQuote, equalTo(quoteToInsert))
    }

    @Test
    fun getAllTags() = runTest {
        val allTags = repository.allTags.first()
        assertThat(allTags, equalTo(quotes.map { it.tag }.distinct()))
    }

    @Test
    fun addToFavourite_getFavoriteQuotes() = runTest{
        repository.addToQuoteFavourite(quotes.first().id)
        val favouriteQuotes = repository.favoriteQuotes.first()
        assertThat(favouriteQuotes.map { it.id }, hasItem(quotes.first().id))
        assertThat(favouriteQuotes.map { it.isLoved }, not(hasItem(false)))
    }

    @Test
    fun getRandomQuote() = runTest {
        val randomQuote = repository.getRandomQuote(
            quotes.map { it.tag }.toSet(), quotes.map { it.lang }.toSet()
        )

        assertThat(randomQuote, isIn(quotes))
    }

    @Test
    fun removeQuoteFromFavourite() = runTest {
        val quote = quotes.first()
        repository.addToQuoteFavourite(quote.id)
        var favouriteQuotes = repository.favoriteQuotes.first()
        assertThat(favouriteQuotes.map { it.id }, hasItem(quote.id))
        assertThat(favouriteQuotes.map { it.isLoved }, not(hasItem(false)))

        repository.removeQuoteFromFavourite(quote.id)
        favouriteQuotes = repository.favoriteQuotes.first()
        assertThat(favouriteQuotes, `is`(empty()))
    }

    @Test
    fun getRandomQuoteIds() = runTest {
        val randomIds = repository.getRandomQuoteIds(
            quotes.size, quotes.map { it.lang }.toSet(), quotes.map { it.tag }.toSet()
        ).first()

        val allIds = quotes.map { it.id }
        assertThat(randomIds, everyItem(isIn(allIds)))
    }

}