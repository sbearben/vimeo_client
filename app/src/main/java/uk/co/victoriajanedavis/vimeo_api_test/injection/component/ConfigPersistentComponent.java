package uk.co.victoriajanedavis.vimeo_api_test.injection.component;

import dagger.Component;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ConfigPersistent;
import uk.co.victoriajanedavis.vimeo_api_test.injection.module.ActivityModule;
import uk.co.victoriajanedavis.vimeo_api_test.injection.module.FragmentModule;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

/**
 * A dagger component that will live during the lifecycle of a Fragment but it won't
 * be destroyed during configuration changes. Check {@link BaseFragment} to see how this components
 * survives configuration changes.
 * Use the {@link ConfigPersistent} scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */

/* This Dagger ConfgPersistentComponent logic for retaining the presenter across configuartion changes
 * is from the Ribot MVP boilerplate app: https://github.com/ribot/android-boilerplate
 */
@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    //ActivityComponent activityComponent(ActivityModule activityModule);

    /* I believe that this is what scopes the ConfigPersistentComponent to the Fragment lifecycle, since we're
     * passing a FragmentModule which contains a Fragment as its context
     * - this allows us to inject the presenter in the Fragment (and then we know it will be disposed when the Fragment is gone)
     * - The presenter is retained by holding onto the ConfigPersistentComponent for each Fragment in a static array in
     *   BaseFragment; the component is then removed when the Fragment is detached
     * - Also, these methods that return other components need to return SubComponents (so FragmentComponent
     *   and ActivityComponent above are both subcomponents of ConfigPersistentComponent I believe)
     */
    FragmentComponent fragmentComponent(FragmentModule fragmentModule);

}