package uk.co.victoriajanedavis.vimeo_api_test.ui.reply;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoComment;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BasePresenter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.MvpView;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions.SuggestionsMvpView;
import uk.co.victoriajanedavis.vimeo_api_test.util.RxUtil;

@ConfigPersistent
public class ReplyPresenter extends BasePresenter<ReplyMvpView> {

    private final DataManager mDataManager;
    private Disposable mDisposable;

    private SingleObserver<VimeoComment> mObserver = new SingleObserver<VimeoComment>() {
        @Override
        public void onSubscribe(Disposable d) {
            mDisposable = d;
        }

        @Override
        public void onSuccess(VimeoComment vimeoComment) {
            if (!isViewAttached()) return;
            getMvpView().hideProgress();
            getMvpView().setResponseResultAndExit(vimeoComment);
        }

        @Override
        public void onError(Throwable e) {
            if (!isViewAttached()) return;
            getMvpView().hideProgress();
            getMvpView().showError(e.getMessage());
        }
    };


    @Inject
    public ReplyPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ReplyMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void postCommentOnVideo(long video_id, String comment) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showProgress();
        mDataManager.postCommentOnVideo(video_id, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    public void postReplyToCommentOnVideo(long video_id, long comment_id, String comment) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showProgress();
        mDataManager.postReplyToCommentOnVideo(video_id, comment_id, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }
}
