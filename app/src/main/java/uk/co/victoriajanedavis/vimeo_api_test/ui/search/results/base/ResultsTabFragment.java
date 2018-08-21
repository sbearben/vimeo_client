package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.MarginDividerItemDecoration;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ParcelableListItem;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.ui.EndlessRecyclerViewOnScrollListener;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.ResultsFragment;
import uk.co.victoriajanedavis.vimeo_api_test.util.DisplayMetricsUtil;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

public abstract class ResultsTabFragment<T extends ParcelableListItem>
        extends CollectionFragment<ResultsTabMvpView<T>, T> implements ResultsTabMvpView<T>,
        SwipeRefreshLayout.OnRefreshListener, ResultsFragment.QuerySubmitCallback {

    private static final String TAG = "VideoTabFragment";

    @BindView(R.id.fragment_results_tab_header_layout) ConstraintLayout mHeaderLayout;
    @BindView(R.id.item_list_header_textview) TextView mHeaderTextView;
    @BindView(R.id.fragment_results_tab_swipe_to_refresh) SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean mIsViewShown = false;
    private String mHeaderText = "";
    private String mQuery;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_results_tab, container, false);
        mUnbinder = ButterKnife.bind(this, v);

        initViews(v);
        //mListAdapter.setListInteractionListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mIsViewShown) {
            updateQueryAndRequestData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);

        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        /* We're saving the HeaderText in an instance variable because due to ViewPager keeping Fragments in memory
         * but sometimes destroying their Views, if the Fragment's View is destroyed and we don't keep its text in an
         * instance variable then the textView shows blank when the View is recreated.
         */
        mHeaderTextView.setText(mHeaderText);
    }

    private void updateQueryAndRequestData() {
        if (getActivity() != null) {
            String newQuery = ((GetQueryCallback) getActivity()).getQuery();
            if (!newQuery.equals(mQuery)) {
                mQuery = newQuery;
                onRefresh();
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            mIsViewShown = true;
            updateQueryAndRequestData();
        } else {
            mIsViewShown = false;
        }
    }

    @Override
    public void submitQuery (String query) {
        if (!query.equals(mQuery)) {
            mQuery = query;
            onRefresh();
        }
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.fragment_results_tab;
    }

    @Override
    protected String getQuery() {
        return mQuery;
    }

    public abstract String getSingularHeaderMetric();

    public abstract String getPluralHeaderMetric();

    // Interface that the hosting Activity must implement
    public interface GetQueryCallback {
        String getQuery();
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showItems (VimeoCollection<T> itemCollection) {
        if (!mSwipeRefreshLayout.isActivated()) {
            mSwipeRefreshLayout.setEnabled(true);
        }

        //mListAdapter.setCollectionTotal(itemCollection.getTotal()-1);
        //mListAdapter.setSingularHeaderMetric(getSingularHeaderMetric());
        //mListAdapter.setPluralHeaderMetric(getPluralHeaderMetric());

        mHeaderText = VimeoApiServiceUtil.formatIntMetric(itemCollection.getTotal(), getSingularHeaderMetric(), getPluralHeaderMetric());
        mHeaderTextView.setText(mHeaderText);
        mListAdapter.addItems(itemCollection.getData());
    }

    @Override
    public void showProgress() {
        if (mListAdapter.isEmpty() && !mSwipeRefreshLayout.isRefreshing()) {
            mContentLoadingProgress.setVisibility(View.VISIBLE);
            mHeaderLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideProgress() {
        if (getView() != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mContentLoadingProgress.setVisibility(View.GONE);
            mHeaderLayout.setVisibility(View.VISIBLE);
            mListAdapter.removeLoadingView();
        }
    }

    @Override
    public void showMessageLayout(boolean show) {
        super.showMessageLayout(show);
        mHeaderLayout.setVisibility(show ? View.GONE : View.VISIBLE);
    }

}
