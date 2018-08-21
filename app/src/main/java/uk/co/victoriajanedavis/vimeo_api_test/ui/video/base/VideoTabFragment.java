package uk.co.victoriajanedavis.vimeo_api_test.ui.video.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.EndlessRecyclerViewOnScrollListener;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.MarginDividerItemDecoration;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ParcelableListItem;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.VideoFragment;
import uk.co.victoriajanedavis.vimeo_api_test.util.DisplayMetricsUtil;

public abstract class VideoTabFragment<T extends ParcelableListItem>
        extends CollectionFragment<VideoTabMvpView<T>, T> implements VideoTabMvpView<T> {

    private static final String TAG = "VideoTabFragment";

    protected VimeoConnection mConnection;
    private boolean mIsViewShown = false;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mIsViewShown) {
            updateVideoAndRequestData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            mIsViewShown = true;
            updateVideoAndRequestData();
        } else {
            mIsViewShown = false;
        }
    }

    private void updateVideoAndRequestData() {
        if (getVideoFragment() != null) {
            VimeoVideo currentVideo = ((GetVideoCallback) getVideoFragment()).getVideo();
            if (!getConnectionFromVideo(currentVideo).getUri().equals(mConnection.getUri())) {
                mConnection = getConnectionFromVideo(currentVideo);
                onRefresh();
            }
        }
    }

    /*
     * This function returns the HOST (parent) fragment (ie. VideoFragment, which has the ViewPager that is
     * instantiating all the child VideoTabFragment subclasses)
     */
    @Nullable
    public VideoFragment getVideoFragment() {
        if (getFragmentManager() != null) {
            return (VideoFragment) getFragmentManager().findFragmentByTag(VideoFragment.FRAGMENT_VIDEO_TAG);
        }
        else {
            return null;
        }
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.fragment_video_tab;
    }

    @Override
    protected String getCollectionUri() {
        return mConnection.getUri();
    }

    @Override
    protected String getQuery() {
        return null;
    }

    public abstract VimeoConnection getConnectionFromVideo (VimeoVideo video);

    // Interface that the hosting (Root) Fragment must implement
    public interface GetVideoCallback {
        VimeoVideo getVideo();
    }
}
