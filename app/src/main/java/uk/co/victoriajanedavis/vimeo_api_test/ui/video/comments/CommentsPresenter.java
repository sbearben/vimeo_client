package uk.co.victoriajanedavis.vimeo_api_test.ui.video.comments;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoComment;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.base.VideoTabPresenter;

@ConfigPersistent
public class CommentsPresenter extends VideoTabPresenter<VimeoComment> {

    @Inject
    public CommentsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public Observable<Response<VimeoCollection<VimeoComment>>> getObservable(String url, String query, Integer page, Integer per_page) {
        Log.d("CommentsPresenter", "Network call - url: " + url);
        return mDataManager.getCommentCollectionByUrl(url, null, page, per_page);
    }
}
