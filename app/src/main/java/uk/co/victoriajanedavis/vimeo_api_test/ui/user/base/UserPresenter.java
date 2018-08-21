package uk.co.victoriajanedavis.vimeo_api_test.ui.user.base;

import io.reactivex.Observable;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionPresenter;

@ConfigPersistent
public abstract class UserPresenter extends CollectionPresenter<UserMvpView, VimeoVideo> {

    @Override
    public abstract void onInitialListRequested(String uri, String query, Integer per_page);

    @Override
    public Observable<Response<VimeoCollection<VimeoVideo>>> getObservable(String url, String query, Integer page, Integer per_page) {
        return mDataManager.getVideoCollectionByUrlAndQuery(url, query, page, per_page);
    }
}
