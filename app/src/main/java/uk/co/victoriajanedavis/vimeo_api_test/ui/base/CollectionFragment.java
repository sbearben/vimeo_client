package uk.co.victoriajanedavis.vimeo_api_test.ui.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
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
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.EndlessRecyclerViewOnScrollListener;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.MarginDividerItemDecoration;
import uk.co.victoriajanedavis.vimeo_api_test.util.DisplayMetricsUtil;
import uk.co.victoriajanedavis.vimeo_api_test.util.LayoutManagerUtil;

public abstract class CollectionFragment<A extends CollectionMvpView<T>, T extends Parcelable>
        extends BaseFragment implements CollectionMvpView<T> {

    private static final String TAG = "CollectionFragment";
    private static final String SAVED_COLLECTION_LIST = "fragment_collection_saved_video_list";

    protected ListAdapter<T> mListAdapter;

    @BindView(R.id.recyclerview) protected RecyclerView mRecycler;

    @BindView(R.id.content_progress) protected ProgressBar mContentLoadingProgress;
    @BindView(R.id.message_layout) protected View mMessageLayout;
    @BindView(R.id.message_imageview) protected ImageView mMessageImage;
    @BindView(R.id.message_textview) protected TextView mMessageText;
    @BindView(R.id.message_tryagain_button) protected Button mMessageButton;

    protected Unbinder mUnbinder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = createListAdapter();

        /*
        if (savedInstanceState != null) {
            mListAdapter.addItems(savedInstanceState.getParcelableArrayList (SAVED_COLLECTION_LIST));
        }
        */
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutResId(), container, false);
        mUnbinder = ButterKnife.bind(this, v);

        initViews(v);

        if (mListAdapter.isEmpty()) {
            getPresenter().onInitialListRequested(getCollectionUri(), getQuery(), 10);
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void initViews(View view) {
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setAdapter(mListAdapter);

        boolean isTabletLayout = DisplayMetricsUtil.isTabletLayout();
        mRecycler.setLayoutManager(LayoutManagerUtil.setUpLayoutManager(getContext(), mListAdapter, isTabletLayout));
        mRecycler.addItemDecoration (new MarginDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, R.dimen.recycler_view_divider_margin));
        mRecycler.addOnScrollListener(setupScrollListener(isTabletLayout, mRecycler.getLayoutManager()));
    }

    private EndlessRecyclerViewOnScrollListener setupScrollListener(boolean isTabletLayout, RecyclerView.LayoutManager layoutManager) {
        return new EndlessRecyclerViewOnScrollListener(isTabletLayout ?
                (GridLayoutManager) layoutManager : (LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int totalItemsCount) {
                if (!TextUtils.isEmpty(getPresenter().getNextCollectionUri()) && !mListAdapter.isEmpty()) {
                    if (mListAdapter.addLoadingView()) {
                        getPresenter().onListEndReached();
                    }
                }

            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecycler.setAdapter(null);

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(SAVED_COLLECTION_LIST, new ArrayList<>(mListAdapter.getItems()));
    }


    @OnClick(R.id.message_tryagain_button)
    public void onRefresh() {
        mListAdapter.removeAll();
        getPresenter().onInitialListRequested(getCollectionUri(), getQuery(), 10);
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    @StringRes
    protected abstract int getOnEmptyListErrorMessageStringRes();

    protected abstract CollectionPresenter<A, T> getPresenter();

    protected abstract ListAdapter<T> createListAdapter();

    protected abstract String getCollectionUri();

    protected abstract String getQuery();


    /***** MVP View methods implementation *****/

    @Override
    public void showItems (VimeoCollection<T> itemCollection) {
        mListAdapter.addItems(itemCollection.getData());
    }

    @Override
    public void showProgress() {
        if (mListAdapter.isEmpty()) {
            mContentLoadingProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        mContentLoadingProgress.setVisibility(View.GONE);
        mListAdapter.removeLoadingView();
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
        mMessageText.setText(getString(getOnEmptyListErrorMessageStringRes()));
        mMessageButton.setText(getString(R.string.action_check_again));
        showMessageLayout(true);
    }

    @Override
    public void showMessageLayout(boolean show) {
        mMessageLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
