package uk.co.victoriajanedavis.vimeo_api_test.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uk.co.victoriajanedavis.vimeo_api_test.VimeoApplication;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.local.PreferencesHelper;
import uk.co.victoriajanedavis.vimeo_api_test.injection.component.ActivityComponent;
import uk.co.victoriajanedavis.vimeo_api_test.injection.component.ConfigPersistentComponent;
import uk.co.victoriajanedavis.vimeo_api_test.injection.component.DaggerConfigPersistentComponent;
import uk.co.victoriajanedavis.vimeo_api_test.injection.module.ActivityModule;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseActivity;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.ResultsFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions.SuggestionsFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions.SuggestionsViewHolder;

/*
 * The code in this Activity is quite ugly and I wonder if instead of having two Fragments (currently
 * SuggestionsFragment and ResultsFragment) that I'm hiding/showing, I should just have one Fragment with
 * 2 views that are being hidden/shown. However, I feel like that way would create a very spaghetti-like
 * Fragment handling a hell of a lot of different things. Having an Activity host adds another layer that,
 * although makes passing data to lower level Fragments somewhat more convoluted, at least makes for a slightly-less
 * messy host. I'm not sure what other options there would be in order to mimic the search functionality of the
 * Vimeo app.
 */
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener,
        SuggestionsViewHolder.OnClickListener, ResultsTabFragment.GetQueryCallback {

    private static final String TAG = "SearchActivity";

    // For saving our instance state so that we open the correct fragment after rotation
    private static final String SAVED_SELECTED_FRAGMENT = "selected_fragment_tag";
    private static final String SAVED_SEARCH_QUERY = "current_search_query";

    private static final String FRAGMENT_SEARCH_SUGGESTIONS_TAG = "fragment_search_suggestions";
    private static final String FRAGMENT_SEARCH_RESULTS_TAG = "fragment_search_results";

    @Inject PreferencesHelper mPreferencesHelper;

    private String mSelectedFragmentTag = FRAGMENT_SEARCH_SUGGESTIONS_TAG;

    private Toolbar mToolbar;
    private SearchView mSearchView;
    private MenuItem mSearchItem;

    private String mSearchQuery = "";
    private List<String> mSugggestionsList;


    public static Intent newIntent (Context packageContext) {
        return new Intent(packageContext, SearchActivity.class);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inflate the activity's view
        setContentView(R.layout.activity_search);

        ConfigPersistentComponent configPersistentComponent = DaggerConfigPersistentComponent.builder()
                .applicationComponent(VimeoApplication.get(this).getComponent())
                .build();

        ActivityComponent activityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
        activityComponent.inject(this);

        // Set and get the toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Check for saved fragment tag so that we can load up the correct fragment after rotation
        if (savedInstanceState != null) {
            mSelectedFragmentTag = savedInstanceState.getString (SAVED_SELECTED_FRAGMENT);
            mSearchQuery = savedInstanceState.getString (SAVED_SEARCH_QUERY);
        }

        onRequestPastSearches();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchView.setOnQueryTextListener(null);
    }

    private void onRequestPastSearches() {
        Disposable d = Observable.fromCallable(mPreferencesHelper::getSearchSuggestionsSet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringsSet -> {
                    mSugggestionsList = new ArrayList<>(stringsSet);// SuggestionItem.stringSetToSuggestionItemList(stringsSet);
                    displayFragment(mSelectedFragmentTag);
                });
    }

    private void displayFragment (String fragmentTag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Fragment selectedFragment = null;

        Fragment curFrag = fm.getPrimaryNavigationFragment();
        if (curFrag != null) {
            fragmentTransaction.detach(curFrag);
        }

        switch(fragmentTag) {
            case FRAGMENT_SEARCH_SUGGESTIONS_TAG:
                mSelectedFragmentTag = FRAGMENT_SEARCH_SUGGESTIONS_TAG;
                selectedFragment = fm.findFragmentByTag(mSelectedFragmentTag);
                if (selectedFragment == null) {
                    selectedFragment = SuggestionsFragment.newInstance();
                    fragmentTransaction.add(R.id.activity_search_fragment_container, selectedFragment, mSelectedFragmentTag);
                } else {
                    fragmentTransaction.attach(selectedFragment);
                }
                break;
            case FRAGMENT_SEARCH_RESULTS_TAG:
                mSelectedFragmentTag = FRAGMENT_SEARCH_RESULTS_TAG;
                selectedFragment = fm.findFragmentByTag(mSelectedFragmentTag);
                if (selectedFragment == null) {
                    selectedFragment = ResultsFragment.newInstance(mSearchQuery);
                    fragmentTransaction.add(R.id.activity_search_fragment_container, selectedFragment, mSelectedFragmentTag);
                } else {
                    fragmentTransaction.attach(selectedFragment);
                }
                break;
            default:
                return;
        }

        fragmentTransaction.setPrimaryNavigationFragment(selectedFragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    private void saveSearch (String query) {
        // Check to make sure query isn't already in list
        if (!mSugggestionsList.contains(query)) {
        //if (!suggestionsListContainsQuery(query)) {
            //SuggestionItem newItem = new SuggestionItem(query);

            mSugggestionsList.add(query);
            Set<String> searchesSet = new HashSet<>(mSugggestionsList);// SuggestionItem.suggestionItemListToStringSet(mSugggestionsList);

            mPreferencesHelper.setSearchSuggestionsSet(searchesSet);

            // Add new query to adapter in SuggestionsFragment
            SuggestionsFragment suggestionsFragment = (SuggestionsFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_SEARCH_SUGGESTIONS_TAG);
            if (suggestionsFragment != null) {
                suggestionsFragment.getSearchAdapter().add(query);
            }
        }
    }

    /*
    public boolean suggestionsListContainsQuery (String query) {
        for (SuggestionItem item : mSugggestionsList) {
            if (item.getSuggestion().equals(query))
                return true;
        }
        return false;
    }
    */

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_SELECTED_FRAGMENT, mSelectedFragmentTag);
        outState.putString(SAVED_SEARCH_QUERY, mSearchQuery);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        mSearchItem = menu.findItem(R.id.action_search);
        //mSearchItem.expandActionView();
        mSearchView = (SearchView) mSearchItem.getActionView();
        mSearchView.setOnQueryTextListener(this);

        mSearchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mSearchView.clearFocus();
                mSearchQuery = "";

                SuggestionsFragment suggestionsFragment = (SuggestionsFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_SEARCH_SUGGESTIONS_TAG);

                if (suggestionsFragment != null && suggestionsFragment.isVisible()) {
                    //suggestionsFragment.getSearchAdapter().getFilter().filter("");
                    //suggestionsFragment.onRefresh();
                }
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //mSearchView.clearFocus();
        mSearchQuery = query;

        saveSearch(query);

        ResultsFragment resultsFragment = (ResultsFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_SEARCH_RESULTS_TAG);
        if (resultsFragment == null) {
            displayFragment(FRAGMENT_SEARCH_RESULTS_TAG);
            resultsFragment = (ResultsFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_SEARCH_RESULTS_TAG);
        }

        displayFragment(FRAGMENT_SEARCH_RESULTS_TAG);
        if (resultsFragment.isVisible()) {
            Log.d (TAG, "THIS IS CALLED");
            resultsFragment.setSearchQuery(query);
        }
        else {
            Log.d (TAG, "OTHER CALLED");
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String queryText) {
        SuggestionsFragment suggestionsFragment = (SuggestionsFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_SEARCH_SUGGESTIONS_TAG);
        if (suggestionsFragment != null) {
            if (!TextUtils.isEmpty(queryText)) { //not empty
                if (suggestionsFragment.isVisible()) {
                    //suggestionsFragment.getSearchAdapter().getFilter().filter(queryText);
                }
            }
            else { //empty
                mSearchView.clearFocus();
                displayFragment(FRAGMENT_SEARCH_SUGGESTIONS_TAG);
                if (suggestionsFragment.isVisible()) {
                    //suggestionsFragment.getSearchAdapter().getFilter().filter("");
                    //suggestionsFragment.onRefresh();
                }
            }
        }
        return false;
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

    @Override
    public String getQuery() {
        return mSearchQuery;
    }
}
