package uk.co.victoriajanedavis.vimeo_api_test.injection.component;

import dagger.Component;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.injection.module.ActivityModule;
import uk.co.victoriajanedavis.vimeo_api_test.injection.module.FragmentModule;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check {@link BaseFragment} to see how this components
 * survives configuration changes.
 * Use the {@link ConfigPersistent} scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);
    FragmentComponent fragmentComponent(FragmentModule fragmentModule);

}