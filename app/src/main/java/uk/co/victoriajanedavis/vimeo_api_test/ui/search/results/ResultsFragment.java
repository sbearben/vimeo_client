package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

public class ResultsFragment extends BaseFragment {

    private static final String TAG = "ResultsFragment";

    private static final String ARG_SEARCH_QUERY = "search_query";

    @BindView(R.id.fragment_results_viewpager) ViewPager mViewPager;
    @BindView(R.id.fragment_results_tablayout) TabLayout mTabLayout;
    private ResultsPagerAdapter mPagerAdapter;

    private String mSearchQuery;

    private Unbinder mUnbinder;


    public static ResultsFragment newInstance (String query) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SEARCH_QUERY, query);

        ResultsFragment fragment = new ResultsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);

        mSearchQuery = (String) getArguments().getSerializable(ARG_SEARCH_QUERY);

        /* Passing in getChildFragmentManager() here because "mViewPager.setAdapter(null);" in
         * onDestroyView() was crashing the app with error: "java.lang.IllegalStateException: FragmentManager is already executing transactions"
         * when we cleared the search bar and were going back to SearchFragment. They solution was found here:
         *     - https://stackoverflow.com/a/40829361/7648952
         */
        mPagerAdapter = new ResultsPagerAdapter(getContext(), getChildFragmentManager());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_results, container, false);
        mUnbinder = ButterKnife.bind(this, v);

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewPager.setAdapter(null);

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setSearchQuery (String query) {
        mSearchQuery = query;
        // Using childFragmentManager here since that is what was used when initialized the PagerAdapter (explained above in onCreate())
        Fragment fragment = getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.fragment_results_viewpager + ":" + mViewPager.getCurrentItem());

        if (fragment != null && fragment.isVisible()) {
            Log.d (TAG, " setSearchQuery(): fragment not null and is visible");
            ((QuerySubmitCallback) fragment).submitQuery(query);
        }
    }

    // Interface that fragments managed by this Fragment's PagerAdapter must implement (all subclasses of ResultsTabFragment)
    public interface QuerySubmitCallback {
        void submitQuery(String query);
    }
}
