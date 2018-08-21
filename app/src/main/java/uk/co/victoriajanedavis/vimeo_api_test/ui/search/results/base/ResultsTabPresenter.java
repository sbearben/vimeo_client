package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ParcelableListItem;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.RemoteObserver;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BasePresenter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.CollectionPresenter;
import uk.co.victoriajanedavis.vimeo_api_test.util.RxUtil;

public abstract class ResultsTabPresenter<T extends ParcelableListItem> extends CollectionPresenter<ResultsTabMvpView<T>, T> {

    private static final String TAG = "VideoTabPresenter";
}
