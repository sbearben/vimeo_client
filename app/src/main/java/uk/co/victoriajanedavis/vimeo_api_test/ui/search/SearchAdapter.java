package uk.co.victoriajanedavis.vimeo_api_test.ui.search;

import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions.SuggestionItem;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;

public class SearchAdapter extends ListAdapter<SuggestionItem> { //implements Filterable {

    private SearchFilter mSearchFilter;


    public SearchAdapter (Context context, BaseFragment fragment, ListItemViewHolder.ListItemViewHolderGenerator<SuggestionItem> viewHolderGenerator) {
        super(context, fragment, viewHolderGenerator);
    }

    /*
    @Override
    public Filter getFilter() {
        if (mSearchFilter == null) {
            mSearchFilter = new SearchFilter(this, getItems());
        }
        return mSearchFilter;
    }

    public void refreshFilter() {
        mSearchFilter = new SearchFilter(this, getItems());
    }
    */
}
