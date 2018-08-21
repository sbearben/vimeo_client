package uk.co.victoriajanedavis.vimeo_api_test.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import uk.co.victoriajanedavis.vimeo_api_test.injection.component.ConfigPersistentComponent;

/**
 * A scoping annotation to permit dependencies conform to the life of the
 * {@link ConfigPersistentComponent}
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigPersistent {
}
