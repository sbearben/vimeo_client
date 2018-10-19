package uk.co.victoriajanedavis.vimeo_api_test.ui.explore;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCategory;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.UserVideoViewHolder;

public class ExploreFragment extends CollectionFragment<ExploreMvpView, VimeoVideo> implements ExploreMvpView {

    private static final String TAG = "ExploreFragment";
    private static final String SAVED_CATEGORY_LIST = "fragment_explore_saved_category_list";

    @Inject ExplorePresenter mPresenter;
    private ListAdapter<VimeoCategory> mCategoryListAdapter;

    @BindView(R.id.fragment_explore_appBarLayout) AppBarLayout mAppBarLayout;
    @BindView(R.id.fragment_explore_categories_recyclerview) RecyclerView mCategoriesRecycler;

    @BindView(R.id.fragment_explore_videos_recyclerview_container) ConstraintLayout mVideosRecyclerContainerLayout;
    @BindView(R.id.layout_recyclerview_with_message_empty_recyclerview_layout) LinearLayout mEmptyVideosRecyclerLayout;


    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        mPresenter.attachView(this);

        mCategoryListAdapter = new ListAdapter<>(this, CategoryViewHolder::new);

        if (savedInstanceState != null) {
            mCategoryListAdapter.addItems(savedInstanceState.getParcelableArrayList (SAVED_CATEGORY_LIST));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mPresenter.attachView(this);

        View v = inflater.inflate(R.layout.fragment_explore, container, false);
        mUnbinder = ButterKnife.bind(this, v);

        if (mListAdapter.isEmpty() || mCategoryListAdapter.isEmpty()) {
            onRefresh();
        }

        initViews(v);


        return v;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        mCategoriesRecycler.setHasFixedSize(true);
        mCategoriesRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.HORIZONTAL, false));
        mCategoriesRecycler.setAdapter(mCategoryListAdapter);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        mCategoriesRecycler.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_CATEGORY_LIST, new ArrayList<>(mCategoryListAdapter.getItems()));
    }

    @OnClick(R.id.message_tryagain_button)
    public void onRefresh() {
        mCategoryListAdapter.removeAll();
        super.onRefresh();
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.fragment_explore;
    }

    @StringRes
    protected int getOnEmptyListErrorMessageStringRes() {
        return R.string.error_no_videos_to_display;
    }

    protected ExplorePresenter getPresenter() {
        return mPresenter;
    }

    protected ListAdapter<VimeoVideo> createListAdapter() {
        return new ListAdapter<>(this, UserVideoViewHolder::new);
    }

    protected String getCollectionUri() {
        return null;
    }

    @Override
    protected String getQuery() {
        return null;
    }


    /***** MVP View methods implementation *****/

    @Override
    public void showCategories (List<VimeoCategory> categoryList) {
        mCategoryListAdapter.addItems(categoryList);
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
        showEmptyVideoRecyclerLayout(mListAdapter.isEmpty());
    }

    @Override
    public void showMessageLayout(boolean show) {
        super.showMessageLayout(show);
        mAppBarLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        mVideosRecyclerContainerLayout.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public void showEmptyVideoRecyclerLayout(boolean show) {
        if (getView() != null) {
            mEmptyVideosRecyclerLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
