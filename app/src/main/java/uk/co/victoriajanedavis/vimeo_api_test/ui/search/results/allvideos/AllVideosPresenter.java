package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.allvideos;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabPresenter;

@ConfigPersistent
public class AllVideosPresenter extends ResultsTabPresenter<VimeoVideo> {

    @Inject
    public AllVideosPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public Observable<Response<VimeoCollection<VimeoVideo>>> getObservable(String url, String query, Integer page, Integer per_page) {
        return mDataManager.getVideoCollectionByUrlAndQuery(url, query, page, per_page);
    }
}
