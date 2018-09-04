package uk.co.victoriajanedavis.vimeo_api_test.ui.reply;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoComment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.SingleFragmentActivity;

public class ReplyActivity extends SingleFragmentActivity {

    private static final String TAG = "ReplyActivity";

    private static final String EXTRA_VIDEO_ID = "uk.co.victoriajanedavis.vimeo_api_test.ReplyActivity.video_id";
    private static final String EXTRA_VIMEO_COMMENT = "uk.co.victoriajanedavis.vimeo_api_test.ReplyActivity.vimeo_comment";
    private static final String EXTRA_RESPONSE_TYPE = "uk.co.victoriajanedavis.vimeo_api_test.ReplyActivity.response_type";


    public static Intent newIntent (Context packageContext, long video_id, VimeoComment comment,
                                    @ReplyFragment.ResponseType String response_type) {
        Intent intent = new Intent(packageContext, ReplyActivity.class);
        intent.putExtra(EXTRA_VIDEO_ID, video_id);
        intent.putExtra(EXTRA_VIMEO_COMMENT, comment);
        intent.putExtra(EXTRA_RESPONSE_TYPE, response_type);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        long video_id = (long) getIntent().getLongExtra(EXTRA_VIDEO_ID, -1);
        VimeoComment comment = (VimeoComment) getIntent().getParcelableExtra(EXTRA_VIMEO_COMMENT);
        String response_type = (String) getIntent().getStringExtra(EXTRA_RESPONSE_TYPE);
        return ReplyFragment.newInstance(video_id, comment, response_type);
    }

    @Override
    protected String getFragmentTag() {
        return ReplyFragment.FRAGMENT_REPLY_TAG;
    }

    @Override
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }
}
