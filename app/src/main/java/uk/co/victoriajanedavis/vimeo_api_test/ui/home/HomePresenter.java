package uk.co.victoriajanedavis.vimeo_api_test.ui.home;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionPresenter;

@ConfigPersistent
public class HomePresenter extends CollectionPresenter<HomeMvpView, VimeoVideo> {

    @Inject
    public HomePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public Observable<Response<VimeoCollection<VimeoVideo>>> getObservable(String url, String query, Integer page, Integer per_page) {
        return mDataManager.getVideoCollectionByUrlAndQuery(url, query, page, per_page);
    }
}
