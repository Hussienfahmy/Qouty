package com.hussien.qouty.data;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface QuotesDao {

    @Query("SELECT * FROM quotes WHERE currently_show = 1")
    LiveData<List<Quote>> getQuotes();

    @Query("SELECT * From quotes where tags in (:tags) AND lang in (:lang) ORDER by random() limit 1")
    Quote getRandomQuote(List<String> tags, List<String> lang);

    @Query("UPDATE quotes SET currently_show = 1 WHERE id IN (" +
            "SELECT id FROM quotes WHERE tags IN (:tags) " +
            "AND lang IN (:lang) ORDER BY RANDOM()" +
            " LIMIT :number_of_quotes_to_show)" +
            "")
    void setRandomCurrentlyShow(int number_of_quotes_to_show, List<String> tags, List<String> lang);

    @Query("UPDATE quotes SET currently_show = 0")
    void clearCurrentShow();

    @Insert()
    List<Long> insertQuote(List<Quote> quoteList);

    @Query("SELECT COUNT(id) FROM quotes")
    int getCount();

    @Query("SELECT tags FROM quotes group by tags")
    String[] getAllTags();

    @Update
    void updateQuote(Quote quote);

    @Query("SELECT * FROM quotes WHERE loved = 1")
    List<Quote> getFavoritesList();
}