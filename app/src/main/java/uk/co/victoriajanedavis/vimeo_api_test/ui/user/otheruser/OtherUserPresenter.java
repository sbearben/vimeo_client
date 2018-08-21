package uk.co.victoriajanedavis.vimeo_api_test.ui.user.otheruser;

import javax.inject.Inject;

import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.base.UserPresenter;

@ConfigPersistent
public class OtherUserPresenter extends UserPresenter {

    @Inject
    public OtherUserPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onInitialListRequested(String uri, String query, Integer per_page) {
        getCollectionByUrlAndQuery (uri, query, 1, 10);
    }
}
