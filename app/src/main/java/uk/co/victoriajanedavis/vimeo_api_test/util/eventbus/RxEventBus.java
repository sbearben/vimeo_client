package uk.co.victoriajanedavis.vimeo_api_test.util.eventbus;

import io.reactivex.Flowable;

public interface RxEventBus {

    void post(Object event);

    Flowable<Object> observable();

    <T> Flowable<T> filteredObservable(final Class<T> eventClass);
}
