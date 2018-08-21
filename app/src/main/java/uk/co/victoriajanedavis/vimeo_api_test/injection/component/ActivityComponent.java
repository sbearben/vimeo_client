package uk.co.victoriajanedavis.vimeo_api_test.injection.component;

import dagger.Subcomponent;
import uk.co.victoriajanedavis.vimeo_api_test.injection.PerActivity;
import uk.co.victoriajanedavis.vimeo_api_test.injection.module.ActivityModule;
import uk.co.victoriajanedavis.vimeo_api_test.ui.MainActivity;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.SearchActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(SearchActivity mainActivity);

}
