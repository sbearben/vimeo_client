package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedHashMap;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.allvideos.AllVideosFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.channels.ChannelsFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.myvideos.MyVideosFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.people.PeopleFragment;

public class ResultsPagerAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_TABS = 4;

    private Context mContext;
    //private LinkedHashMap<Integer, Fragment> mPageReferenceMap;
    //mPageReferenceMap.put(index, myFragment);


    public ResultsPagerAdapter (Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem (int position) {
        switch (position) {
            case 0:
                return AllVideosFragment.newInstance();
            case 1:
                return MyVideosFragment.newInstance();
            case 2:
                return ChannelsFragment.newInstance();
            case 3:
                return PeopleFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.results_pager_adapter_allvideos);
            case 1:
                return mContext.getString(R.string.results_pager_adapter_myvideos);
            case 2:
                return mContext.getString(R.string.results_pager_adapter_channels);
            case 3:
                return mContext.getString(R.string.results_pager_adapter_people);
            default:
                return null;
        }
    }


}
