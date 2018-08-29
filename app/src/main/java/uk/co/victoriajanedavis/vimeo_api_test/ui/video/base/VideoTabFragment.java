package uk.co.victoriajanedavis.vimeo_api_test.ui.video.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.VideoFragment;

public abstract class VideoTabFragment<T extends Parcelable>
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
