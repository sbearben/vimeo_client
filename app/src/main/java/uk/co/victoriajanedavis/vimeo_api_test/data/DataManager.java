package uk.co.victoriajanedavis.vimeo_api_test.data;

import java.util.Set;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.BuildConfig;
import uk.co.victoriajanedavis.vimeo_api_test.data.local.PreferencesHelper;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoAccessToken;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCategory;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoChannel;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoComment;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo.VimeoApiService;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationScope;
import uk.co.victoriajanedavis.vimeo_api_test.ui.explore.VimeoCategoryAndVideoCollectionHolder;
import uk.co.victoriajanedavis.vimeo_api_test.util.HttpUtil;

@ApplicationScope
public class DataManager {

    private final VimeoApiService mVimeoApiService;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(VimeoApiService vimeoApiService, PreferencesHelper preferencesHelper) {
        mVimeoApiService = vimeoApiService;
        mPreferencesHelper = preferencesHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<VimeoAccessToken> getAuthenticatedAccessToken (String code, String redirect_uri) {
        return mVimeoApiService.getAuthenticatedToken (HttpUtil.basicAuthorizationHeader(
                BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET),
                code, redirect_uri);
    }

    public void setAccessToken (VimeoAccessToken vimeoAccessToken) {
        mPreferencesHelper.setAccessToken(vimeoAccessToken);
    }

    public void deleteAccessToken() {
        mPreferencesHelper.deleteAccessToken();
    }

    public Observable<Set<String>> getSearchSuggestionsSet() {
        return Observable.fromCallable(mPreferencesHelper::getSearchSuggestionsSet);
    }

    public void setSearchSuggestionsSet(Set<String> suggestionSet) {
        mPreferencesHelper.setSearchSuggestionsSet(suggestionSet);
    }


    public Observable<VimeoCollection<VimeoVideo>> getVideoCollectionByUrl (String url, Integer page, Integer per_page) {
        return mVimeoApiService.getVideoList1(url, null, page, per_page);
    }

    public Observable<Response<VimeoCollection<VimeoVideo>>> getVideoCollectionByUrlAndQuery (String url, String query, Integer page, Integer per_page) {
        return mVimeoApiService.getVideoList(url, query, page, per_page);
    }

    public Observable<VimeoUser> getUserProfile() {
        return mVimeoApiService.getMyProfile();
    }


    public Observable<VimeoCollection<VimeoCategory>> getCategoriesCollection (Integer page, Integer per_page) {
        return mVimeoApiService.getCategories(page, per_page);
    }

    public Observable<VimeoCategoryAndVideoCollectionHolder> getCategoryAndVideoCollection (Integer page, Integer per_page) {
        return Observable.zip(getCategoriesCollection(null, null), getVideoCollectionByUrl("channels/staffpicks/videos", page, per_page),
                VimeoCategoryAndVideoCollectionHolder::new);
    }

    public Observable<VimeoUser> getUserAndVideoCollection (Integer page, Integer per_page) {
        return Observable.zip(getUserProfile(), getVideoCollectionByUrl("me/videos", page, per_page),
                (user, videoCollection) -> {
                    user.setVideosCollection(videoCollection);
                    return user;
                });
    }

    public Observable<Response<VimeoCollection<VimeoChannel>>> getChannelCollectionByUrlAndQuery (String url, String query, Integer page, Integer per_page) {
        return mVimeoApiService.searchChannels(url, query, page, per_page);
    }

    public Observable<Response<VimeoCollection<VimeoUser>>> getUserCollectionByUrlAndQuery (String url, String query, Integer page, Integer per_page) {
        return mVimeoApiService.searchUsers(url, query, page, per_page);
    }

    public Observable<Response<VimeoCollection<VimeoComment>>> getCommentCollectionByUrl (String url, String direction, Integer page, Integer per_page) {
        return mVimeoApiService.getComments(url, direction, page, per_page);
    }

    public Single<Response<Void>> getFollowUserSingle (long user_id) {
        return mVimeoApiService.followUser(user_id);
    }

    public Single<Response<Void>> getUnfollowUserSingle (long user_id) {
        return mVimeoApiService.unfollowUser(user_id);
    }

    public Single<Response<Void>> getSubscribeToChannelSingle (long channel_id) {
        return mVimeoApiService.subscribeToChannel(channel_id);
    }

    public Single<Response<Void>> getUnsubscribeFromChannelSingle (long channel_id) {
        return mVimeoApiService.unsubscribeFromChannel(channel_id);
    }

    public Single<VimeoComment> postCommentOnVideo(long video_id, String comment) {
        return mVimeoApiService.postCommentOnVideo(video_id, comment);
    }

    public Single<VimeoComment> postReplyToCommentOnVideo(long video_id, long comment_id, String comment) {
        return mVimeoApiService.postReplyToCommentOnVideo(video_id, comment_id, comment);
    }
}
