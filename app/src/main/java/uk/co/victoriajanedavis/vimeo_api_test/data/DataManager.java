package uk.co.victoriajanedavis.vimeo_api_test.data;

import java.util.Set;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.Completable;
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
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

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

    public Observable<VimeoAccessToken> getUnauthenticatedAccessToken() {
        return mVimeoApiService.getUnauthenticatedToken (VimeoApiServiceUtil.basicAuthorizationHeader(
                BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET),
                null);
    }

    public Observable<VimeoAccessToken> getAuthenticatedAccessToken (String code, String redirect_uri) {
        return mVimeoApiService.getAuthenticatedToken (VimeoApiServiceUtil.basicAuthorizationHeader(
                BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET),
                code, redirect_uri);
    }

    public Observable<VimeoAccessToken> getAccessToken() {
        return Observable.fromCallable(mPreferencesHelper::getAccessToken);
    }

    public void setAccessToken (VimeoAccessToken vimeoAccessToken) {
        mPreferencesHelper.setAccessToken(vimeoAccessToken);
    }

    public Observable<Set<String>> getSearchSuggestionsSet() {
        return Observable.fromCallable(mPreferencesHelper::getSearchSuggestionsSet);
    }

    public void setSearchSuggestionsSet (Set<String> suggestionsSet) {
        mPreferencesHelper.setSearchSuggestionsSet(suggestionsSet);
    }

    public Observable<Response<VimeoCollection<VimeoVideo>>> getStaffPicksVideoCollection (Integer page, Integer per_page) {
        return mVimeoApiService.getStaffPicksVideos(page, per_page);
    }

    public Observable<Response<VimeoCollection<VimeoVideo>>> getVideoCollectionByUrl (String url, Integer page, Integer per_page) {
        return mVimeoApiService.getVideoList(url, null, page, per_page);
    }

    public Observable<Response<VimeoCollection<VimeoVideo>>> getVideoCollectionByQuery (String query, Integer page, Integer per_page) {
        return mVimeoApiService.searchVideos(query, page, per_page);
    }

    public Observable<Response<VimeoCollection<VimeoVideo>>> getVideoCollectionByUrlAndQuery (String url, String query, Integer page, Integer per_page) {
        return mVimeoApiService.getVideoList(url, query, page, per_page);
    }

    public Observable<Response<VimeoUser>> getUserProfile() {
        return mVimeoApiService.getMyProfile();
    }

    public Observable<Response<VimeoUser>> getUserAndVideoCollection (Integer page, Integer per_page) {
        return Observable.zip(getUserProfile(), getVideoCollectionByUrl("me/videos", page, per_page),
                (userResponse, videoResponse) -> {
            // Here we add the videoCollection to the user object so that we can push the user object downstream containing all our data
            VimeoCollection<VimeoVideo> videoCollection = videoResponse.body();
            VimeoUser user = userResponse.body();
            if (user != null) { // If the service call to get the user is unauthorized/error the VimeoUser object will be null
                user.setVideosCollection(videoCollection);
            }

            /* This is some ugly code that goes through the individual response codes to determine the appropriate collective response.
             * Would love a cleaner alternative to this, but I have to preserve the potential UNAUTHORIZED information as that is crucial
             * for the app to work properly, and I only get that info by wrapping the vimeoObjects in the retrofit Response wrapper.
             */
            if (VimeoApiServiceUtil.responseType(userResponse).equals(VimeoApiServiceUtil.RESPONSE_OK) &&
                    VimeoApiServiceUtil.responseType(videoResponse).equals(VimeoApiServiceUtil.RESPONSE_OK)) {
                return Response.success(user, userResponse.raw());
            }
            else if (VimeoApiServiceUtil.responseType(userResponse).equals(VimeoApiServiceUtil.RESPONSE_UNAUTHORIZED) ||
                    VimeoApiServiceUtil.responseType(videoResponse).equals(VimeoApiServiceUtil.RESPONSE_UNAUTHORIZED)) {
                // instead of using userResponse.errorBody think could do ResponseBody.create(null, "")
                return Response.error(HttpsURLConnection.HTTP_UNAUTHORIZED, userResponse.errorBody());
            }
            else {
                return Response.error(userResponse.errorBody(), userResponse.raw());
            }
        });
    }

    public Observable<Response<VimeoCollection<VimeoCategory>>> getCategoriesCollection (Integer page, Integer per_page) {
        return mVimeoApiService.getCategories(page, per_page);
    }

    /*
     * NOTE: the arguments here will only be applied to getting the videos since there are only
     * 16 Categories, so we'll get them all and don't need pagination
     */
    public Observable<Response<VimeoCategoryAndVideoCollectionHolder>> getCategoryAndVideoCollection (Integer page, Integer per_page) {
        return Observable.zip(getCategoriesCollection(null, null), getVideoCollectionByUrl("channels/staffpicks/videos", page, per_page),
                (categoryResponse, videoResponse) -> {

                    /* This is some ugly code that goes through the individual response codes to determine the appropriate collective response.
                     * Would love a cleaner alternative to this, but I have to preserve the potential UNAUTHORIZED information as that is crucial
                     * for the app to work properly, and I only get that info by wrapping the vimeoObjects in the retrofit Response wrapper.
                     */
                    if (VimeoApiServiceUtil.responseType(categoryResponse).equals(VimeoApiServiceUtil.RESPONSE_OK) &&
                            VimeoApiServiceUtil.responseType(videoResponse).equals(VimeoApiServiceUtil.RESPONSE_OK)) {
                        VimeoCategoryAndVideoCollectionHolder holder = new VimeoCategoryAndVideoCollectionHolder(categoryResponse.body(),
                                videoResponse.body());
                        return Response.success(holder, categoryResponse.raw());
                    }
                    else if (VimeoApiServiceUtil.responseType(categoryResponse).equals(VimeoApiServiceUtil.RESPONSE_UNAUTHORIZED) ||
                            VimeoApiServiceUtil.responseType(videoResponse).equals(VimeoApiServiceUtil.RESPONSE_UNAUTHORIZED)) {
                        // instead of using userResponse.errorBody think could do ResponseBody.create(null, "")
                        return Response.error(HttpsURLConnection.HTTP_UNAUTHORIZED, categoryResponse.errorBody());
                    }
                    else {
                        return Response.error(categoryResponse.errorBody(), categoryResponse.raw());
                    }
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
}
