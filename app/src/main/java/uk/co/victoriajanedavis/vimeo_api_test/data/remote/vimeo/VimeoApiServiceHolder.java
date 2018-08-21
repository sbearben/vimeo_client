package uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo;

import javax.inject.Inject;

import io.reactivex.annotations.Nullable;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationScope;

@ApplicationScope
public class VimeoApiServiceHolder {

    private VimeoApiService mApiService;


    @Inject
    public VimeoApiServiceHolder() {}

    @Nullable
    public VimeoApiService getVimeoApiService() {
        return mApiService;
    }

    public void setVimeoApiService (VimeoApiService apiService) {
        mApiService = apiService;
    }
}
