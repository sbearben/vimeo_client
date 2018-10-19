package uk.co.victoriajanedavis.vimeo_api_test.injection.module;

import android.content.Context;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo.AuthenticationInterceptor;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo.VimeoServiceAuthenticator;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationScope;

@Module(includes = ApplicationModule.class)
public class NetworkModule {

    @Provides
    @ApplicationScope
    public OkHttpClient okHttpClient (HttpLoggingInterceptor loggingInterceptor,
                                      AuthenticationInterceptor authenticationInterceptor,
                                      VimeoServiceAuthenticator vimeoServiceAuthenticator) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authenticationInterceptor)
                .authenticator(vimeoServiceAuthenticator)
                .build();
    }

    @Provides
    @ApplicationScope
    public HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> {
            Timber.i(message);
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return interceptor;
    }

    /*
    @Provides
    @ApplicationScope
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1024 * 1024); //10 MB Cache
    }

    @Provides
    @ApplicationScope
    public File cacheFile(@ApplicationContext Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }
    */
}
