package uk.co.victoriajanedavis.vimeo_api_test.ui.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.VideoActivity;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoTextUtil;

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

        mTimeTextView.setText(VimeoTextUtil.formatSecondsToDuration(mListItem.getDurationSeconds()));
        GlideApp.with(mBaseFragment)
                .load(mListItem.getPictures().getSizesList().get(2).getLink())
                .placeholder(R.drawable.video_image_placeholder)
                .fallback(R.drawable.video_image_placeholder)
                .fitCenter()
                .into(mImageView);
    }

    public void setImageViewDimensions (int width, int height) {
        mImageView.setLayoutParams(new ConstraintLayout.LayoutParams(width, height));
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
