package uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo;

import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoAccessToken;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoAccessTokenHolder;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationScope;

@ApplicationScope
public class AuthenticationInterceptor implements Interceptor {

    private static final String TAG = "AuthInterceptor";

    private VimeoAccessTokenHolder mAccessTokenHolder;


    @Inject
    public AuthenticationInterceptor (VimeoAccessTokenHolder accessTokenHolder) {
        mAccessTokenHolder = accessTokenHolder;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (mAccessTokenHolder.getVimeoAccessToken() == null) {
            throw new NullPointerException("Error: accessToken hasn't been set on AuthenticatorInterceptor");
        }
        VimeoAccessToken accessToken = mAccessTokenHolder.getVimeoAccessToken();
        Request original = chain.request();


        Log.i(TAG, accessToken.getAuthorizationHeader());

        /* If we already have an Authorization header, it's a basic Authorization header from when
           VimeoServiceAuthenticator tries to generate a basic token that we don't yet have. Therefore,
           if we continue to build a newRequest and add another Authorization header, accessToken.getAuthorizationHeader()
           will return "". Adding this header with the basic header from VimeoServiceAuthenticator will result
           in an http response of 400 Bad Request, so we have to check here if the Authorization header is already set,
           and continue as is if it is since we need to get the unauthenticated token.
         */
        if (original.headers("Authorization").size() > 0) {
            return chain.proceed(original);
        }

        // set or override the 'Authorization' header - keep the request body
        Request newRequest = original.newBuilder()
                .header("Authorization", accessToken.getAuthorizationHeader())
                .method(original.method(), original.body())
                .build();

        return chain.proceed(newRequest);
    }
}
