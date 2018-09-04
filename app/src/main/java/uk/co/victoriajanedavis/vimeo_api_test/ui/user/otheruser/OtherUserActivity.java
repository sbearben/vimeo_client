package uk.co.victoriajanedavis.vimeo_api_test.ui.user.otheruser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.SingleFragmentActivity;

public class OtherUserActivity extends SingleFragmentActivity {

    private static final String EXTRA_VIMEO_USER = "uk.co.victoriajanedavis.vimeo_api_test.vimeo_user";
    private static final String EXTRA_USER_POSITION = "uk.co.victoriajanedavis.vimeo_api_test.OtherUserActivity.user_position";


    public static Intent newIntent (Context packageContext, VimeoUser user, int position) {
        Intent intent = new Intent(packageContext, OtherUserActivity.class);
        intent.putExtra(EXTRA_VIMEO_USER, user);
        intent.putExtra(EXTRA_USER_POSITION, position);
        return intent;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Profile");
        }
    }

    @Override
    protected Fragment createFragment() {
        VimeoUser user = (VimeoUser) getIntent().getParcelableExtra(EXTRA_VIMEO_USER);
        int position = (int) getIntent().getIntExtra(EXTRA_USER_POSITION, -1);
        return OtherUserFragment.newInstance(user, position);
    }

    @Override
    protected String getFragmentTag() {
        return OtherUserFragment.FRAGMENT_OTHER_USER_TAG;
    }

    @Override
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment_toolbar;
    }
}
