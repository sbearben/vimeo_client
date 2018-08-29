package uk.co.victoriajanedavis.vimeo_api_test.ui.base;

import android.os.Parcelable;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;

public interface CollectionMvpView<T extends Parcelable> extends MvpView {

    void showItems(VimeoCollection<T> itemCollection);
}
