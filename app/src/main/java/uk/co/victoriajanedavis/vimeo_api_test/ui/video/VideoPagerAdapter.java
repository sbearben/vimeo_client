package uk.co.victoriajanedavis.vimeo_api_test.ui.video;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoMetadataVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.comments.CommentsFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.likes.LikesFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.upnext.UpNextFragment;

public class VideoPagerAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_TABS = 3;

    private Context mContext;
    private VimeoMetadataVideo mVideoMetadata;


    public VideoPagerAdapter(Context context, FragmentManager fm, VimeoMetadataVideo videoMetadata) {
        super(fm);
        mContext = context;
        mVideoMetadata = videoMetadata;
    }

    @Override
    public Fragment getItem (int position) {
        switch (position) {
            case 0:
                return UpNextFragment.newInstance(mVideoMetadata.getRecommendationsConnection());
            case 1:
                return CommentsFragment.newInstance(mVideoMetadata.getCommentsConnection());
            case 2:
                return LikesFragment.newInstance(mVideoMetadata.getLikesConnection());
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.video_pager_adapter_upnext);
            case 1:
                return mContext.getResources().getQuantityString(R.plurals.video_pager_adapter_comments_plural,
                        mVideoMetadata.getCommentsConnection().getTotal(), mVideoMetadata.getCommentsConnection().getTotal());
            case 2:
                return mContext.getResources().getQuantityString(R.plurals.video_pager_adapter_likes_plural,
                        mVideoMetadata.getLikesConnection().getTotal(), mVideoMetadata.getLikesConnection().getTotal());
            default:
                return null;
        }
    }

    public void setVideoMetadata (VimeoMetadataVideo metadata) {
        mVideoMetadata = metadata;
        notifyDataSetChanged();
    }
}
