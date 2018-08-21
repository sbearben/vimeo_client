package uk.co.victoriajanedavis.vimeo_api_test.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import uk.co.victoriajanedavis.vimeo_api_test.R;

/**
 * Created by Armon on 15/03/2018.
 */

public abstract class SingleFragmentActivity extends BaseActivity {

    protected abstract Fragment createFragment();

    protected abstract String getFragmentTag();

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inflate the activity's view
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        // The following code (up until .commit()) gives the FragmentManager a fragment to manage
        Fragment fragment = fm.findFragmentById (R.id.activity_main_fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            // this code creates a new fragment transaction, includes one add operation in it,
            // and then commits it
            fm.beginTransaction()
                    .add(R.id.activity_main_fragment_container, fragment, getFragmentTag())
                    .commit();
        }
    }
}
