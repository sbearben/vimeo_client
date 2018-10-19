package uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.SearchEvent;
import uk.co.victoriajanedavis.vimeo_api_test.util.eventbus.RxBehaviourEventBus;

public class SuggestionsFragment extends BaseFragment implements SuggestionsMvpView {

    private static final String TAG = "SuggestionsFragment";

    @Inject SuggestionsPresenter mSuggestionsPresenter;
    @Inject
    RxBehaviourEventBus mSearchEventBus;

    private Disposable mDisposable;
    private SuggestionsAdapter mSuggestionsAdapter;

    @BindView(R.id.fragment_suggestions_recyclerview) RecyclerView mSuggestionsRecycler;

    @BindView(R.id.content_progress) ProgressBar mContentLoadingProgress;
    @BindView(R.id.message_layout) View mMessageLayout;
    @BindView(R.id.message_imageview) ImageView mMessageImage;
    @BindView(R.id.message_textview) TextView mMessageText;
    @BindView(R.id.message_tryagain_button) Button mMessageButton;

    private Unbinder mUnbinder;


    public static SuggestionsFragment newInstance() {
        return new SuggestionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        mSuggestionsPresenter.attachView(this);

        mSuggestionsAdapter = new SuggestionsAdapter();

        mDisposable = mSearchEventBus.filteredObservable(SearchEvent.class).skip(1).subscribe(event -> {
            mSuggestionsAdapter.addSuggestion(event.getQuery());
            mSuggestionsPresenter.onNewSearchQuery(event.getQuery());
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_suggestions, container, false);
        mUnbinder = ButterKnife.bind(this, v);

        initViews(v);
        if (mSuggestionsAdapter.isEmpty()) {
            mSuggestionsPresenter.onInitialListRequested();
        }

        return v;
    }

    private void initViews(View view) {
        mSuggestionsRecycler.setMotionEventSplittingEnabled(false);
        mSuggestionsRecycler.setItemAnimator(new DefaultItemAnimator());
        mSuggestionsRecycler.setAdapter(mSuggestionsAdapter);

        mSuggestionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mSuggestionsRecycler.addItemDecoration (new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSuggestionsRecycler.setAdapter(null);

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        mDisposable.dispose();
        mSuggestionsPresenter.detachView();
        super.onDestroy();

    }

    @OnClick(R.id.message_tryagain_button)
    public void onRefresh() {
        mSuggestionsAdapter.removeAll();
        mSuggestionsPresenter.onInitialListRequested();
    }


    /***** MVP View methods implementation *****/

    @Override
    public void showSuggestions(List<String> suggestionsList) {
        mSuggestionsAdapter.addAllSuggestions(suggestionsList);
        //mSuggestionsAdapter.refreshFilter();
    }

    @Override
    public void showProgress() {
        if (mSuggestionsAdapter.isEmpty()) {
            mContentLoadingProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        mContentLoadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void showUnauthorizedError() {
    }

    @Override
    public void showError(String errorMessage) {
        mMessageImage.setImageResource(R.drawable.ic_error_outline_black_48dp);
        mMessageText.setText(getString(R.string.error_generic_sharedpref_error, errorMessage));
        mMessageButton.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showEmpty() {
    }

    @Override
    public void showMessageLayout(boolean show) {
        mMessageLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
