package uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VimeoApiServiceGenerator {

    public static final String  API_URL_ENDPOINT = "https://api.vimeo.com/";


    private static Retrofit.Builder mBuilder = new Retrofit.Builder()
            .baseUrl(API_URL_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit mRetrofit = mBuilder.build();

    private static HttpLoggingInterceptor mLogging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder mHttpClient = new OkHttpClient.Builder();


    public static <S> S createService (Class<S> serviceClass) {
        if (!mHttpClient.interceptors().contains(mLogging)) {
            mHttpClient.addInterceptor(mLogging);
            mBuilder.client(mHttpClient.build());
            mRetrofit = mBuilder.build();
        }
        return mRetrofit.create(serviceClass);
    }
}
