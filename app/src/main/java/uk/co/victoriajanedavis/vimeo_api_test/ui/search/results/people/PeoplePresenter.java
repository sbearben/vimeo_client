package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.people;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabPresenter;

@ConfigPersistent
public class PeoplePresenter extends ResultsTabPresenter<VimeoUser> {

    @Inject
    public PeoplePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public Observable<Response<VimeoCollection<VimeoUser>>> getObservable(String url, String query, Integer page, Integer per_page) {
        return mDataManager.getUserCollectionByUrlAndQuery(url, query, page, per_page);
    }
}
