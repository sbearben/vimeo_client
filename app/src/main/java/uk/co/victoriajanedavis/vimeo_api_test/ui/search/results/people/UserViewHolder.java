package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.people;

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
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.otheruser.OtherUserActivity;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class UserViewHolder extends ListItemViewHolder<VimeoUser> implements View.OnClickListener {

    private static final String TAG = "UserViewHolder";

    @BindView(R.id.item_user_imageview) AppCompatImageView mImageView;
    @BindView(R.id.item_user_name_textview) TextView mNameTextView;
    @BindView(R.id.item_user_videosfollowers_textview) TextView mVideosFollowersTextView;


    public UserViewHolder(Context context, BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        super (context, baseFragment, inflater.inflate (R.layout.item_user, parent, false));
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener (this);
    }

    @Override
    public void bind (@NonNull VimeoUser vimeoUser) {
        mListItem = vimeoUser;

        mNameTextView.setText(mListItem.getName());

        String videoCountAndFollowers = VimeoApiServiceUtil.formatVideoCountAndFollowers(mListItem.getMetadata().getVideosConnection().getTotal(),
                mListItem.getMetadata().getFollowersConnection().getTotal());
        mVideosFollowersTextView.setText(videoCountAndFollowers);

        GlideApp.with(mBaseFragment)
                .load(mListItem.getPictures().getSizesList().get(2).getLink())
                .placeholder(R.drawable.user_image_placeholder)
                .fallback(R.drawable.user_image_placeholder)
                .circleCrop()
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
        Intent intent = OtherUserActivity.newIntent(mContext, mListItem);
        mContext.startActivity(intent);
    }
}
