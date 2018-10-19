package uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsViewHolder> {

    private List<String> mSuggestions;


    public SuggestionsAdapter() {
        mSuggestions = new ArrayList<>();
    }

    @Override
    @NonNull
    public SuggestionsViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new SuggestionsViewHolder (layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder (@NonNull SuggestionsViewHolder holder, int position) {
        String suggestion = mSuggestions.get(position);
        holder.bind(suggestion);
    }

    @Override
    public int getItemCount() {
        return mSuggestions.size();
    }

    public List<String> getSuggestions() {
        return mSuggestions;
    }

    public void addSuggestion(String suggestion) {
        if (!mSuggestions.contains(suggestion)) {
            mSuggestions.add(suggestion);
            notifyItemInserted(mSuggestions.size() - 1);
        }
    }

    public void addAllSuggestions(List<String> suggestions) {
        mSuggestions.addAll(suggestions);
        notifyItemRangeInserted(mSuggestions.size() - suggestions.size(), suggestions.size());
    }

    public void removeAll() {
        mSuggestions.clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }
}
