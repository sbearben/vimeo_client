package uk.co.victoriajanedavis.vimeo_api_test.paging.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.RemoteObserver;
import uk.co.victoriajanedavis.vimeo_api_test.paging.State;

public abstract class VimeoCollectionDataSource<T extends Parcelable> extends PageKeyedDataSource<String, T> {

    private final CompositeDisposable mDisposables;

    private MutableLiveData<State> mState = new MutableLiveData<>();
    private Completable mRetryCompletable;

    private String mInitialUri;
    private String mSearchQuery;


    public VimeoCollectionDataSource (CompositeDisposable disposables) {
        mDisposables = disposables;
    }

    public abstract Observable<Response<VimeoCollection<T>>> getObservable(String url, String query, Integer page, Integer per_page);

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, T> callback) {
        updateState(State.LOADING_INITIAL);
        getObservable(mInitialUri, mSearchQuery, 1, params.requestedLoadSize)
                .subscribe(new RemoteObserver<VimeoCollection<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onSuccess(VimeoCollection<T> collection) {
                        if (collection.getData().isEmpty()) updateState(State.NO_DATA);
                        else updateState(State.SUCCESS);

                        callback.onResult(collection.getData(), null, collection.getPaging().getNextUri());
                    }

                    @Override
                    public void onUnauthorized(Response<VimeoCollection<T>> response) {
                        updateState(State.UNAUTHORIZED);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        updateState(State.ERROR);
                        setRetry(() -> loadInitial(params, callback));
                    }
                });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, T> callback) {
        updateState(State.LOADING_MORE);
        getObservable(params.key, null, null, null)
                .subscribe(new RemoteObserver<VimeoCollection<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onSuccess(VimeoCollection<T> collection) {
                        updateState(State.SUCCESS);
                        callback.onResult(collection.getData(), collection.getPaging().getNextUri());
                    }

                    @Override
                    public void onUnauthorized(Response<VimeoCollection<T>> response) {
                        updateState(State.UNAUTHORIZED);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        updateState(State.ERROR);
                        setRetry(() -> loadAfter(params, callback));
                    }
                });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, T> callback) {
    }

    public MutableLiveData<State> getStateLiveData() {
        return mState;
    }

    public void setInitialUri(String uri) {
        mInitialUri = uri;
    }

    public void setSearchQuery(String query) {
        mSearchQuery = query;
    }

    private void updateState(State state) {
        mState.postValue(state);
    }

    public void retry() {
        if (mRetryCompletable != null) {
            mDisposables.add(mRetryCompletable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe());
        }
    }

    private void setRetry(Action action) {
        mRetryCompletable = (action == null) ? null : Completable.fromAction(action);
    }
}
