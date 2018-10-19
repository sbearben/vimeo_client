package uk.co.victoriajanedavis.vimeo_api_test.injection.component;

import dagger.Component;
import dagger.Subcomponent;
import uk.co.victoriajanedavis.vimeo_api_test.injection.PerActivity;
import uk.co.victoriajanedavis.vimeo_api_test.injection.module.ActivityModule;
import uk.co.victoriajanedavis.vimeo_api_test.ui.MainActivity;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.SearchActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(modules = ActivityModule.class, dependencies = ApplicationComponent.class)
public interface ActivityComponent {

    void inject(SearchActivity searchActivity);
}
