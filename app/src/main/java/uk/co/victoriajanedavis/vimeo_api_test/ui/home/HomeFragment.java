package uk.co.victoriajanedavis.vimeo_api_test.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.paging.State;
import uk.co.victoriajanedavis.vimeo_api_test.paging.base.VimeoCollectionAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.paging.videos.AdjustableImageViewAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.MarginDividerItemDecoration;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.util.DisplayMetricsUtil;
import uk.co.victoriajanedavis.vimeo_api_test.util.LayoutManagerUtil;

public class HomeFragment extends BaseFragment implements HomeMvpView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "HomeFragment";

    @Inject HomePresenter mPresenter;

    VimeoCollectionAdapter<VimeoVideo> mPagerAdapter;

    @BindView(R.id.fragment_home_swipe_to_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerview) RecyclerView mRecycler;

    @BindView(R.id.content_progress) ProgressBar mContentLoadingProgress;
    @BindView(R.id.message_layout) View mMessageLayout;
    @BindView(R.id.message_imageview) ImageView mMessageImage;
    @BindView(R.id.message_textview) TextView mMessageText;
    @BindView(R.id.message_tryagain_button) Button mMessageButton;

    private Unbinder mUnbinder;

    private DisplayMetricsUtil.Dimensions mScreenDimensions;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mScreenDimensions = DisplayMetricsUtil.getScreenDimensions();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        mPresenter.attachView(this);

        initViews();
        initState();

        return v;
    }

    private void initViews() {
        boolean isTabletLayout = DisplayMetricsUtil.isTabletLayout();

        mPagerAdapter = new AdjustableImageViewAdapter(this, VideoFeedViewHolder::new,
                DisplayMetricsUtil.getDimensionsRequiredToFillWidthMaintainingAspectRatio(mScreenDimensions,
                isTabletLayout));

        mRecycler.setBackgroundColor(getResources().getColor(R.color.mediumLightGray));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setLayoutManager(LayoutManagerUtil.setUpLayoutManager(getContext(), isTabletLayout));
        mRecycler.addItemDecoration (new MarginDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, R.dimen.recycler_view_divider_margin));
        mRecycler.setAdapter(mPagerAdapter);

        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mPresenter.getVideoListLiveData().observe(this, mPagerAdapter::submitList);
    }


    private void initState() {
        mMessageButton.setOnClickListener((view) -> mPresenter.retry());

        mPresenter.getStateLiveData().observe(this, state -> {
            if (state == State.LOADING_INITIAL) {
                showMessageLayout(false);
                showProgress();
            }
            else if (state == State.SUCCESS) hideProgress();
            else if (state == State.NO_DATA) showEmpty();
            else if (state == State.ERROR) {
                hideProgress();
                showError("An error occured");
            }
            else if (state == State.UNAUTHORIZED) {
                hideProgress();
                showUnauthorizedError();
            }

        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        mSwipeRefreshLayout.setOnRefreshListener(null);
        mRecycler.setAdapter(null);

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (!((Activity) getContext()).isChangingConfigurations()) {
            mPresenter.clearDisposables();
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.invalidateDataSource();
    }


    /***** MVP View methods implementation *****/

    @Override
    public void showItems (VimeoCollection<VimeoVideo> itemCollection) {
    }

    @Override
    public void showProgress() {
        if (mPagerAdapter.isEmpty() && !mSwipeRefreshLayout.isRefreshing()) {
            mContentLoadingProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
        mContentLoadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void showUnauthorizedError() {
        mMessageImage.setImageResource(R.drawable.ic_error_outline_black_48dp);
        mMessageText.setText(getString(R.string.error_generic_server_error, "Unauthorized"));
        mMessageButton.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showError(String errorMessage) {
        mMessageImage.setImageResource(R.drawable.ic_error_outline_black_48dp);
        mMessageText.setText(getString(R.string.error_generic_server_error, errorMessage));
        mMessageButton.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showEmpty() {
        mMessageImage.setImageResource(R.drawable.ic_clear_black_48dp);
        mMessageText.setText(getString(R.string.error_no_videos_to_display));
        mMessageButton.setText(getString(R.string.action_check_again));
        showMessageLayout(true);
    }

    @Override
    public void showMessageLayout(boolean show) {
        mMessageLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
