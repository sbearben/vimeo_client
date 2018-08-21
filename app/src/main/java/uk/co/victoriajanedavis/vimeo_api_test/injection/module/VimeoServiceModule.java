package uk.co.victoriajanedavis.vimeo_api_test.injection.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.victoriajanedavis.vimeo_api_test.data.local.PreferencesHelper;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoAccessToken;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoMetadataChannel;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoMetadataUser;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoMetadataVideo;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo.VimeoApiService;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo.VimeoApiServiceHolder;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo.VimeoMetadataChannelDeserializer;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo.VimeoMetadataUserDeserializer;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo.VimeoMetadataVideoDeserializer;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationScope;

@Module(includes = NetworkModule.class)
public class VimeoServiceModule {


    @Provides
    @ApplicationScope
    public VimeoApiService vimeoApiService (Retrofit vimeoRetrofit, VimeoApiServiceHolder apiServiceHolder) {
        VimeoApiService apiService = vimeoRetrofit.create(VimeoApiService.class);
        apiServiceHolder.setVimeoApiService (apiService);
        return apiService;
    }

    @Provides
    @ApplicationScope
    public Retrofit retrofit (OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://api.vimeo.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @ApplicationScope
    public Gson gson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .registerTypeHierarchyAdapter(VimeoMetadataUser.class, new VimeoMetadataUserDeserializer())
                .registerTypeHierarchyAdapter(VimeoMetadataVideo.class, new VimeoMetadataVideoDeserializer())
                .registerTypeHierarchyAdapter(VimeoMetadataChannel.class, new VimeoMetadataChannelDeserializer())
                .create();
    }

    // TODO: maybe remove this code and stick with loading the token and setting it on the AuthenticatorInterceptor in HomePresenter
    // TODO: or load it in the VimeoServiceAuthenticator itself since that class now has PreferenceHelper injected
    @Provides
    @ApplicationScope
    public VimeoAccessToken vimeoAccessToken (PreferencesHelper preferencesHelper) {
        return preferencesHelper.getAccessToken();
    }
}
