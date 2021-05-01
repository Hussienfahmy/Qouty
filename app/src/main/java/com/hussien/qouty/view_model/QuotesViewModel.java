package com.hussien.qouty.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hussien.qouty.data.Quote;
import com.hussien.qouty.data.QuotesRepository;

import java.util.List;

public class QuotesViewModel extends AndroidViewModel {

    public QuotesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Quote>> getQuotesList(){
        return QuotesRepository.getRepo_instance(getApplication()).getQuoteList();
    }
}