package uk.co.victoriajanedavis.vimeo_api_test.ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.victoriajanedavis.vimeo_api_test.GlideApp;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.VideoActivity;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class VideoFeedViewHolder extends ListItemViewHolder<VimeoVideo> implements View.OnClickListener {

    @BindView(R.id.video_feed_imageview) AppCompatImageView mImageView;
    @BindView(R.id.video_feed_title_textview) TextView mTitleTextView;
    @BindView(R.id.video_feed_user_textview) TextView mUserTextView;
    @BindView(R.id.video_feed_likes_textview) TextView mLikesTextView;
    @BindView(R.id.video_feed_timelength_textview) TextView mTimeTextView;


    public VideoFeedViewHolder (BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        super (baseFragment, inflater.inflate (R.layout.item_video_feed, parent, false));
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener (this);
    }

    @Override
    public void bind (@NonNull VimeoVideo vimeoVideo) {
        mListItem = vimeoVideo;

        mTitleTextView.setText(mListItem.getName());
        mUserTextView.setText(mListItem.getUser().getName());

        int totalLikes = mListItem.getMetadata().getLikesConnection().getTotal();
        mLikesTextView.setText(itemView.getResources().getQuantityString(R.plurals.video_feed_likes_plural, totalLikes, totalLikes));

        mTimeTextView.setText(VimeoApiServiceUtil.formatSecondsToDuration(mListItem.getDurationSeconds()));
        GlideApp.with(mBaseFragment)
                .load(mListItem.getPictures().getSizesList().get(2).getLink()) // get(2) since when looking at Json response it appears this is the location of the most reasonable size
                //.thumbnail(Glide.with(itemView.getContext())
                        //.load(mListItem.getPictures().getSizesList().get(0).getLink()))
                .placeholder(R.drawable.video_image_placeholder)
                .fallback(R.drawable.video_image_placeholder)
                .fitCenter()
                .transition(withCrossFade())
                .into(mImageView);
    }

    @Override
    public void recycle() {
        Glide.with(mBaseFragment)
                .clear(mImageView);
        mImageView.setImageDrawable(null);
    }

    @Override
    public void onClick (View view) {
        Intent intent = VideoActivity.newIntent(mBaseFragment.getContext(), mListItem);
        mBaseFragment.getContext().startActivity(intent);
    }
}
