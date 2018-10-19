package uk.co.victoriajanedavis.vimeo_api_test.util;

import android.util.Base64;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Response;

public class HttpUtil {

    public static final String RESPONSE_OK = "ok";
    public static final String RESPONSE_UNAUTHORIZED = "unauthorized";
    public static final String RESPONSE_ERROR = "error";


    public static String basicAuthorizationHeader (String clientId, String clientSecret) {
        String rawString = clientId + ":" + clientSecret;
        return "basic " + Base64.encodeToString(rawString.getBytes(), Base64.NO_WRAP);
    }

    public static String responseType (Response response) {
        switch (response.code()) {
            case HttpsURLConnection.HTTP_OK:
            case HttpsURLConnection.HTTP_CREATED:
            case HttpsURLConnection.HTTP_ACCEPTED:
            case HttpsURLConnection.HTTP_NO_CONTENT:
            case HttpsURLConnection.HTTP_NOT_AUTHORITATIVE:
                return RESPONSE_OK;

            case HttpsURLConnection.HTTP_UNAUTHORIZED:
                return RESPONSE_UNAUTHORIZED;

            default:
                return RESPONSE_ERROR;
        }
    }
}
