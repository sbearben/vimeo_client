package uk.co.victoriajanedavis.vimeo_api_test.ui.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.paging.State;
import uk.co.victoriajanedavis.vimeo_api_test.paging.videos.VideosDataSourceFactory;
import uk.co.victoriajanedavis.vimeo_api_test.paging.base.VimeoCollectionDataSource;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BasePresenter;

@ConfigPersistent
public class HomePresenter extends BasePresenter<HomeMvpView> {

    private final DataManager mDataManager;

    private final VideosDataSourceFactory mDataSourceFactory;
    private final CompositeDisposable mDisposables;
    private final LiveData<PagedList<VimeoVideo>> mVideosList;


    @Inject
    public HomePresenter(DataManager dataManager) {
        mDataManager = dataManager;

        mDisposables = new CompositeDisposable();

        mDataSourceFactory = new VideosDataSourceFactory(mDataManager, mDisposables);
        mDataSourceFactory.setInitialUri("channels/staffpicks/videos");

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(10)
                .setInitialLoadSizeHint(20)
                .setEnablePlaceholders(false)
                .build();

        mVideosList = new LivePagedListBuilder<>(mDataSourceFactory, config).build();

    }

    public void clearDisposables() {
        mDisposables.clear();
    }

    LiveData<State> getStateLiveData() {
        return Transformations.switchMap (mDataSourceFactory.getCollectionDataSourceLiveData(), VimeoCollectionDataSource::getStateLiveData);
    }

    LiveData<PagedList<VimeoVideo>> getVideoListLiveData() {
        return mVideosList;
    }

    void retry() {
        if (mDataSourceFactory.getCollectionDataSourceLiveData().getValue() != null) {
            mDataSourceFactory.getCollectionDataSourceLiveData().getValue().retry();
        }
    }

    void invalidateDataSource() {
        if (mDataSourceFactory.getCollectionDataSourceLiveData().getValue() != null) {
            mDataSourceFactory.getCollectionDataSourceLiveData().getValue().invalidate();
        }
    }
}
