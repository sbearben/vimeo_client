package uk.co.victoriajanedavis.vimeo_api_test.ui.base.list;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;


public class ListAdapter<T> extends RecyclerView.Adapter<ListItemViewHolder<T>> {

    public static final String TAG = ListAdapter.class.getSimpleName();

    protected final BaseFragment mBaseFragment;

    protected final List<T> mDataList;
    protected final ListItemViewHolder.ListItemViewHolderGenerator<T> mViewHolderGenerator;

    /**
     * ViewTypes serve as a mapping point to which layout should be inflated
     */
    public static final int VIEW_TYPE_LIST = 0;
    public static final int VIEW_TYPE_LOADING = 1;

    @IntDef({VIEW_TYPE_LIST, VIEW_TYPE_LOADING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    @ViewType
    private int mViewType;


    public ListAdapter (BaseFragment fragment, ListItemViewHolder.ListItemViewHolderGenerator<T> viewHolderGenerator) {
        mBaseFragment = fragment;
        mViewHolderGenerator = viewHolderGenerator;

        mDataList = new ArrayList<>();
        mViewType = VIEW_TYPE_LIST;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position) == null ? VIEW_TYPE_LOADING : mViewType;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    @NonNull
    @Override
    public ListItemViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return onIndicationViewHolder(parent);
        }

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return mViewHolderGenerator.generateViewHolder(mBaseFragment, layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder<T> holder, final int position) {
        if (holder.getItemViewType() == VIEW_TYPE_LOADING) {
            return; // no-op
        }
        T listItem = mDataList.get(position);
        holder.bind(listItem);
    }

    @Override
    public void onViewRecycled(@NonNull ListItemViewHolder<T> holder) {
        super.onViewRecycled(holder);

        /* If we don't perform this check recycle() causes the app to crash when we back out of it.
         *   - issue and solution here: https://github.com/bumptech/glide/issues/1484
         */
        if (!((AppCompatActivity) mBaseFragment.getContext()).isFinishing()) {
            holder.recycle();
        }
    }

    protected ListItemViewHolder<T> onIndicationViewHolder(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ProgressBarViewHolder (mBaseFragment, layoutInflater, parent);
    }

    public void add(T item) {
        add(null, item);
    }

    public void add(@Nullable Integer position, T item) {
        if (position != null) {
            mDataList.add(position, item);
            notifyItemInserted(position);
        } else {
            mDataList.add(item);
            notifyItemInserted(mDataList.size() - 1);
        }
    }

    public void addItems(List<T> itemsList) {
        mDataList.addAll(itemsList);
        notifyItemRangeInserted(mDataList.size()-itemsList.size(), itemsList.size());
    }

    public List<T> getItems() {
        return mDataList;
    }

    public void remove(int position) {
        if (mDataList.size() < position) {
            Log.w(TAG, "The item at position: " + position + " doesn't exist");
            return;
        }
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public void updateItem(int index, T item) {
        mDataList.remove(index);
        mDataList.add(index, item);
        notifyItemChanged(index);
    }

    public boolean addLoadingView() {
        if (getItemViewType(mDataList.size() - 1) != VIEW_TYPE_LOADING) {
            add(null);
            return true;
        }
        return false;
    }

    public boolean removeLoadingView() {
        if (mDataList.size() > 1) {
            int loadingViewPosition = mDataList.size() - 1;
            if (getItemViewType(loadingViewPosition) == VIEW_TYPE_LOADING) {
                remove(loadingViewPosition);
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    /**
     * ViewHolders
     */
    public class ProgressBarViewHolder extends ListItemViewHolder<T> {

        public final ProgressBar mProgressBar;

        public ProgressBarViewHolder (BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
            //super(view);
            super (baseFragment, inflater.inflate (R.layout.item_progress_bar, parent, false));
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }

        @Override
        public void bind(@NonNull T listItem) {
        }

        @Override
        public void recycle() {
        }
    }
}
