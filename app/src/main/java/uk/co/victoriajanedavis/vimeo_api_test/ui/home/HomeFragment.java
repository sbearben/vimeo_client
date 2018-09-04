package uk.co.victoriajanedavis.vimeo_api_test.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionPresenter;

public class HomeFragment extends CollectionFragment<HomeMvpView, VimeoVideo> implements HomeMvpView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "HomeFragment";

    @Inject HomePresenter mPresenter;

    @BindView(R.id.fragment_home_swipe_to_refresh) SwipeRefreshLayout mSwipeRefreshLayout;


    public static HomeFragment newInstance() {
        return new HomeFragment();
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
        return new ListAdapter<>( this, VideoFeedViewHolder::new);
    }

    protected String getCollectionUri() {
        return "channels/staffpicks/videos";
    }

    @Override
    protected String getQuery() {
        return null;
    }
}
