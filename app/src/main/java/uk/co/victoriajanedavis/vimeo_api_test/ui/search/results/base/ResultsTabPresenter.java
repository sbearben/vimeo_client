package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base;

import android.os.Parcelable;

import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionPresenter;

public abstract class ResultsTabPresenter<T extends Parcelable> extends CollectionPresenter<ResultsTabMvpView<T>, T> {

    private static final String TAG = "VideoTabPresenter";
}
