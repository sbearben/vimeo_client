package uk.co.victoriajanedavis.vimeo_api_test.util.eventbus;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationScope;

/**
 * A simple event bus built with RxJava
 */
@ApplicationScope
public class RxPublishEventBus implements RxEventBus {

    private final BackpressureStrategy mBackpressureStrategy = BackpressureStrategy.BUFFER;
    private final PublishRelay<Object> mBusSubject;

    @Inject
    public RxPublishEventBus() {
        mBusSubject = PublishRelay.create();
    }

    /**
     * Posts an object (usually an Event) to the bus
     */
    @Override
    public void post(Object event) {
        mBusSubject.accept(event);
    }

    /**
     * Observable that will emmit everything posted to the event bus.
     */
    @Override
    public Flowable<Object> observable() {
        return mBusSubject.toFlowable(mBackpressureStrategy);
    }

    /**
     * Observable that only emits events of a specific class.
     * Use this if you only want to subscribe to one type of events.
     */
    @Override
    public <T> Flowable<T> filteredObservable(final Class<T> eventClass) {
        return mBusSubject.ofType(eventClass).toFlowable(mBackpressureStrategy);
    }

}
