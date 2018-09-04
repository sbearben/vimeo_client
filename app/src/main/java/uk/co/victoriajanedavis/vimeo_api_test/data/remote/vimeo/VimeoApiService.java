package uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoAccessToken;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCategory;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoChannel;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoComment;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;

public interface VimeoApiService {

    /* NOTE: possible values for the following queries:
     * ALSO: any method with a GET Uri that involves "me" requires a token with "private" scope (a user needs to be signed in obviously)
     *       @Query("direction"): asc, desc
     *       @Query("sort"): alphabetical, date (some have more than this)
     *       @Query("weak_search"): Whether to include private videos in the search.
     *       @Query("filter"): app_only, embeddable, featured, playable
     *       @Query("type"): appears, category_featured, channel, facebook_feed, following, group,
     *                       likes, ondemand_publish, share, tagged_with, twitter_timeline, uploads
     */

    @POST("oauth/authorize/client?grant_type=client_credentials")
    Observable<VimeoAccessToken> getUnauthenticatedToken (@Header("Authorization") String basic_auth,
                                                          @Query("scope") String scope);

    @POST("oauth/access_token?grant_type=authorization_code")
    @FormUrlEncoded
    Observable<VimeoAccessToken> getAuthenticatedToken (@Header("Authorization") String basic_auth,
                                                        @Field("code") String code,
                                                        @Field("redirect_uri") String redirect_uri);

    @GET("channels/staffpicks/videos")
    Observable<Response<VimeoCollection<VimeoVideo>>> getStaffPicksVideos (@Query("page") Integer page,
                                                                           @Query("per_page") Integer per_page);

    @GET("categories")
    Observable<Response<VimeoCollection<VimeoCategory>>> getCategories (@Query("page") Integer page,
                                                                        @Query("per_page") Integer per_page);

    @GET("videos")
    Observable<Response<VimeoCollection<VimeoVideo>>> searchVideos (@Query("query") String query,
                                                                    @Query("page") Integer page,
                                                                    @Query("per_page") Integer per_page);

    @GET
    Observable<Response<VimeoCollection<VimeoVideo>>> getVideoList (@Url String url,
                                                                    @Query("query") String query,
                                                                    @Query("page") Integer page,
                                                                    @Query("per_page") Integer per_page);

    @GET("me/feed") // TODO: might need to delete offset Query (see: https://developer.vimeo.com/api/reference/users#GET/users/{user_id}/feed)
    Observable<Response<VimeoCollection<VimeoVideo>>> getMyVideoFeed (@Query("offset") Integer offset,
                                                                      @Query("page") Integer page,
                                                                      @Query("per_page") Integer per_page,
                                                                      @Query("type") List<String> type);

    @GET("me/videos") // TODO: if needed can add 'sort, 'search', and 'direction' query here
    Observable<Response<VimeoCollection<VimeoVideo>>> getMyVideos (@Query("query") String query,
                                                                   @Query("page") Integer page,
                                                                   @Query("per_page") Integer per_page,
                                                                   @Query("filter") List<String> filter,
                                                                   @Query("weak_search") Boolean weak_search);

    @GET("users/{id}/videos") // TODO: if needed can add 'sort', 'search', and 'direction' query here
    Observable<Response<VimeoCollection<VimeoVideo>>> getUsersVideos (@Path("id") long user_id,
                                                                      @Query("page") Integer page,
                                                                      @Query("per_page") Integer per_page,
                                                                      @Query("filter") List<String> filter,
                                                                      @Query("weak_search") Boolean weak_search);

    @GET("me")
    Observable<Response<VimeoUser>> getMyProfile();

    @GET("me/followers") // TODO: can add 'direction', 'query', and 'sort' queries here if needed
    Observable<Response<VimeoCollection<VimeoUser>>> getMyFollowers (@Query("page") Integer page,
                                                                     @Query("per_page") Integer per_page);

    @GET("users/{id}/followers") // TODO: can add 'direction', 'query', and 'sort' queries here if needed
    Observable<Response<VimeoCollection<VimeoUser>>> getUsersFollowers (@Path("id") long user_id,
                                                                        @Query("page") Integer page,
                                                                        @Query("per_page") Integer per_page);

    @GET("me/following") // TODO: can add 'direction', 'query', and 'sort' queries here if needed
    Observable<Response<VimeoCollection<VimeoUser>>> getMyFollowing (@Query("page") Integer page,
                                                                     @Query("per_page") Integer per_page);

    @GET("users/{id}/following") // TODO: can add 'direction', 'query', and 'sort' queries here if needed
    Observable<Response<VimeoCollection<VimeoUser>>> getUsersFollowing (@Path("id") long user_id,
                                                                        @Query("page") Integer page,
                                                                        @Query("per_page") Integer per_page);

    @GET("videos/{id}/comments")
    Observable<Response<VimeoCollection<VimeoComment>>> getVideoComments (@Path("id") long video_id,
                                                                          @Query("direction") String direction,
                                                                          @Query("page") Integer page,
                                                                          @Query("per_page") Integer per_page);

    @GET
    Observable<Response<VimeoCollection<VimeoComment>>> getComments (@Url String url,
                                                                     @Query("direction") String direction,
                                                                     @Query("page") Integer page,
                                                                     @Query("per_page") Integer per_page);

    @GET("videos/{id}/likes")
    Observable<Response<VimeoCollection<VimeoUser>>> getVideoLikes (@Path("id") long video_id,
                                                                    @Query("direction") String direction,
                                                                    @Query("page") Integer page,
                                                                    @Query("per_page") Integer per_page,
                                                                    @Query("sort") String sort);

    @GET
    Observable<Response<VimeoCollection<VimeoUser>>> getLikes (@Url String url,
                                                               @Query("direction") String direction,
                                                               @Query("page") Integer page,
                                                               @Query("per_page") Integer per_page,
                                                               @Query("sort") String sort);

    @GET
    Observable<Response<VimeoCollection<VimeoChannel>>> searchChannels (@Url String url,
                                                                        @Query("query") String query,
                                                                        @Query("page") Integer page,
                                                                        @Query("per_page") Integer per_page);

    @GET
    Observable<Response<VimeoCollection<VimeoUser>>> searchUsers (@Url String url,
                                                                  @Query("query") String query,
                                                                  @Query("page") Integer page,
                                                                  @Query("per_page") Integer per_page);

    @PUT("me/following/{user_id}")
    Single<Response<Void>> followUser(@Path("user_id") long user_id);

    @DELETE("me/following/{user_id}")
    Single<Response<Void>> unfollowUser(@Path("user_id") long user_id);

    @PUT("me/channels/{channel_id}")
    Single<Response<Void>> subscribeToChannel(@Path("channel_id") long channel_id);

    @DELETE("me/channels/{channel_id}")
    Single<Response<Void>> unsubscribeFromChannel(@Path("channel_id") long channel_id);

    @POST("videos/{video_id}/comments")
    @FormUrlEncoded
    Single<VimeoComment> postCommentOnVideo(@Path("video_id") long video_id,
                                   @Field("text") String comment);

    @POST("videos/{video_id}/comments/{comment_id}/replies")
    @FormUrlEncoded
    Single<VimeoComment> postReplyToCommentOnVideo(@Path("video_id") long video_id,
                                          @Path("comment_id") long comment_id,
                                          @Field("text") String reply);

}
