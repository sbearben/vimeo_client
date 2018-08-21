package uk.co.victoriajanedavis.vimeo_api_test.ui.base;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ParcelableListItem;

public interface CollectionMvpView<T extends ParcelableListItem> extends MvpView {

    void showItems(VimeoCollection<T> itemCollection);
}
