package uk.co.victoriajanedavis.vimeo_api_test.ui.user.currentuser;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoAccessToken;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoAccessTokenHolder;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.RemoteObserver;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.base.UserPresenter;
import uk.co.victoriajanedavis.vimeo_api_test.util.RxUtil;

@ConfigPersistent
public class CurrentUserPresenter extends UserPresenter {

    private final VimeoAccessTokenHolder mAccessTokenHolder;


    @Inject
    CurrentUserPresenter(DataManager dataManager, VimeoAccessTokenHolder accessTokenHolder) {
        mDataManager = dataManager;
        mAccessTokenHolder = accessTokenHolder;
    }

    @Override
    public void onInitialListRequested(String uri, String query, Integer per_page) {
        getCompleteUserData(1, 10);
    }

    private void getCompleteUserData (Integer page, Integer per_page) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showMessageLayout(false);
        getMvpView().showProgress();
        mDataManager.getUserAndVideoCollection(page, per_page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VimeoUser>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(VimeoUser user) {
                        if (!isViewAttached()) return;
                        getMvpView().hideProgress();

                        mNextCollectionUri = user.getVideosCollection().getPaging().getNextUri();

                        if (user.getVideosCollection().getData().isEmpty()) {
                            getMvpView().showEmpty();
                        }

                        getMvpView().updateUserViews(user);
                        getMvpView().showItems(user.getVideosCollection());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isViewAttached()) return;
                        getMvpView().hideProgress();

                        if (e instanceof HttpException) {
                            int code = ((HttpException) e).code();
                            if (code == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                                getMvpView().showUnauthorizedError();
                                return;
                            }
                        }

                        getMvpView().showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    void getOauthToken (String code, String redirect_uri) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showMessageLayout(false);
        getMvpView().showProgress();
        mDataManager.getAuthenticatedAccessToken(code, redirect_uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VimeoAccessToken>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(VimeoAccessToken vimeoAccessToken) {
                        if (!isViewAttached()) return;
                        vimeoAccessToken.setTokenAuthLevel(VimeoAccessToken.TOKEN_LEVEL_AUTHENTICATED);
                        mDataManager.setAccessToken(vimeoAccessToken);
                        mAccessTokenHolder.login(vimeoAccessToken);
                        onInitialListRequested(null, null, null);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (!isViewAttached()) return;
                        getMvpView().hideProgress();
                        getMvpView().showError(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    void onLogout() {
        mDataManager.deleteAccessToken();
        mAccessTokenHolder.logout();
        getMvpView().showUnauthorizedError();
    }
}
