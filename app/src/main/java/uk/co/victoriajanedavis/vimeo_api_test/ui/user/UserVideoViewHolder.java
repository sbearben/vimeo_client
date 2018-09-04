package uk.co.victoriajanedavis.vimeo_api_test.ui.user;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class UserVideoViewHolder extends ListItemViewHolder<VimeoVideo> implements View.OnClickListener {

    @BindView(R.id.user_video_imageview) AppCompatImageView mImageView;
    @BindView(R.id.user_video_title_textview) TextView mTitleTextView;
    @BindView(R.id.user_video_user_textview) TextView mUserTextView;
    @BindView(R.id.user_video_plays_textview) TextView mPlaysAgeTextView;
    @BindView(R.id.user_video_timelength_textview) TextView mTimeTextView;


    public UserVideoViewHolder(BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        super (baseFragment, inflater.inflate (R.layout.item_user_video, parent, false));
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);
    }

    @Override
    public void bind (@NonNull VimeoVideo vimeoVideo) {
        mListItem = vimeoVideo;

        mTitleTextView.setText(mListItem.getName());
        mUserTextView.setText(mListItem.getUser().getName());

        String videoAgeAndPlays = VimeoApiServiceUtil.formatVideoAgeAndPlays(mListItem.getStats().getPlays(), mListItem.getCreatedTime());
        mPlaysAgeTextView.setText(videoAgeAndPlays);

        mTimeTextView.setText(VimeoApiServiceUtil.formatSecondsToDuration(mListItem.getDurationSeconds()));
        GlideApp.with(mBaseFragment)
                .load(mListItem.getPictures().getSizesList().get(0).getLink()) // get(1) since when looking at Json response it appears this is the location of the most reasonable size
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
    }

    @Override
    public void onClick (View view) {
        Intent intent = VideoActivity.newIntent(mBaseFragment.getContext(), mListItem);
        mBaseFragment.getContext().startActivity(intent);
    }
}
