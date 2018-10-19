package uk.co.victoriajanedavis.vimeo_api_test.ui.video.upnext;

import android.os.Bundle;
import android.support.annotation.StringRes;

import javax.inject.Inject;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListAdapter;
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
        if (savedInstanceState != null) {
            mConnection = savedInstanceState.getParcelable(SAVED_VIMEO_CONNECTION);
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
        return R.string.error_no_videos_to_display;
    }

    @Override
    public VideoTabPresenter<VimeoVideo> getPresenter() {
        return mPresenter;
    }

    @Override
    public ListAdapter<VimeoVideo> createListAdapter() {
        return new ListAdapter<>(this, UpNextVideoViewHolder::new);
    }

    @Override
    public VimeoConnection getConnectionFromVideo (VimeoVideo video) {
        return video.getMetadata().getRecommendationsConnection();
    }
}
