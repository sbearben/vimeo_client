package uk.co.victoriajanedavis.vimeo_api_test.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import uk.co.victoriajanedavis.vimeo_api_test.VimeoApplication;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.injection.component.ActivityComponent;
import uk.co.victoriajanedavis.vimeo_api_test.injection.component.DaggerActivityComponent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseActivity;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.ResultsFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions.SuggestionsFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions.SuggestionsViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.util.eventbus.RxBehaviourEventBus;
import uk.co.victoriajanedavis.vimeo_api_test.util.ViewUtil;


public class SearchActivity extends BaseActivity implements SuggestionsViewHolder.OnClickListener {

    private static final String TAG = "SearchActivity";

    // For saving our instance state so that we open the correct fragment after rotation
    private static final String SAVED_SEARCH_QUERY = "current_search_query";

    private static final String FRAGMENT_SEARCH_SUGGESTIONS_TAG = "fragment_search_suggestions";
    private static final String FRAGMENT_SEARCH_RESULTS_TAG = "fragment_search_results";

    @Inject
    RxBehaviourEventBus mSearchEventBus;

    private SearchView mSearchView;
    private MenuItem mSearchItem;

    private String mSearchQuery = "";


    public static Intent newIntent (Context packageContext) {
        return new Intent(packageContext, SearchActivity.class);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(VimeoApplication.get(this).getComponent())
                .build();
        activityComponent.inject(this);

        // Check for saved fragment tag so that we can load up the correct fragment after rotation
        if (savedInstanceState != null) {
            mSearchQuery = savedInstanceState.getString (SAVED_SEARCH_QUERY);
        }

        initToolbar();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById (R.id.activity_search_fragment_container);

        if (fragment == null) {
            fragment = SuggestionsFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.activity_search_fragment_container, fragment, FRAGMENT_SEARCH_SUGGESTIONS_TAG)
                    .commit();
        }
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchView.setOnQueryTextListener(null);
        mSearchItem = null;
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_SEARCH_QUERY, mSearchQuery);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        mSearchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) mSearchItem.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ViewUtil.hideKeyboard(SearchActivity.this);
                mSearchQuery = query;
                // SuggestionsFragment and ResultsTabFragment are subscribed to these events
                mSearchEventBus.post(new SearchEvent(query));

                if (!resultsFragmentDisplayed()) {
                    FragmentManager fm = getSupportFragmentManager();

                    fm.beginTransaction()
                            .replace(R.id.activity_search_fragment_container, ResultsFragment.newInstance(), FRAGMENT_SEARCH_RESULTS_TAG)
                            .addToBackStack(null)
                            .commit();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String queryText) {
                if (TextUtils.isEmpty(queryText)) {
                    if (resultsFragmentDisplayed()) {
                        getSupportFragmentManager().popBackStack();
                    }
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private boolean resultsFragmentDisplayed() {
        return getSupportFragmentManager().getBackStackEntryCount() != 0;
    }

    @Override
    public void onArrowIconClick (String suggestion) {
        mSearchItem.expandActionView();
        mSearchView.setQuery(suggestion, false);
    }

    @Override
    public void onViewHolderClick (String suggestion) {
        mSearchItem.expandActionView();
        mSearchView.setQuery(suggestion, true);
    }
}
