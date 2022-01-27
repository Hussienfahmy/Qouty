package com.hussien.qouty.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hussien.qouty.models.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {
    @Query("SELECT * FROM Quotes WHERE id IN (:quotesIds)")
    fun getQuotes(quotesIds: List<Long>): Flow<List<Quote>>

    @Query("SELECT * FROM Quotes WHERE id = :quoteId")
    suspend fun get(quoteId: Long): Quote

    @Query("SELECT * FROM Quotes WHERE lang IN (:lang) AND tag IN (:tags) ORDER by random() limit 1")
    suspend fun getRandomQuote(tags: Set<String>, lang: Set<String>): Quote

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertQuotes(quotes: List<Quote>): List<Long>

    @Query("SELECT COUNT(id) FROM Quotes")
    suspend fun count(): Long

    @Query("UPDATE Quotes set loved = 1 WHERE id = :quoteId")
    suspend fun addToFavourite(quoteId: Long)

    @Query("UPDATE Quotes set loved = 0 WHERE id = :quoteId")
    suspend fun removeQuoteFromFavourite(quoteId: Long)

    @get:Query("SELECT DISTINCT tag FROM quotes ORDER BY tag")
    val allTags: Flow<List<String>>

    @get:Query("SELECT * FROM quotes WHERE loved = 1")
    val favorites: Flow<List<Quote>>

    /**
     * Get random quote ids.
     *
     * @param numberOfQuotes Number of wanted quotes
     * @param languages Languages of wanted quotes
     * @param tags Tags of wanted quotes
     * @return flow of random ids to be able to get updates when adding or removing from favourites
     */
    @Query("SELECT id FROM Quotes WHERE lang in (:languages) AND tag in (:tags) ORDER BY RANDOM() LIMIT :numberOfQuotes")
    fun getRandomQuoteIds(
        numberOfQuotes: Int,
        languages: Set<String>,
        tags: Set<String>
    ): Flow<List<Long>?>
}