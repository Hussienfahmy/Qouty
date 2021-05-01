package com.hussien.qouty.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuotesRepository {
    private static QuotesRepository repo_instance;
    private static final Object LOCK = new Object();
    private final QuotesDao quotesDao;

    public static QuotesRepository getRepo_instance(Context context) {
        if (repo_instance == null){
            synchronized (LOCK){
                if (repo_instance == null){
                    repo_instance = new QuotesRepository(context);
                }
            }
        }
        return repo_instance;
    }

    private QuotesRepository(Context context){
        this.quotesDao = Database.getInstance(context).quotesDao();
    }

    public LiveData<List<Quote>> getQuoteList(){
        return quotesDao.getQuotes();
    }

    public void setRandomCurrentlyShow(int number_of_quotes_to_show, List<String> tags, List<String> lang){
        quotesDao.setRandomCurrentlyShow(number_of_quotes_to_show, tags, lang);
    }

    public void clearCurrentShow(){
        quotesDao.clearCurrentShow();
    }

    public int getCount(){
        return quotesDao.getCount();
    }

    public List<Long> insertQuote(List<Quote> quoteList){
        return quotesDao.insertQuote(quoteList);
    }

    public String[] getAllTags(){
        return quotesDao.getAllTags();
    }

    public void updateQuote(Quote quote){
        quotesDao.updateQuote(quote);
    }

    public List<Quote> getFavoritesQuoteList() {
        return quotesDao.getFavoritesList();
    }

    public Quote getRandomQuote(List<String> tags, List<String> lang){
        return quotesDao.getRandomQuote(tags, lang);
    }
}