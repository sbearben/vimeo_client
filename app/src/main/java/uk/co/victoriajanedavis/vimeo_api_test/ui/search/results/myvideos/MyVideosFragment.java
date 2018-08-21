package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.myvideos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabPresenter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.UserVideoViewHolder;

public class MyVideosFragment extends ResultsTabFragment<VimeoVideo> {

    private static final String TAG = "MyVideosFragment";

    @Inject MyVideosPresenter mPresenter;


    public static MyVideosFragment newInstance() {
        return new MyVideosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        getPresenter().attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getPresenter().detachView();
    }

    @Override
    @StringRes
    public int getOnEmptyListErrorMessageStringRes() {
        return R.string.error_no_videos_to_display;
    }

    @Override
    public ResultsTabPresenter<VimeoVideo> getPresenter() {
        return mPresenter;
    }

    @Override
    public ListAdapter<VimeoVideo> createListAdapter() {
        return new ListAdapter<>(getContext(), this, UserVideoViewHolder::new);
    }

    @Override
    protected String getCollectionUri() {
        return "me/videos";
    }

    @Override
    public String getSingularHeaderMetric() {
        return "video";
    }

    @Override
    public String getPluralHeaderMetric() {
        return "videos";
    }

}
