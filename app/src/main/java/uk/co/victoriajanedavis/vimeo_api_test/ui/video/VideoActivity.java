package uk.co.victoriajanedavis.vimeo_api_test.ui.video;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.SingleFragmentActivity;

public class VideoActivity extends SingleFragmentActivity {

    private static final String EXTRA_VIMEO_VIDEO = "uk.co.victoriajanedavis.vimeo_api_test.vimeo_video";


    public static Intent newIntent (Context packageContext, VimeoVideo video) {
        Intent intent = new Intent(packageContext, VideoActivity.class);
        intent.putExtra(EXTRA_VIMEO_VIDEO, video);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        VimeoVideo video = (VimeoVideo) getIntent().getParcelableExtra(EXTRA_VIMEO_VIDEO);
        return VideoFragment.newInstance(video);
    }

    @Override
    protected String getFragmentTag() {
        return VideoFragment.FRAGMENT_VIDEO_TAG;
    }
}
