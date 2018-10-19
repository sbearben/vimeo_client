package uk.co.victoriajanedavis.vimeo_api_test.paging.videos;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.paging.base.VimeoCollectionDataSource;

public class VideosDataSource extends VimeoCollectionDataSource<VimeoVideo> {

    private final DataManager mDataManager;


    public VideosDataSource(DataManager dataManager, CompositeDisposable disposables) {
        super(disposables);
        mDataManager = dataManager;
    }

    @Override
    public Observable<Response<VimeoCollection<VimeoVideo>>> getObservable(String url, String query, Integer page, Integer per_page) {
        return mDataManager.getVideoCollectionByUrlAndQuery(url, query, page, per_page);
    }
}
