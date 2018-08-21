package uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BasePresenter;
import uk.co.victoriajanedavis.vimeo_api_test.util.RxUtil;

@ConfigPersistent
public class SuggestionsPresenter extends BasePresenter<SuggestionsMvpView> {

    private final DataManager mDataManager;
    private Disposable mDisposable;

    private List<SuggestionItem> mSuggestionsList;


    @Inject
    public SuggestionsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SuggestionsMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void onInitialListRequested() {
        getSearchSuggestionsList();
    }

    private void getSearchSuggestionsList() {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showMessageLayout(false);
        getMvpView().showProgress();
        mDataManager.getSearchSuggestionsSet()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Set<String>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Set<String> suggestionsSet) {
                        if (!isViewAttached()) return;
                        getMvpView().hideProgress();

                        mSuggestionsList = SuggestionItem.stringSetToSuggestionItemList(suggestionsSet);
                        getMvpView().showSuggestions(mSuggestionsList);
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
}
