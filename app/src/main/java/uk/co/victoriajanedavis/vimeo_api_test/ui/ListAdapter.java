package uk.co.victoriajanedavis.vimeo_api_test.ui;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} populated with {@link ListItem}
 * makes the call to the {@link ListAdapter.InteractionListener}.
 */
public class ListAdapter<T extends ListItem> extends RecyclerView.Adapter<ListItemViewHolder<T>> {

    public static final String TAG = ListAdapter.class.getSimpleName();

    protected Context mContext;
    protected BaseFragment mBaseFragment;

    private InteractionListener mListInteractionListener;
    protected final List<T> mDataList;

    /* TODO: I'm using teh BiFunction here to generate our ViewHolders since I can't call a constructor for whatever class extends ListItemViewHolder<T>
     * Solution found here: https://stackoverflow.com/a/36315051/7648952
     *   - Unfortunately using the apply method of BiFunction is only supported in Android API >= 24. This is an issue I don't currently have a solution for
     */
    //private BiFunction<LayoutInflater, ViewGroup, ListItemViewHolder<T>> mViewHolderSupplier;
    protected ListItemViewHolder.ListItemViewHolderGenerator<T> mViewHolderGenerator;

    /**
     * ViewTypes serve as a mapping point to which layout should be inflated
     */
    //public static final int VIEW_TYPE_GALLERY = 0;
    public static final int VIEW_TYPE_LIST = 0;
    public static final int VIEW_TYPE_LOADING = 1;

    @IntDef({VIEW_TYPE_LIST, VIEW_TYPE_LOADING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    @ViewType
    protected int mViewType;


    public ListAdapter (Context context, BaseFragment fragment, ListItemViewHolder.ListItemViewHolderGenerator<T> viewHolderGenerator) {
        mContext = context;
        mBaseFragment = fragment;
        mViewHolderGenerator = viewHolderGenerator;

        mDataList = new ArrayList<>();
        mViewType = VIEW_TYPE_LIST;
        mListInteractionListener = null;

    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position) == null ? VIEW_TYPE_LOADING : mViewType;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public long getItemId(int position) {
        if (hasStableIds()) {
            Log.d ("ListAdapter", "HAS STABLE IDS IS USED");
            return mDataList.size() >= position ? mDataList.get(position).getId() : -1;
        } else {
            return RecyclerView.NO_ID;
        }
    }

    @NonNull
    @Override
    public ListItemViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return onIndicationViewHolder(parent);
        }

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return mViewHolderGenerator.generateViewHolder(mContext, mBaseFragment, layoutInflater, parent);
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
        if (!((AppCompatActivity) mContext).isFinishing()) {
            holder.recycle();
        }
    }

    protected ListItemViewHolder<T> onIndicationViewHolder(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ProgressBarViewHolder (mContext, mBaseFragment, layoutInflater, parent);
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
        //notifyItemRangeInserted(getItemCount(), mDataList.size() - 1);
        // NOTE: I commented out the above line and switched it to the below line since the above seemed to be a bug
        notifyItemRangeInserted(mDataList.size() - 1, getItemCount());
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

    public int getViewType() {
        return mViewType;
    }

    public void setViewType(@ViewType int viewType) {
        mViewType = viewType;
    }


    /**
     * ViewHolders
     */
    public class ProgressBarViewHolder extends ListItemViewHolder<T> {

        public final ProgressBar progressBar;

        public ProgressBarViewHolder (Context context, BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
            //super(view);
            super (context, baseFragment, inflater.inflate (R.layout.item_progress_bar, parent, false));
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }

        @Override
        public void bind(@NonNull T listItem) {
        }

        @Override
        public void recycle() {
        }
    }

    /**
     * Interface for handling list interactions
     */
    public interface InteractionListener<T extends ListItem> {
        void onListClick(T item, View sharedElementView, int adapterPosition);
    }

    public void setListInteractionListener(InteractionListener listInteractionListener) {
        mListInteractionListener = listInteractionListener;
    }
}
