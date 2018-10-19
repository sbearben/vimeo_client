package uk.co.victoriajanedavis.vimeo_api_test.ui.reply;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoComment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.util.ViewUtil;

import static android.app.Activity.RESULT_OK;

public class ReplyFragment extends BaseFragment implements ReplyMvpView {

    private static final String TAG = "ReplyFragment";

    private static final String ARG_VIDEO_ID = "video_id";
    private static final String ARG_VIMEO_COMMENT = "vimeo_comment"; // This is called a comment since if anything is passed in, it is a comment we are replying to
    private static final String ARG_RESPONSE_TYPE = "response_type";

    public static final String EXTRA_VIMEO_RESPONSE = "uk.co.victoriajanedavis.vimeo_api_test.ReplyFragment.vimeo_response"; // Calling this a RESPONSE since it could be a new comment or reply

    public static final String FRAGMENT_REPLY_TAG = "fragment_reply";

    public static final String RESPONSE_TYPE_REPLY = "reply";
    public static final String RESPONSE_TYPE_COMMENT = "comment";

    @StringDef({RESPONSE_TYPE_REPLY, RESPONSE_TYPE_COMMENT})
    @Retention(RetentionPolicy.SOURCE)
    @interface ResponseType {
    }

    @Inject ReplyPresenter mPresenter;

    @BindView(R.id.fragment_reply_root_layout) CoordinatorLayout mRootLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.fragment_reply_post_textview) TextView mPostTextView;

    @BindView(R.id.fragment_reply_comment_layout) ConstraintLayout mCommentLayout;
    @BindView(R.id.item_comment_imageview) AppCompatImageView mCommentImageView;
    @BindView(R.id.item_comment_name_textview) TextView mCommentNameTextView;
    @BindView(R.id.item_comment_age_textview) TextView mCommentAgeTextView;
    @BindView(R.id.item_comment_comment_textview) TextView mCommentTextView;
    @BindView(R.id.item_comment_reply_textview) TextView mCommentReplyButtonTextView;

    @BindView(R.id.fragment_reply_divider) View mDivider;
    @BindView(R.id.fragment_reply_edittext) EditText mResponseEditText;

    @BindView(R.id.fragment_reply_progress_layout) FrameLayout mProgressLayout;
    @BindView(R.id.content_progress) ProgressBar mProgress;

    private Disposable mDisposable;

    private long mVideoId;
    private VimeoComment mComment;
    private  @ResponseType String mResponseType;


    public static ReplyFragment newInstance (long video_id, VimeoComment comment, @ResponseType String response_type) {
        Bundle args = new Bundle();
        args.putLong(ARG_VIDEO_ID, video_id);
        args.putParcelable(ARG_VIMEO_COMMENT, comment);
        args.putString(ARG_RESPONSE_TYPE, response_type);

        ReplyFragment fragment = new ReplyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);

        mVideoId = (long) getArguments().getLong(ARG_VIDEO_ID);
        mComment = (VimeoComment) getArguments().getParcelable(ARG_VIMEO_COMMENT);
        mResponseType = (String) getArguments().getString(ARG_RESPONSE_TYPE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mPresenter.attachView(this);
        View v = inflater.inflate(R.layout.fragment_reply, container, false);
        ButterKnife.bind(this, v);

        initViews(v);

        return v;
    }

    private void initViews(View view) {
        mCommentReplyButtonTextView.setVisibility(View.GONE);
        if (mResponseType.equals(RESPONSE_TYPE_COMMENT)) {
            mCommentLayout.setVisibility(View.GONE);
            mDivider.setVisibility(View.GONE);
        }

        mResponseEditText.setHint("Type " + mResponseType + "...");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Setup the Toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Compose " + mResponseType);
        }

        // RxBinding for the EditText
        mDisposable =  RxTextView.textChanges(mResponseEditText).subscribe(text -> {
            if(TextUtils.isEmpty(text)) {
                mPostTextView.setEnabled(false);
            }
            else {
                mPostTextView.setEnabled(true);
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        mDisposable.dispose();
        super.onDestroyView();

    }

    private void showErrorSnackbar(String errorMessage) {
        final Snackbar snackbar = Snackbar.make(mRootLayout, errorMessage, Snackbar.LENGTH_LONG);

        snackbar.setAction("RETRY", view -> {
            snackbar.dismiss();
            if (!TextUtils.isEmpty(mResponseEditText.getText().toString())) {
                onPostButtonClick(null);
            }
        });

        snackbar.show();
    }

    @OnClick(R.id.fragment_reply_post_textview)
    public void onPostButtonClick(View v) {
        if (mResponseType.equals(RESPONSE_TYPE_COMMENT)) {
            mPresenter.postCommentOnVideo(mVideoId, mResponseEditText.getText().toString());
        }
        else {
            mPresenter.postReplyToCommentOnVideo(mVideoId, mComment.getId(), mResponseEditText.getText().toString());
        }
    }


    /***** MVP View methods implementation *****/

    @Override
    public void showProgress() {
        mProgressLayout.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.VISIBLE);

        if (getActivity() != null) {
            ViewUtil.disableTouchInput(getActivity());
        }
    }

    @Override
    public void hideProgress() {
        mProgressLayout.setVisibility(View.GONE);
        mProgress.setVisibility(View.GONE);

        if (getActivity() != null) {
            ViewUtil.enableTouchInput(getActivity());
        }
    }

    @Override
    public void showUnauthorizedError() {
    }

    @Override
    public void showError(String errorMessage) {
        if (getActivity() != null) {
            ViewUtil.hideKeyboard(getActivity());
        }

        showErrorSnackbar(errorMessage);
    }

    @Override
    public void showEmpty() {
    }

    @Override
    public void showMessageLayout(boolean show) {
    }

    @Override
    public void setResponseResultAndExit(VimeoComment response) {
        Intent data = new Intent();
        data.putExtra (EXTRA_VIMEO_RESPONSE, response);

        if (getActivity() != null) {
            getActivity().setResult(RESULT_OK, data);
            getActivity().onBackPressed();
        }
    }
}
