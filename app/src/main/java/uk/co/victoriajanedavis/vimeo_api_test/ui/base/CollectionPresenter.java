package uk.co.victoriajanedavis.vimeo_api_test.ui.base;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.RemoteObserver;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ParcelableListItem;
import uk.co.victoriajanedavis.vimeo_api_test.util.RxUtil;

public abstract class CollectionPresenter<A extends CollectionMvpView<T>, T extends ParcelableListItem>
        extends BasePresenter<A> {

    private static final String TAG = "CollectionPresenter";

    protected DataManager mDataManager;
    protected Disposable mDisposable;

    protected String mNextCollectionUri;


    @Override
    public void attachView(A mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void onInitialListRequested(String uri, String query, Integer per_page) {
        getCollectionByUrlAndQuery (uri, query, 1, per_page);
    }

    public void onListEndReached() {
        getCollectionByUrlAndQuery(mNextCollectionUri, null, null, null);
    }

    protected void getCollectionByUrlAndQuery (String url, String query, Integer page, Integer per_page) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showMessageLayout(false);
        getMvpView().showProgress();

        getObservable(url, query, page, per_page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RemoteObserver<VimeoCollection<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onSuccess(VimeoCollection<T> collection) {
                        if (!isViewAttached()) return;
                        getMvpView().hideProgress();

                        if (collection.getPaging() != null) {
                            mNextCollectionUri = collection.getPaging().getNextUri();
                        }

                        getMvpView().showItems(collection);

                        if (collection.getData().isEmpty()) {
                            getMvpView().showEmpty();
                        }
                    }

                    @Override
                    public void onUnauthorized(Response<VimeoCollection<T>> response) {
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

    public String getNextCollectionUri() {
        return mNextCollectionUri;
    }

    public abstract Observable<Response<VimeoCollection<T>>> getObservable(String url, String query, Integer page, Integer per_page);
}
