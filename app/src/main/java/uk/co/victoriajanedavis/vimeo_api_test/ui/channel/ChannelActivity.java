package uk.co.victoriajanedavis.vimeo_api_test.ui.channel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoChannel;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.SingleFragmentActivity;

public class ChannelActivity extends SingleFragmentActivity {

    private static final String EXTRA_VIMEO_CHANNEL = "uk.co.victoriajanedavis.vimeo_api_test.ChannelActivity.vimeo_channel";
    private static final String EXTRA_CHANNEL_POSITION = "uk.co.victoriajanedavis.vimeo_api_test.ChannelActivity.channel_position";


    public static Intent newIntent (Context packageContext, VimeoChannel channel, int position) {
        Intent intent = new Intent(packageContext, ChannelActivity.class);
        intent.putExtra(EXTRA_VIMEO_CHANNEL, channel);
        intent.putExtra(EXTRA_CHANNEL_POSITION, position);
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
            actionBar.setTitle("Channel");
        }
    }

    @Override
    protected Fragment createFragment() {
        VimeoChannel channel = (VimeoChannel) getIntent().getParcelableExtra(EXTRA_VIMEO_CHANNEL);
        int position = (int) getIntent().getIntExtra(EXTRA_CHANNEL_POSITION, -1);
        return ChannelFragment.newInstance(channel, position);
    }

    @Override
    protected String getFragmentTag() {
        return ChannelFragment.FRAGMENT_CHANNEL_TAG;
    }

    @Override
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment_toolbar;
    }
}
