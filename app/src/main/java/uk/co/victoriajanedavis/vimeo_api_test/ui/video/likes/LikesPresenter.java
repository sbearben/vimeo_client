package uk.co.victoriajanedavis.vimeo_api_test.ui.video.likes;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.base.VideoTabPresenter;

@ConfigPersistent
public class LikesPresenter extends VideoTabPresenter<VimeoUser> {

    @Inject
    public LikesPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public Observable<Response<VimeoCollection<VimeoUser>>> getObservable(String url, String query, Integer page, Integer per_page) {
        return mDataManager.getUserCollectionByUrlAndQuery(url, null, page, per_page);
    }

    public Single<Response<Void>> getFollowUserSingle (long user_id) {
        return mDataManager.getFollowUserSingle(user_id);
    }

    public Single<Response<Void>> getUnfollowUserSingle (long user_id) {
        return mDataManager.getUnfollowUserSingle(user_id);
    }
}
