package uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import uk.co.victoriajanedavis.vimeo_api_test.BuildConfig;
import uk.co.victoriajanedavis.vimeo_api_test.data.local.PreferencesHelper;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoAccessToken;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoAccessTokenHolder;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationScope;
import uk.co.victoriajanedavis.vimeo_api_test.util.HttpUtil;

@ApplicationScope
public class VimeoServiceAuthenticator implements Authenticator {

    private static final String TAG = "VimeoAuthenticator";

    private VimeoApiServiceHolder mApiServiceHolder;
    private VimeoAccessTokenHolder mAccessTokenHolder;
    private PreferencesHelper mPrefHelper;
    private VimeoAccessToken newAccessToken;


    @Inject
    public VimeoServiceAuthenticator (@NonNull VimeoApiServiceHolder apiServiceHolder,
                                      @NonNull VimeoAccessTokenHolder accessTokenHolder,
                                      @NonNull PreferencesHelper prefHelper) {
        mApiServiceHolder = apiServiceHolder;
        mAccessTokenHolder = accessTokenHolder;
        mPrefHelper = prefHelper;
        //mDisposables = new CompositeDisposable();
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        Log.i(TAG, "calling authenticator - responseCount: " + responseCount(response));
        // If we’ve failed 2 times, give up.
        if (responseCount(response) >= 2) {
            return null;
        }

        // Make sure neither the vimeoApiService nor the accessToken are null
        if (mApiServiceHolder.getVimeoApiService() == null || mAccessTokenHolder.getVimeoAccessToken() == null) {
            /* We cannot answer the challenge as no token service is available
               - return null as per contract of Retrofit Authenticator interface for when unable to contest a challenge */
            return null;
        }

        /* Check if the current accessToken is Authenticated - if it is and this Authenticator is being run then we know
           the Authenticated access token is no longer valid and the user needs to login again */
        if (mAccessTokenHolder.getVimeoAccessToken().getTokenAuthLevel().equals(VimeoAccessToken.TOKEN_LEVEL_AUTHENTICATED)) {
            // TODO: We need to log user out and throw up some sort of error dialog to the user that indicates they need to log back in
            return null;
        }

        // If we've made it this far we know we need an Unauthenticated access token - so we get our apiService in order to retrieve one
        VimeoApiService apiService = mApiServiceHolder.getVimeoApiService();
        Request newRequest = null;

        // Get the new Unauthenticated access token
        apiService.getUnauthenticatedToken(HttpUtil.basicAuthorizationHeader(
                BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET), null)
                .blockingSubscribe(new Observer<VimeoAccessToken>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(VimeoAccessToken vimeoAccessToken) {
                        newAccessToken = vimeoAccessToken;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "We hit onError()");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "We hit onComplete()");
                    }
                });

        if (newAccessToken != null) {
            newAccessToken.setTokenAuthLevel(VimeoAccessToken.TOKEN_LEVEL_UNAUTHENTICATED); // need to set this since it isn't set automatically
            newRequest = response.request().newBuilder()
                    .header("Authorization", newAccessToken.getAuthorizationHeader())
                    .build();
            // We need to save the newAccessToken in SharedPref
            mPrefHelper.setAccessToken(newAccessToken);
            mAccessTokenHolder.setVimeoAccessToken(newAccessToken);

        } else {
            // TODO: We need to throw up some sort of error dialog to the user that indicates we couldn't get a Unauthenticated token
        }

        return newRequest;
    }

    private int responseCount (Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}
