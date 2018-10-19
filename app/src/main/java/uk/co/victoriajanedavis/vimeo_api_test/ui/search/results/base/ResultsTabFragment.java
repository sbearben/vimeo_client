package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.SearchEvent;
import uk.co.victoriajanedavis.vimeo_api_test.util.eventbus.RxBehaviourEventBus;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoTextUtil;

public abstract class ResultsTabFragment<T extends Parcelable>
        extends CollectionFragment<ResultsTabMvpView<T>, T> implements ResultsTabMvpView<T>,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ResultsTabFragment";

    @Inject
    RxBehaviourEventBus mSearchEventBus;
    private Disposable mDisposable;

    @BindView(R.id.fragment_results_tab_header_layout) ConstraintLayout mHeaderLayout;
    @BindView(R.id.item_list_header_textview) TextView mHeaderTextView;
    @BindView(R.id.fragment_results_tab_swipe_to_refresh) SwipeRefreshLayout mSwipeRefreshLayout;

    private String mHeaderText = "";
    private String mQuery;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_results_tab, container, false);
        mUnbinder = ButterKnife.bind(this, v);

        mDisposable = mSearchEventBus.filteredObservable(SearchEvent.class).subscribe(event -> submitQuery(event.getQuery()));

        initViews(v);

        return v;
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

    @Override
    public void onDestroyView() {
        mSwipeRefreshLayout.setOnRefreshListener(null);
        mDisposable.dispose();
        super.onDestroyView();
    }

    private void submitQuery (String query) {
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

    /***** MVP View methods implementation *****/

    @Override
    public void showItems (VimeoCollection<T> itemCollection) {
        if (!mSwipeRefreshLayout.isActivated()) {
            mSwipeRefreshLayout.setEnabled(true);
        }

        mHeaderText = VimeoTextUtil.formatIntMetric(itemCollection.getTotal(), getSingularHeaderMetric(), getPluralHeaderMetric());
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
