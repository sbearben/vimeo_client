package uk.co.victoriajanedavis.vimeo_api_test.injection.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationContext;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationScope;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }
}
