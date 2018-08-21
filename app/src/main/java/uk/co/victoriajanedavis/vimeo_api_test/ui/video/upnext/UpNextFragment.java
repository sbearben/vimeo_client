package uk.co.victoriajanedavis.vimeo_api_test.ui.video.upnext;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.base.VideoTabFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.base.VideoTabPresenter;

public class UpNextFragment extends VideoTabFragment<VimeoVideo> {

    private static final String TAG = "UpNextFragment";

    protected static final String ARG_VIMEO_CONNECTION = "vimeo_connection";

    @Inject UpNextPresenter mPresenter;


    public static UpNextFragment newInstance (VimeoConnection connection) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_VIMEO_CONNECTION, connection);

        UpNextFragment fragment = new UpNextFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        getPresenter().attachView(this);

        mConnection = (VimeoConnection) getArguments().getParcelable(ARG_VIMEO_CONNECTION);
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
    public VideoTabPresenter<VimeoVideo> getPresenter() {
        return mPresenter;
    }

    @Override
    public ListAdapter<VimeoVideo> createListAdapter() {
        return new UpNextListAdapter(getContext(), this, UpNextVideoViewHolder::new, getVideoFragment());
    }

    @Override
    public VimeoConnection getConnectionFromVideo (VimeoVideo video) {
        return video.getMetadata().getRecommendationsConnection();
    }

    // Called by VideoFragment after a new Up Next video is clicked
    public void setRecommendationsConnection (VimeoConnection recommendationsConnection) {
        if (!recommendationsConnection.getUri().equals(mConnection.getUri())) {
            mConnection = recommendationsConnection;
            onRefresh();
        }
    }
}
