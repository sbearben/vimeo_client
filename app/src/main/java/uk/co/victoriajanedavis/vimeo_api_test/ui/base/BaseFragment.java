package uk.co.victoriajanedavis.vimeo_api_test.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LongSparseArray;
import android.util.Log;

import java.util.concurrent.atomic.AtomicLong;

import timber.log.Timber;
import uk.co.victoriajanedavis.vimeo_api_test.VimeoApplication;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoAccessToken;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoAccessTokenHolder;
import uk.co.victoriajanedavis.vimeo_api_test.injection.component.ConfigPersistentComponent;
import uk.co.victoriajanedavis.vimeo_api_test.injection.component.DaggerConfigPersistentComponent;
import uk.co.victoriajanedavis.vimeo_api_test.injection.component.FragmentComponent;
import uk.co.victoriajanedavis.vimeo_api_test.injection.module.FragmentModule;

/**
 * Abstract Fragment that every other Fragment in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */

/* This Dagger ConfgPersistentComponent logic for retaining the presenter across configuartion changes
 * is from the Ribot MVP boilerplate app: https://github.com/ribot/android-boilerplate
 */
public class BaseFragment extends Fragment {

    private static final String KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final LongSparseArray<ConfigPersistentComponent>
            sComponentsMap = new LongSparseArray<>();

    private FragmentComponent mFragmentComponent;
    private long mFragmentId;

    private VimeoAccessTokenHolder mTokenHolder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the FragmentComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mFragmentId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_FRAGMENT_ID) : NEXT_ID.getAndIncrement();

        ConfigPersistentComponent configPersistentComponent = sComponentsMap.get(mFragmentId, null);

        if (configPersistentComponent == null) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", mFragmentId);
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(VimeoApplication.get(getContext()).getComponent())
                    .build();
            sComponentsMap.put(mFragmentId, configPersistentComponent);
        }
        mFragmentComponent = configPersistentComponent.fragmentComponent(new FragmentModule(this));
        mTokenHolder = VimeoApplication.get(getContext()).getComponent().vimeoAccessTokenHolder();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_FRAGMENT_ID, mFragmentId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO: this is for LeakCanary
        VimeoApplication.get(getContext()).mustDie(this);
    }

    @Override
    public void onDetach() {
        if (!((Activity) getContext()).isChangingConfigurations()) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", mFragmentId);
            Timber.i("ConfigPersistentComponent toString: " + sComponentsMap.get(mFragmentId).toString());
            sComponentsMap.remove(mFragmentId);
        }
        super.onDetach();
    }

    public FragmentComponent fragmentComponent() {
        return mFragmentComponent;
    }


    public boolean isUserLoggedIn() {
        return mTokenHolder.getVimeoAccessToken() != null &&
                mTokenHolder.getVimeoAccessToken().getTokenAuthLevel().equals(VimeoAccessToken.TOKEN_LEVEL_AUTHENTICATED);
    }
}
