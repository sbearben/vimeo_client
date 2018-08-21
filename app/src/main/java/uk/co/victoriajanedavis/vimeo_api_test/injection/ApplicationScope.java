package uk.co.victoriajanedavis.vimeo_api_test.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface ApplicationScope {
}
