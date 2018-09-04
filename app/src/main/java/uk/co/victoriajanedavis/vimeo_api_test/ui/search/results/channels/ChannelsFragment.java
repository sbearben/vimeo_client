package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.channels;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoChannel;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonRxBinding;
import uk.co.victoriajanedavis.vimeo_api_test.ui.channel.ChannelFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabPresenter;

public class ChannelsFragment extends ResultsTabFragment<VimeoChannel> implements FollowButtonRxBinding.FollowButtonClickListener {

    private static final String TAG = "ChannelsFragment";

    static final int REQUEST_CHANNEL = 1;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_CHANNEL) {
            VimeoChannel channel = (VimeoChannel) data.getParcelableExtra(ChannelFragment.EXTRA_VIMEO_CHANNEL);
            int position = (int) data.getIntExtra(ChannelFragment.EXTRA_CHANNEL_POSITION, -1);
            mListAdapter.updateItem(position, channel);
        }
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
        return new FollowButtonListAdapter<VimeoChannel>(this, ChannelViewHolder::new, this);
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
        return mPresenter.getSubscribeToChannelSingle(channel_id);
    }

    @Override
    public Single<Response<Void>> onUnfollowButtonClick(long channel_id) {
        return mPresenter.getUnsubscribeFromChannelSingle(channel_id);
    }
}
