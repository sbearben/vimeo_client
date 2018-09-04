package uk.co.victoriajanedavis.vimeo_api_test.ui.search;

import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;

public class SearchAdapter extends ListAdapter<String> { //implements Filterable {

    private SearchFilter mSearchFilter;


    public SearchAdapter (BaseFragment fragment, ListItemViewHolder.ListItemViewHolderGenerator<String> viewHolderGenerator) {
        super(fragment, viewHolderGenerator);
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
