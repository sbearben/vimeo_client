package uk.co.victoriajanedavis.vimeo_api_test.ui.video.base;

import android.os.Parcelable;

import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionPresenter;

public abstract class VideoTabPresenter<T extends Parcelable> extends CollectionPresenter<VideoTabMvpView<T>, T> {

    private static final String TAG = "VideoTabPresenter";
}
