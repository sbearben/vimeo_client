package uk.co.victoriajanedavis.vimeo_api_test.ui.channel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.GlideApp;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoChannel;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
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

public class ChannelFragment extends CollectionFragment<ChannelMvpView, VimeoVideo> implements ChannelMvpView {

    private static final String TAG = "ChannelFragment";
    private static final String ARG_VIMEO_CHANNEL = "vimeo_channel";

    public static final String FRAGMENT_CHANNEL_TAG = "fragment_channel";

    @Inject ChannelPresenter mPresenter;

    @BindView(R.id.fragment_channel_imageview) AppCompatImageView mImageView;
    @BindView(R.id.fragment_channel_name_textview) TextView mNameTextView;
    @BindView(R.id.fragment_channel_videosfollowers_textview) TextView mVideosFollowersTextView;
    @BindView(R.id.fragment_channel_follow_button_layout) FollowToggleButton mFollowButton;

    //@BindView(R.id.fragment_channel_description_layout) ConstraintLayout mDescriptionLayout;
    @BindView(R.id.layout_expandable_textview_textview) ExpandableTextView mDescriptionTextView;
    @BindView(R.id.layout_expandable_textview_imageview) AppCompatImageView mDescriptionExpandImageView;

    @BindView(R.id.item_user_imageview) AppCompatImageView mUserImageView;
    @BindView(R.id.item_user_name_textview) TextView mUserNameTextView;
    @BindView(R.id.item_user_videosfollowers_textview) TextView mChannelAgeTextView;
    @BindView(R.id.fragment_channel_videocount_textview) TextView mVideoCountTextView;

    @BindView(R.id.fragment_channel_appBarLayout) AppBarLayout mAppBarLayout;
    @BindView(R.id.fragment_channel_recyclerview_container) ConstraintLayout mRecyclerContainerLayout;
    @BindView(R.id.layout_recyclerview_with_message_empty_recyclerview_layout) LinearLayout mEmptyRecyclerLayout;

    protected VimeoChannel mChannel;
    private ExpandableTextViewUtil mExpandableTextViewUtil;

    private FollowButtonRxBinding mFollowButtonRxBinding;
    private Disposable mFollowButtonDisposable;


    public static ChannelFragment newInstance (VimeoChannel channel) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_VIMEO_CHANNEL, channel);

        ChannelFragment fragment = new ChannelFragment();
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
        mPresenter.attachView(this);

        mChannel = (VimeoChannel) getArguments().getParcelable(ARG_VIMEO_CHANNEL);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        updateChannelViews(mChannel);

        return v;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        mFollowButtonRxBinding = new FollowButtonRxBinding(mChannel, mFollowButton, new FollowButtonRxBinding.FollowButtonClickListener() {
            @Override
            public Single<Response<Void>> onFollowButtonClick(long channel_id) {
                return mPresenter.getSubscribeToChannelSingle(channel_id);
            }

            @Override
            public Single<Response<Void>> onUnfollowButtonClick(long channel_id) {
                return mPresenter.getUnsubscribeFromChannelSingle(channel_id);
            }
        });
        mFollowButtonDisposable = mFollowButtonRxBinding.setupFollowButtonRxBindingStream();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFollowButtonDisposable.dispose();
        mFollowButtonRxBinding.setFollowButtonClickListener(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.fragment_channel;
    }

    protected ListItemViewHolder<VimeoVideo> generateViewHolder (Context context, BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        return new UserVideoViewHolder(context, baseFragment, inflater, parent);
    }

    @StringRes
    protected int getOnEmptyListErrorMessageStringRes() {
        return R.string.error_no_videos_to_display;
    }

    protected ChannelPresenter getPresenter() {
        return mPresenter;
    }

    protected ListAdapter<VimeoVideo> createListAdapter() {
        return new ListAdapter<>(getContext(), this, UserVideoViewHolder::new);
    }

    protected String getCollectionUri() {
        return mChannel.getMetadata().getVideosConnection().getUri();
    }

    @Override
    protected String getQuery() {
        return null;
    }

    /***** MVP View methods implementation *****/

    public void updateChannelViews(VimeoChannel channel) {
        mChannel = channel;

        GlideApp.with(this)
                .load(channel.getHeader().getSizesList().get(0).getLink())
                .placeholder(R.drawable.video_image_placeholder)
                .fallback(R.drawable.video_image_placeholder)
                .fitCenter()
                .transition(withCrossFade())
                .into(mImageView);

        mNameTextView.setText(channel.getName());
        mVideosFollowersTextView.setText(VimeoApiServiceUtil.formatVideoCountAndFollowers(
                channel.getMetadata().getVideosConnection().getTotal(),
                channel.getMetadata().getUsersConnection().getTotal()));


        if (mExpandableTextViewUtil != null) {
            mExpandableTextViewUtil.clear();
        }
        Log.d (TAG, "getDescription(): " + channel.getDescription());
        VimeoApiServiceUtil.hideOrDisplayTextViewIfNullString(mDescriptionTextView, channel.getDescription());
        mExpandableTextViewUtil = new ExpandableTextViewUtil(mDescriptionTextView, mDescriptionExpandImageView);


        VimeoUser user = channel.getUser();

        GlideApp.with(this)
                .load(user.getPictures().getSizesList().get(2).getLink())
                .circleCrop()
                .placeholder(R.drawable.user_image_placeholder)
                .fallback(R.drawable.user_image_placeholder)
                .transition(withCrossFade())
                .into(mUserImageView);

        String createdBy = "Created by: " + user.getName();
        mUserNameTextView.setText(createdBy);
        mChannelAgeTextView.setText(VimeoApiServiceUtil.dateCreatedToTimePassed(channel.getCreatedTime()));

        mVideoCountTextView.setText(getResources().getQuantityString(R.plurals.user_videos_plural,
                channel.getMetadata().getVideosConnection().getTotal(), channel.getMetadata().getVideosConnection().getTotal()));
    }

    @Override
    public void showProgress() {
        if (mListAdapter.isEmpty()) {
            mContentLoadingProgress.setVisibility(View.VISIBLE);
            mAppBarLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideProgress() {
        mContentLoadingProgress.setVisibility(View.GONE);
        mAppBarLayout.setVisibility(View.VISIBLE);
        mListAdapter.removeLoadingView();
    }

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
        mEmptyRecyclerLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
