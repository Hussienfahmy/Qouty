package com.hussien.qouty.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hussien.qouty.AppExecutor;
import com.hussien.qouty.R;
import com.hussien.qouty.adapter.QuotesAdapter;
import com.hussien.qouty.data.QuotesRepository;
import com.hussien.qouty.databinding.FragmentQoutesBinding;
import com.hussien.qouty.utilities.SharedPreferenceUtils;
import com.hussien.qouty.view_model.QuotesViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuotesFragment extends Fragment {
    private QuotesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNewRowsToShow();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return FragmentQoutesBinding.inflate(inflater, container, false).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentQoutesBinding binding = FragmentQoutesBinding.bind(view);
        adapter = new QuotesAdapter(getActivity());
        binding.recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        binding.swipeRefresh.setOnRefreshListener(this::setNewRowsToShow);

        startViewModel(binding);
    }

    private void setNewRowsToShow() {
        SetNewRowsToShowRunnable runnable = new SetNewRowsToShowRunnable(getActivity());
        AppExecutor.getInstance().getDiskIo().execute(runnable);
    }

    private void startViewModel(FragmentQoutesBinding binding) {
        if (getActivity() == null) return;
        QuotesViewModel quotesViewModel = new QuotesViewModel(getActivity().getApplication());
        quotesViewModel.getQuotesList().observe(getViewLifecycleOwner(), quotes -> {
            adapter.setQuoteList(quotes);
            binding.swipeRefresh.setRefreshing(false);
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                    .remove(getString(R.string.key_should_refresh)).apply();
        });
    }

    public static class SetNewRowsToShowRunnable implements Runnable {

        private final Activity activity;

        public SetNewRowsToShowRunnable(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            QuotesRepository quotesRepository = QuotesRepository.getRepo_instance(activity.getApplication());
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

            String numToShowSt = sharedPreferences.getString(activity.getString(R.string.key_number_quotes),
                    activity.getString(R.string.number_quotes_default));

            int numToShow = Integer.parseInt(numToShowSt);
            List<String> tags = SharedPreferenceUtils.getArrayFromSharedPref(activity, activity.getString(R.string.key_tags));
            List<String> lang = SharedPreferenceUtils.getArrayFromSharedPref(activity, activity.getString(R.string.key_lang));

            quotesRepository.clearCurrentShow();
            quotesRepository.setRandomCurrentlyShow(numToShow, tags, lang);
        }
    }
}