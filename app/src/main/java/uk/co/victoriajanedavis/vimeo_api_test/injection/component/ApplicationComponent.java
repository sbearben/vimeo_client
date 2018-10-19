package uk.co.victoriajanedavis.vimeo_api_test.injection.component;

import android.app.Application;
import android.content.Context;

import dagger.Component;
import uk.co.victoriajanedavis.vimeo_api_test.data.DataManager;
import uk.co.victoriajanedavis.vimeo_api_test.data.local.PreferencesHelper;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoAccessTokenHolder;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationContext;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationScope;
import uk.co.victoriajanedavis.vimeo_api_test.injection.module.ApplicationModule;
import uk.co.victoriajanedavis.vimeo_api_test.injection.module.VimeoServiceModule;
import uk.co.victoriajanedavis.vimeo_api_test.util.eventbus.RxBehaviourEventBus;
import uk.co.victoriajanedavis.vimeo_api_test.util.eventbus.RxPublishEventBus;

@ApplicationScope
@Component(modules = {ApplicationModule.class, VimeoServiceModule.class})
public interface ApplicationComponent {

    @ApplicationContext Context context();
    Application application();
    PreferencesHelper preferencesHelper();
    DataManager dataManager();
    RxBehaviourEventBus behaviourEventBus();
    RxPublishEventBus publishEventBus();
    VimeoAccessTokenHolder vimeoAccessTokenHolder();
}
