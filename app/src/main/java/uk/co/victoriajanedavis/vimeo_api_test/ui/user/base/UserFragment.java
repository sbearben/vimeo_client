package uk.co.victoriajanedavis.vimeo_api_test.ui.user.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import uk.co.victoriajanedavis.vimeo_api_test.GlideApp;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.EndlessRecyclerViewOnScrollListener;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.MarginDividerItemDecoration;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ExpandableTextView;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonRxBinding;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowToggleButton;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.UserVideoViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.util.DisplayMetricsUtil;
import uk.co.victoriajanedavis.vimeo_api_test.util.ExpandableTextViewUtil;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public abstract class UserFragment extends CollectionFragment<UserMvpView, VimeoVideo> implements UserMvpView {

    private static final String TAG = "UserFragment";

    @BindView(R.id.fragment_user_imageview) AppCompatImageView mImageView;
    @BindView(R.id.fragment_user_followers_textview) TextView mFollowersTextView;
    @BindView(R.id.fragment_user_following_textview) TextView mFollowingTextView;
    @BindView(R.id.fragment_user_likes_textview) TextView mLikesTextView;

    @BindView(R.id.fragment_user_name_textview) TextView mNameTextView;
    @BindView(R.id.fragment_user_location_textview) TextView mLocationTextView;
    @BindView(R.id.fragment_user_follow_button_layout) protected FollowToggleButton mFollowButton;

    @BindView(R.id.fragment_user_videocount_textview) TextView mVideoCountTextView;
    @BindView(R.id.layout_expandable_textview_textview) ExpandableTextView mBioTextView;
    @BindView(R.id.layout_expandable_textview_imageview) AppCompatImageView mBioExpandImageView;

    @BindView(R.id.fragment_user_appBarLayout) AppBarLayout mAppBarLayout;
    @BindView(R.id.fragment_user_recyclerview_container) ConstraintLayout mRecyclerContainerLayout;
    @BindView(R.id.layout_recyclerview_with_message_empty_recyclerview_layout) LinearLayout mEmptyRecyclerLayout;
    @BindView(R.id.fragment_user_signin_webview) protected WebView mWebView;

    protected VimeoUser mUser;
    private ExpandableTextViewUtil mExpandableTextViewUtil;

    protected FollowButtonRxBinding mFollowButtonRxBinding;
    protected Disposable mFollowButtonDisposable;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        if (mUser != null) {
            updateUserViews(mUser);
        }

        return v;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        mMessageButton.setOnClickListener(v -> onRefreshMessageButtonClick());
    }

    @Override
    public void onDestroyView() {
        if (!((AppCompatActivity) getContext()).isFinishing()) {
            Glide.with(this).clear(mImageView);
        }
        super.onDestroyView();
    }

    //@OnClick(R.id.message_tryagain_button)
    public void onRefreshMessageButtonClick() {
        super.onRefresh();
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.fragment_user;
    }

    @StringRes
    protected int getOnEmptyListErrorMessageStringRes() {
        return R.string.error_no_videos_to_display;
    }

    protected ListAdapter<VimeoVideo> createListAdapter() {
        return new ListAdapter<>(this, UserVideoViewHolder::new);
    }

    @Override
    protected String getQuery() {
        return null;
    }

    /***** MVP View methods implementation *****/

    @Override
    public void updateUserViews(VimeoUser user) {
        mUser = user;

        GlideApp.with(this)
                .load(user.getPictures().getSizesList().get(2).getLink())
                .circleCrop()
                .placeholder(R.drawable.user_image_placeholder)
                .fallback(R.drawable.user_image_placeholder)
                .transition(withCrossFade())
                .into(mImageView);

        mFollowersTextView.setText(VimeoApiServiceUtil.formatForUserMetric(user.getMetadata().getFollowersConnection().getTotal(), "follower", "followers"));
        mFollowingTextView.setText(VimeoApiServiceUtil.formatForUserMetric(user.getMetadata().getFollowingConnection().getTotal(), "following", "following"));
        mLikesTextView.setText(VimeoApiServiceUtil.formatForUserMetric(user.getMetadata().getLikesConnection().getTotal(), "like", "likes"));

        mNameTextView.setText(user.getName());
        VimeoApiServiceUtil.hideOrDisplayTextViewIfNullString(mLocationTextView, user.getLocation());

        if (mExpandableTextViewUtil != null) {
            mExpandableTextViewUtil.clear();
        }
        VimeoApiServiceUtil.hideOrDisplayTextViewIfNullString(mBioTextView, user.getBio());
        mExpandableTextViewUtil = new ExpandableTextViewUtil(mBioTextView, mBioExpandImageView);

        mVideoCountTextView.setText(getResources().getQuantityString(R.plurals.user_videos_plural,
                user.getMetadata().getVideosConnection().getTotal(), user.getMetadata().getVideosConnection().getTotal()));
    }

    @Override
    public void showProgress() {
        if (mListAdapter.isEmpty()) {
            mContentLoadingProgress.setVisibility(View.VISIBLE);
            mAppBarLayout.setVisibility(View.GONE);
            mWebView.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideProgress() {
        mContentLoadingProgress.setVisibility(View.GONE);
        mAppBarLayout.setVisibility(View.VISIBLE);
        mListAdapter.removeLoadingView();
    }

    @Override
    public abstract void showUnauthorizedError();

    @Override
    public abstract  void showError(String errorMessage);

    @Override
    public void showEmpty() {
        showEmptyRecyclerLayout(mListAdapter.isEmpty());
    }

    @Override
    public void showMessageLayout(boolean show) {
        super.showMessageLayout(show);
        mAppBarLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        mRecyclerContainerLayout.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public void showEmptyRecyclerLayout(boolean show) {
        Log.d (TAG, "show: " + show);
        mEmptyRecyclerLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
