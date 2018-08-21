package uk.co.victoriajanedavis.vimeo_api_test.ui.explore;

import java.util.List;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCategory;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionMvpView;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.MvpView;

public interface ExploreMvpView extends CollectionMvpView<VimeoVideo> {

    void showCategories(List<VimeoCategory> categoryList);
}
