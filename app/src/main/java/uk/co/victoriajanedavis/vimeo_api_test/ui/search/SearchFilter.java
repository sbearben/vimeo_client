package uk.co.victoriajanedavis.vimeo_api_test.ui.search;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions.SuggestionItem;


public class SearchFilter extends Filter {

    private final SearchAdapter mSearchAdapter;
    private final List<SuggestionItem> mOriginalList;
    private final List<SuggestionItem> mFilteredList;


    SearchFilter (SearchAdapter adapter, List<SuggestionItem> originalList) {
        super();
        mSearchAdapter = adapter;
        this.mOriginalList = new LinkedList<>(originalList);
        this.mFilteredList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        mFilteredList.clear();
        final FilterResults results = new FilterResults();

        if (charSequence.length() == 0) {
            mFilteredList.addAll(mOriginalList);
        } else {
            final String filterPattern = charSequence.toString().toLowerCase().trim();

            for (SuggestionItem item : mOriginalList) {
                if (item.getSuggestion().toLowerCase().startsWith(filterPattern)) {
                    mFilteredList.add(item);
                }
            }
        }

        results.values = mFilteredList;
        results.count = mFilteredList.size();
        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        mSearchAdapter.removeAll();
        mSearchAdapter.addItems((List<SuggestionItem>) filterResults.values);
        mSearchAdapter.notifyDataSetChanged();
    }
}
