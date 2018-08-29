package uk.co.victoriajanedavis.vimeo_api_test.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

/**
 *  Subclasses must implement a constructor of form:
 *      SubTypeViewHolder(Context context, BaseFragment baseFragment, LayoutInflator layoutInflater, ViewGroup parent)
 */
public abstract class ListItemViewHolder<T> extends RecyclerView.ViewHolder {

    protected Context mContext;
    protected BaseFragment mBaseFragment;

    protected T mListItem;
    protected View mListItemView;


    public ListItemViewHolder (Context context, BaseFragment baseFragment, View itemView) {
        super (itemView);

        mContext = context;
        mBaseFragment = baseFragment;
        mListItemView = itemView;
    }

    public abstract void bind (@NonNull T listItem);

    public abstract void recycle();

    /*
    public void recycle() {
        mContext = null;
        mBaseFragment = null;
    }
    */

    /**
     * Interface for generating new ViewHolders
     */
    public interface ListItemViewHolderGenerator<T> {
        ListItemViewHolder<T> generateViewHolder(Context context, BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent);
    }


}
