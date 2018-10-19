package uk.co.victoriajanedavis.vimeo_api_test.ui.video.comments;

import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.reply.ReplyActivity;
import uk.co.victoriajanedavis.vimeo_api_test.ui.reply.ReplyFragment;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoTextUtil;

public class CommentHeaderListAdapter<T> extends ListAdapter<T> {

    private static final int HEADER_OFFSET = 1;

    static final int VIEW_TYPE_HEADER = 2;

    @IntDef({VIEW_TYPE_LIST, VIEW_TYPE_LOADING, VIEW_TYPE_HEADER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    private VimeoConnection mConnection;


    public CommentHeaderListAdapter(BaseFragment fragment, VimeoConnection connection,
                                    ListItemViewHolder.ListItemViewHolderGenerator<T> viewHolderGenerator) {
        super(fragment, viewHolderGenerator);
        mConnection = connection;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return VIEW_TYPE_HEADER;
        }
        return super.getItemViewType(position-HEADER_OFFSET);
    }

    @Override
    public int getItemCount() {
        return mDataList.size() + HEADER_OFFSET;
    }

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
        super.onBindViewHolder(holder, position-HEADER_OFFSET);
    }

    private ListItemViewHolder<T> onHeaderViewHolder(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new HeaderViewHolder (mBaseFragment, layoutInflater, parent);
    }

    @Override
    public void add(@Nullable Integer position, T item) {
        if (position != null) {
            mDataList.add(position, item);
            notifyItemInserted(position+HEADER_OFFSET);
        } else {
            mDataList.add(item);
            notifyItemInserted(mDataList.size());
        }
    }

    @Override
    public void addItems(List<T> itemsList) {
        mDataList.addAll(itemsList);
        notifyItemRangeInserted(mDataList.size()-itemsList.size()+HEADER_OFFSET, itemsList.size());
    }

    @Override
    public void remove(int position) {
        if (mDataList.size() < position) {
            return;
        }
        mDataList.remove(position);
        notifyItemRemoved(position+HEADER_OFFSET);
    }

    @Override
    public void removeAll() {
        super.removeAll();
    }

    @Override
    public void updateItem(int index, T item) {
        mDataList.remove(index);
        mDataList.add(index, item);
        notifyItemChanged(index+HEADER_OFFSET);
    }

    @Override
    public boolean addLoadingView() {
        if (getItemViewType(mDataList.size()) != VIEW_TYPE_LOADING) {
            add(null);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeLoadingView() {
        if (mDataList.size() > 1) {
            int loadingViewPosition = mDataList.size();
            if (getItemViewType(loadingViewPosition) == VIEW_TYPE_LOADING) {
                remove(loadingViewPosition-HEADER_OFFSET);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return getItemCount() == HEADER_OFFSET;
    }

    public void setVimeoConnection(VimeoConnection connection) {
        mConnection = connection;
    }

    /**
     * ViewHolders
     */
    public class HeaderViewHolder extends ListItemViewHolder<T> {

        public final TextView addCommentButton;

        public HeaderViewHolder (BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
            super (baseFragment, inflater.inflate (R.layout.item_add_comment_header, parent, false));

            addCommentButton = (TextView) itemView.findViewById(R.id.item_add_comment_header_button);
            addCommentButton.setOnClickListener(view -> {
                Intent intent = ReplyActivity.newIntent(baseFragment.getContext(),
                        VimeoTextUtil.generateVideoIdFromCommentUri(mConnection.getUri()),
                        null, ReplyFragment.RESPONSE_TYPE_COMMENT);

                mBaseFragment.startActivityForResult(intent, CommentsFragment.REQUEST_COMMENT);
            });
        }

        @Override
        public void bind(@Nullable T listItem) {
        }

        @Override
        public void recycle() {
        }
    }


}
