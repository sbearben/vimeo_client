package uk.co.victoriajanedavis.vimeo_api_test.ui.explore;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.RemoteObserver;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BasePresenter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionPresenter;
import uk.co.victoriajanedavis.vimeo_api_test.util.RxUtil;

@ConfigPersistent
public class ExplorePresenter extends CollectionPresenter<ExploreMvpView, VimeoVideo> {

    @Inject
    public ExplorePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onInitialListRequested(String uri, String query, Integer per_page) {
        getCategoryAndVideoLists(1, 10);
    }

    /*
     * NOTE: the arguments here will only be applied to getting the videos since there are only
     * 16 Categories, so we'll get them all and don't need pagination
     */
    private void getCategoryAndVideoLists (Integer page, Integer per_page) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showMessageLayout(false);
        getMvpView().showProgress();
        mDataManager.getCategoryAndVideoCollection(page, per_page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RemoteObserver<VimeoCategoryAndVideoCollectionHolder>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onSuccess(VimeoCategoryAndVideoCollectionHolder holder) {
                        if (!isViewAttached()) return;
                        getMvpView().hideProgress();

                        mNextCollectionUri = holder.getVideosCollection().getPaging().getNextUri();

                        getMvpView().showCategories(holder.getCategoriesCollection().getData());
                        getMvpView().showItems(holder.getVideosCollection());
                    }

                    @Override
                    public void onUnauthorized(Response<VimeoCategoryAndVideoCollectionHolder> response) {
                        if (!isViewAttached()) return;
                        getMvpView().hideProgress();
                        getMvpView().showUnauthorizedError();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (!isViewAttached()) return;
                        getMvpView().hideProgress();
                        getMvpView().showError(throwable.getMessage());
                    }
                });
    }

    @Override
    public Observable<Response<VimeoCollection<VimeoVideo>>> getObservable(String url, String query, Integer page, Integer per_page) {
        return mDataManager.getVideoCollectionByUrlAndQuery(url, query, page, per_page);
    }
}
