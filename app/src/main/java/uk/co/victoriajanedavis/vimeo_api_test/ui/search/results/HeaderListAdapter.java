package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

public class HeaderListAdapter<T> extends ListAdapter<T> {

    public static final int VIEW_TYPE_HEADER = 2;

    @IntDef({VIEW_TYPE_LIST, VIEW_TYPE_LOADING, VIEW_TYPE_HEADER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    private int mTotal = 0;
    private String mSingularHeaderMetric = "";
    private String mPluralHeaderMetric = "";


    public HeaderListAdapter (Context context, BaseFragment fragment, ListItemViewHolder.ListItemViewHolderGenerator<T> viewHolderGenerator) {
        super(context, fragment, viewHolderGenerator);
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return VIEW_TYPE_HEADER;
        }
        return super.getItemViewType(position);
    }

    /*
    @Override
    public int getItemCount() {
        return mDataList.size();
    }
    */

    @Override
    public ListItemViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return onHeaderViewHolder(parent);
        }

        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder<T> holder, final int position) {
        if (holder.getItemViewType() == VIEW_TYPE_HEADER) {
            holder.bind(null);
            return;
        }
        super.onBindViewHolder(holder, position-1);
    }

    private ListItemViewHolder<T> onHeaderViewHolder(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new HeaderViewHolder (mContext, mBaseFragment, layoutInflater, parent);
    }

    /*
    @Override
    public void add(@Nullable Integer position, T item) {
        if (position != null) {
            mDataList.add(position, item);
            notifyItemInserted(position+1);
        } else {
            mDataList.add(item);
            notifyItemInserted(mDataList.size());
        }
    }

    @Override
    public void addItems(List<T> itemsList) {
        mDataList.addAll(itemsList);
        notifyItemRangeInserted(getItemCount(), mDataList.size());
    }

    @Override
    public void remove(int position) {
        if (mDataList.size() < position) {
            return;
        }
        mDataList.remove(position);
        notifyItemRemoved(position+1);
    }
    */

    @Override
    public boolean isEmpty() {
        return getItemCount() == 0 || getItemCount() == 1;
    }

    public void setCollectionTotal (int total) {
        mTotal = total;
    }

    public void setSingularHeaderMetric (String singular) {
        mSingularHeaderMetric = singular;
    }

    public void setPluralHeaderMetric (String plural) {
        mPluralHeaderMetric = plural;
    }

    /**
     * ViewHolders
     */
    public class HeaderViewHolder extends ListItemViewHolder<T> {

        public final TextView textView;

        public HeaderViewHolder (Context context, BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
            //super(view);
            super (context, baseFragment, inflater.inflate (R.layout.item_list_header, parent, false));
            textView = (TextView) itemView.findViewById(R.id.item_list_header_textview);
        }

        @Override
        public void bind(@Nullable T listItem) {
            String headerText = VimeoApiServiceUtil.formatIntMetric(mTotal, mSingularHeaderMetric, mPluralHeaderMetric);
            textView.setText(headerText);
        }

        @Override
        public void recycle() {
        }
    }


}
