package uk.co.victoriajanedavis.vimeo_api_test.ui.video.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.UpNextVideoClickEvent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.VideoFragment;
import uk.co.victoriajanedavis.vimeo_api_test.util.eventbus.RxBehaviourEventBus;
import uk.co.victoriajanedavis.vimeo_api_test.util.eventbus.RxPublishEventBus;

public abstract class VideoTabFragment<T extends Parcelable>
        extends CollectionFragment<VideoTabMvpView<T>, T> implements VideoTabMvpView<T> {

    private static final String TAG = "VideoTabFragment";

    protected static final String SAVED_VIMEO_CONNECTION = "fragment_video_tab_connection";

    @Inject RxPublishEventBus mEventBus;
    private Disposable mDisposable;

    protected VimeoConnection mConnection;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        Log.d(TAG, "ON CREATE VIEW");

        mDisposable = mEventBus.filteredObservable(UpNextVideoClickEvent.class).subscribe(
                event -> {
                    Log.d(this.getClass().getSimpleName(), "Video name: " + event.getVideo().getName());
                    //updateVideoAndRequestData();
                    VimeoConnection newConnection = getConnectionFromVideo(event.getVideo());
                    if (!newConnection.getUri().equals(mConnection.getUri())) {
                        mConnection = newConnection;
                        Log.d(this.getClass().getSimpleName(), "onRefresh " + event.getVideo().getName());
                        onRefresh();
                    }
                });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDisposable.dispose();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_VIMEO_CONNECTION, mConnection);
    }

    @Override
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
}
