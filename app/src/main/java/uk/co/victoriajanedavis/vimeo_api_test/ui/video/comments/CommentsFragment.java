package uk.co.victoriajanedavis.vimeo_api_test.ui.video.comments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;

import javax.inject.Inject;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoComment;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.reply.ReplyFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.base.VideoTabFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.base.VideoTabPresenter;

public class CommentsFragment extends VideoTabFragment<VimeoComment> {

    private static final String TAG = "CommentsFragment";

    private static final String ARG_VIMEO_CONNECTION = "vimeo_connection";
    public static final int REQUEST_COMMENT = 1;
    public static final int REQUEST_REPLY = 2;

    @Inject CommentsPresenter mPresenter;


    public static CommentsFragment newInstance (VimeoConnection connection) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_VIMEO_CONNECTION, connection);

        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This needs to be called here
        mConnection = (VimeoConnection) getArguments().getParcelable(ARG_VIMEO_CONNECTION);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        getPresenter().attachView(this);

        if (savedInstanceState != null) {
            mConnection = savedInstanceState.getParcelable(SAVED_VIMEO_CONNECTION);
        }
        setVimeoConnection();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_COMMENT) {
            VimeoComment comment = (VimeoComment) data.getParcelableExtra(ReplyFragment.EXTRA_VIMEO_RESPONSE);
            mListAdapter.add(0, comment);
        }
        else if (requestCode == REQUEST_REPLY) {
            VimeoComment reply = (VimeoComment) data.getParcelableExtra(ReplyFragment.EXTRA_VIMEO_RESPONSE);
            // Do something else (expand replies on the comment and add this one in)
        }
    }

    @Override
    public void onRefresh() {
        setVimeoConnection();
        super.onRefresh();
    }

    public void setVimeoConnection() {
        if (mListAdapter instanceof CommentHeaderListAdapter) {
            ((CommentHeaderListAdapter<VimeoComment>) mListAdapter).setVimeoConnection(mConnection);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().detachView();
    }

    @Override
    @StringRes
    public int getOnEmptyListErrorMessageStringRes() {
        return R.string.error_no_comments_to_display;
    }

    @Override
    public VideoTabPresenter<VimeoComment> getPresenter() {
        return mPresenter;
    }

    @Override
    public ListAdapter<VimeoComment> createListAdapter() {
        if (isUserLoggedIn()) {
            return new CommentHeaderListAdapter<>(this, mConnection, CommentViewHolder::new);
        }
        return new ListAdapter<>(this, CommentViewHolder::new);
    }

    @Override
    public VimeoConnection getConnectionFromVideo (VimeoVideo video) {
        return video.getMetadata().getCommentsConnection();
    }

    @Override
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.fragment_video_tab;
    }
}
