package uk.co.victoriajanedavis.vimeo_api_test.paging.base;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

public class VimeoCollectionAdapter<T> extends PagedListAdapter<T, ListItemViewHolder<T>> {

    protected final BaseFragment mBaseFragment;
    protected final ListItemViewHolder.ListItemViewHolderGenerator<T> mViewHolderGenerator;


    public VimeoCollectionAdapter(BaseFragment fragment, ListItemViewHolder.ListItemViewHolderGenerator<T> viewHolderGenerator) {
        super(new DIFF_CALLBACK<T>());
        mBaseFragment = fragment;
        mViewHolderGenerator = viewHolderGenerator;
    }

    @NonNull
    @Override
    public ListItemViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return mViewHolderGenerator.generateViewHolder(mBaseFragment, layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder<T> holder, int position) {
        T item = getItem(position);
        if (item != null) {
            holder.bind(item);
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    private static class DIFF_CALLBACK<T> extends DiffUtil.ItemCallback<T> {

        @Override
        public boolean areItemsTheSame(T oldItem, T newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(T oldItem, T newItem) {
            return oldItem.equals(newItem);
        }
    }
}
