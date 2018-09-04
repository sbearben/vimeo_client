package uk.co.victoriajanedavis.vimeo_api_test.ui.video.comments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoComment;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.reply.ReplyActivity;
import uk.co.victoriajanedavis.vimeo_api_test.ui.reply.ReplyFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.base.VideoTabFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.base.VideoTabPresenter;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

public class CommentsFragment extends VideoTabFragment<VimeoComment> {

    private static final String TAG = "CommentsFragment";

    private static final String ARG_VIMEO_CONNECTION = "vimeo_connection";
    static final int REQUEST_COMMENT = 1;
    public static final int REQUEST_REPLY = 2;

    @Inject CommentsPresenter mPresenter;

    //@BindView(R.id.fragment_comments_appBarLayout) AppBarLayout mAppBarLayout;
    //@BindView(R.id.fragment_comments_comment_button) Button mCommentButton;


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
        mConnection = (VimeoConnection) getArguments().getParcelable(ARG_VIMEO_CONNECTION);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        getPresenter().attachView(this);
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        //mAppBarLayout.setVisibility(isUserLoggedIn() ? View.VISIBLE : View.GONE);
    }

    /*
    @OnClick(R.id.fragment_comments_comment_button)
    public void onCommentButtonClicked(View v) {
        Log.d (TAG, "connection Uri: " + mConnection.getUri());
        Intent intent = ReplyActivity.newIntent(getContext(),
                VimeoApiServiceUtil.generateVideoIdFromCommentUri(mConnection.getUri()),
                null, ReplyFragment.RESPONSE_TYPE_COMMENT);

        startActivityForResult(intent, REQUEST_COMMENT);
    }
    */

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
    public void onDetach() {
        super.onDetach();
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
