package uk.co.victoriajanedavis.vimeo_api_test.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionPresenter;
import uk.co.victoriajanedavis.vimeo_api_test.util.DisplayMetricsUtil;

public class HomeFragment extends CollectionFragment<HomeMvpView, VimeoVideo> implements HomeMvpView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "HomeFragment";

    @Inject HomePresenter mPresenter;

    @BindView(R.id.fragment_home_swipe_to_refresh) SwipeRefreshLayout mSwipeRefreshLayout;

    private int mScreenWidth;
    private int mScreenHeight;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This code is used to get the screen dimensions of the user's device
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((AppCompatActivity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        mRecycler.setBackgroundColor(getResources().getColor(R.color.mediumLightGray));

        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @StringRes
    protected int getOnEmptyListErrorMessageStringRes() {
        return R.string.error_no_videos_to_display;
    }

    protected HomePresenter getPresenter() {
        return mPresenter;
    }

    protected ListAdapter<VimeoVideo> createListAdapter() {
        boolean isTabletLayout = DisplayMetricsUtil.isScreenW(CollectionFragment.SCREEN_TABLET_DP_WIDTH);
        int imageViewWidth, imageViewHeight;

        if (isTabletLayout) imageViewWidth = mScreenWidth/CollectionFragment.TAB_LAYOUT_SPAN_SIZE;
        else imageViewWidth = mScreenWidth;

        imageViewHeight = (int) (imageViewWidth/1.778);

        return new AdjustableImageViewListAdapter(this, VideoFeedViewHolder::new, imageViewWidth, imageViewHeight);
    }

    protected String getCollectionUri() {
        return "channels/staffpicks/videos";
    }

    @Override
    protected String getQuery() {
        return null;
    }


    /***** MVP View methods implementation *****/

    @Override
    public void showItems (VimeoCollection<VimeoVideo> itemCollection) {
        if (!mSwipeRefreshLayout.isActivated()) {
            mSwipeRefreshLayout.setEnabled(true);
        }

        super.showItems(itemCollection);
    }

    @Override
    public void showProgress() {
        if (mListAdapter.isEmpty() && !mSwipeRefreshLayout.isRefreshing()) {
            mContentLoadingProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (getView() != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mContentLoadingProgress.setVisibility(View.GONE);
            mListAdapter.removeLoadingView();
        }
    }
}
