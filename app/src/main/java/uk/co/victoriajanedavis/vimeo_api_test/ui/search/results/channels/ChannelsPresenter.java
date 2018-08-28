package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.channels;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoChannel;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabPresenter;

@ConfigPersistent
public class ChannelsPresenter extends ResultsTabPresenter<VimeoChannel> {

    @Inject
    public ChannelsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public Observable<Response<VimeoCollection<VimeoChannel>>> getObservable(String url, String query, Integer page, Integer per_page) {
        return mDataManager.getChannelCollectionByUrlAndQuery(url, query, page, per_page);
    }

    public Single<Response<Void>> getSubscribeToChannelCompletable (long channel_id) {
        return mDataManager.getSubscribeToChannelCompletable(channel_id);
    }

    public Single<Response<Void>> getUnsubscribeFromChannelCompletable (long channel_id) {
        return mDataManager.getUnsubscribeFromChannelCompletable(channel_id);
    }
}
