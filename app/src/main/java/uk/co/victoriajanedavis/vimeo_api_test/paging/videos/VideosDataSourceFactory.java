package uk.co.victoriajanedavis.vimeo_api_test.paging.videos;

import io.reactivex.disposables.CompositeDisposable;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.paging.base.VimeoCollectionDataSource;
import uk.co.victoriajanedavis.vimeo_api_test.paging.base.VimeoCollectionDataSourceFactory;

public class VideosDataSourceFactory extends VimeoCollectionDataSourceFactory<VimeoVideo> {

    private final DataManager mDataManager;
    private final CompositeDisposable mDisposables;


    public VideosDataSourceFactory (DataManager dataManager, CompositeDisposable disposables) {
        mDataManager = dataManager;
        mDisposables = disposables;
    }

    public VimeoCollectionDataSource<VimeoVideo> generateDataSource() {
        return new VideosDataSource(mDataManager, mDisposables);
    }
}
