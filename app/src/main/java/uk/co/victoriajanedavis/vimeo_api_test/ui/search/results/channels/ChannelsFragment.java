package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.channels;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoChannel;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabPresenter;

public class ChannelsFragment extends ResultsTabFragment<VimeoChannel> implements FollowButtonViewHolder.FollowButtonClickListener {

    private static final String TAG = "ChannelsFragment";

    @Inject ChannelsPresenter mPresenter;


    public static ChannelsFragment newInstance() {
        return new ChannelsFragment();
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
        return R.string.error_no_channels_to_display;
    }

    @Override
    public ResultsTabPresenter<VimeoChannel> getPresenter() {
        return mPresenter;
    }

    @Override
    public ListAdapter<VimeoChannel> createListAdapter() {
        return new FollowButtonListAdapter<VimeoChannel>(getContext(), this, ChannelViewHolder::new, this);
    }

    @Override
    protected String getCollectionUri() {
        return "channels";
    }

    @Override
    public String getSingularHeaderMetric() {
        return "channel";
    }

    @Override
    public String getPluralHeaderMetric() {
        return "channels";
    }

    // The next two Overrides are for FollowButtonViewHolder.FollowButtonClickListener
    @Override
    public Single<Response<Void>> onFollowButtonClick(long channel_id) {
        return mPresenter.getSubscribeToChannelCompletable(channel_id);
    }

    @Override
    public Single<Response<Void>> onUnfollowButtonClick(long channel_id) {
        return mPresenter.getUnsubscribeFromChannelCompletable(channel_id);
    }
}
