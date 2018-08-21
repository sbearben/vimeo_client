package uk.co.victoriajanedavis.vimeo_api_test.ui.user.base;

import java.util.List;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionMvpView;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.MvpView;

public interface UserMvpView extends CollectionMvpView<VimeoVideo> {

    void updateUserViews(VimeoUser user);
}
