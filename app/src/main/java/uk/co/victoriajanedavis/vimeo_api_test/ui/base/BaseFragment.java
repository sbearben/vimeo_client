package uk.co.victoriajanedavis.vimeo_api_test.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AppCompatActivity;
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
public class BaseFragment extends Fragment {

    private static final String KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final LongSparseArray<ConfigPersistentComponent>
            sComponentsMap = new LongSparseArray<>();

    private Context mContext;
    private AppCompatActivity mActivity;
    private FragmentComponent mFragmentComponent;
    private long mFragmentId;

    private VimeoAccessTokenHolder mTokenHolder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        Log.d (this.getClass().getSimpleName(), "onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d (this.getClass().getSimpleName(), "onCreate()");

        // Create the FragmentComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mFragmentId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_FRAGMENT_ID) : NEXT_ID.getAndIncrement();

        ConfigPersistentComponent configPersistentComponent = sComponentsMap.get(mFragmentId, null);

        if (configPersistentComponent == null) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", mFragmentId);
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(VimeoApplication.get(mContext).getComponent())
                    .build();
            sComponentsMap.put(mFragmentId, configPersistentComponent);
        }
        mFragmentComponent = configPersistentComponent.fragmentComponent(new FragmentModule(this));
        mTokenHolder = VimeoApplication.get(mContext).getComponent().vimeoAccessTokenHolder();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
        Log.d (this.getClass().getSimpleName(), "onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(this.getClass().getSimpleName(), "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(this.getClass().getSimpleName(), "onResume()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_FRAGMENT_ID, mFragmentId);
        Log.d(this.getClass().getSimpleName(), "onSaveInstanceState()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(this.getClass().getSimpleName(), "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(this.getClass().getSimpleName(), "onStop()");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(this.getClass().getSimpleName(), "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO: this is for LeakCanary
        VimeoApplication.get(mContext).mustDie(this);

        Log.d(this.getClass().getSimpleName(), "onDestroy()");
    }

    @Override
    public void onDetach() {
        if (!((Activity) mContext).isChangingConfigurations()) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", mFragmentId);
            sComponentsMap.remove(mFragmentId);
        }
        Log.d(this.getClass().getSimpleName(), "onDetach()");
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(this.getClass().getSimpleName(), "setUserVisibleHint(): " + isVisibleToUser);
    }

    public FragmentComponent fragmentComponent() {
        return mFragmentComponent;
    }

    public Context getContext() {
        return mContext;
    }

    public AppCompatActivity getAppCompatActivity() {
        return mActivity;
    }

    public boolean isUserLoggedIn() {
        return mTokenHolder.getVimeoAccessToken() != null &&
                mTokenHolder.getVimeoAccessToken().getTokenAuthLevel().equals(VimeoAccessToken.TOKEN_LEVEL_AUTHENTICATED);
    }
}
