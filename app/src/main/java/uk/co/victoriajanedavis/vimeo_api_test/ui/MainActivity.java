package uk.co.victoriajanedavis.vimeo_api_test.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseActivity;
import uk.co.victoriajanedavis.vimeo_api_test.ui.explore.ExploreFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.home.HomeFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.SearchActivity;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.currentuser.CurrentUserFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    // For saving our instance state so that we open the correct fragment after rotation
    private static final String SAVED_SELECTED_FRAGMENT = "selected_fragment_tag";

    private static final String ACTION_HOME_TAG = "action_home";
    private static final String ACTION_EXPLORE_TAG = "action_explore";
    private static final String ACTION_PROFILE_TAG = "action_profile";
    private static final String ACTION_SEARCH_TAG = "action_search";

    @BindView(R.id.activity_main_bottom_navigation) BottomNavigationView mBottomNavView;
    @BindView(R.id.activity_main_fab) FloatingActionButton mFab;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    private String mSelectedFragmentTag = ACTION_HOME_TAG;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        setTheme(R.style.AppTheme); // Branded launch
        super.onCreate(savedInstanceState);
        // inflate the activity's view
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Set and get the toolbar
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();

        // Check for saved fragment tag so that we can load up the correct fragment after rotation
        if (savedInstanceState != null) {
            mSelectedFragmentTag = savedInstanceState.getString (SAVED_SELECTED_FRAGMENT);
        }

        mBottomNavView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            Fragment selectedFragment = null;

            Fragment curFrag = fm.getPrimaryNavigationFragment();
            if (curFrag != null) {
                fragmentTransaction.detach(curFrag);
            }

            switch (item.getItemId()) {
                case R.id.action_home:
                    mSelectedFragmentTag = ACTION_HOME_TAG;
                    selectedFragment = fm.findFragmentByTag(mSelectedFragmentTag);
                    if (selectedFragment == null) {
                        selectedFragment = HomeFragment.newInstance();
                        fragmentTransaction.add(R.id.activity_main_fragment_container, selectedFragment, mSelectedFragmentTag);
                    } else {
                        fragmentTransaction.attach(selectedFragment);
                    }
                    supportActionBar.setTitle(R.string.menu_home);
                    break;
                case R.id.action_explore:
                    mSelectedFragmentTag = ACTION_EXPLORE_TAG;
                    selectedFragment = fm.findFragmentByTag(mSelectedFragmentTag);
                    if (selectedFragment == null) {
                        selectedFragment = ExploreFragment.newInstance();
                        fragmentTransaction.add(R.id.activity_main_fragment_container, selectedFragment, mSelectedFragmentTag);
                    } else {
                        fragmentTransaction.attach(selectedFragment);
                    }
                    supportActionBar.setTitle(R.string.menu_explore);
                    break;
                case R.id.action_profile:
                    mSelectedFragmentTag = ACTION_PROFILE_TAG;
                    selectedFragment = fm.findFragmentByTag(mSelectedFragmentTag);
                    if (selectedFragment == null) {
                        selectedFragment = CurrentUserFragment.newInstance();
                        fragmentTransaction.add(R.id.activity_main_fragment_container, selectedFragment, mSelectedFragmentTag);
                    } else {
                        fragmentTransaction.attach(selectedFragment);
                    }
                    supportActionBar.setTitle(R.string.menu_profile);
                    break;
            }

            fragmentTransaction.setPrimaryNavigationFragment(selectedFragment);
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.commitNowAllowingStateLoss();

            return true;
        });

        switch (mSelectedFragmentTag) {
            case ACTION_HOME_TAG:
                mBottomNavView.setSelectedItemId(R.id.action_home);
                break;
            case ACTION_EXPLORE_TAG:
                mBottomNavView.setSelectedItemId(R.id.action_explore);
                break;
            case ACTION_PROFILE_TAG:
                mBottomNavView.setSelectedItemId(R.id.action_profile);
                break;
        }
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_SELECTED_FRAGMENT, mSelectedFragmentTag);
    }

    @OnClick(R.id.activity_main_fab)
    public void onSearchFabClick() {
        Intent intent = SearchActivity.newIntent(this);
        startActivity(intent);
    }
}
