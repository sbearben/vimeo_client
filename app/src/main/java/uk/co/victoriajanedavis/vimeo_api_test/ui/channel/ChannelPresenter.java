package uk.co.victoriajanedavis.vimeo_api_test.ui.channel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionPresenter;

@ConfigPersistent
public class ChannelPresenter extends CollectionPresenter<ChannelMvpView, VimeoVideo> {

    private DataManager mDataManager;

    private Disposable mDisposable;
    private String mNextVideoCollectionUri;


    @Inject
    public ChannelPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public Observable<Response<VimeoCollection<VimeoVideo>>> getObservable(String url, String query, Integer page, Integer per_page) {
        return mDataManager.getVideoCollectionByUrlAndQuery(url, query, page, per_page);
    }
}
