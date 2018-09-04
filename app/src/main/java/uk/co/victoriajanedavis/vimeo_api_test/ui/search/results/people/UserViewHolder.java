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
import butterknife.OnCheckedChanged;
import uk.co.victoriajanedavis.vimeo_api_test.GlideApp;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.otheruser.OtherUserActivity;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.otheruser.OtherUserFragment;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class UserViewHolder extends FollowButtonViewHolder<VimeoUser> implements View.OnClickListener {

    private static final String TAG = "UserViewHolder";

    @BindView(R.id.item_user_imageview) AppCompatImageView mImageView;
    @BindView(R.id.item_user_name_textview) TextView mNameTextView;
    @BindView(R.id.item_user_videosfollowers_textview) TextView mVideosFollowersTextView;
    //@BindView(R.id.item_user_follow_button_layout) FollowToggleButton mFollowButton;


    public UserViewHolder(BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        super (baseFragment, inflater.inflate (R.layout.item_user, parent, false));

        itemView.setOnClickListener (this);
    }

    @Override
    public void bind (@NonNull VimeoUser vimeoUser) {
        mListItem = vimeoUser;

        mFollowButtonRxBinding.setFollowButton(mFollowButton);
        mFollowButtonRxBinding.setFollowableItem(mListItem);
        mDisposable = setUpFollowButtonRxBindingStream();

        mNameTextView.setText(mListItem.getName());

        String videoCountAndFollowers = VimeoApiServiceUtil.formatVideoCountAndFollowers(mListItem.getMetadata().getVideosConnection().getTotal(),
                mListItem.getMetadata().getFollowersConnection().getTotal());
        mVideosFollowersTextView.setText(videoCountAndFollowers);

        GlideApp.with(mBaseFragment)
                .load(mListItem.getPictures().getSizesList().get(1).getLink())
                .placeholder(R.drawable.user_image_placeholder)
                .fallback(R.drawable.user_image_placeholder)
                .circleCrop()
                .transition(withCrossFade())
                .into(mImageView);
    }

    /*
    @OnCheckedChanged(R.id.item_follow_button_layout)
    public void onFollowCheckChanged() {
        if (mDisposable == null) {
            mDisposable = setUpFollowButtonRxBindingStream();
        }
    }
    */

    @Override
    public void recycle() {
        Glide.with(mBaseFragment)
                .clear(mImageView);
        mImageView.setImageDrawable(null);

        mDisposable.dispose();
        mDisposable = null;
    }

    @Override
    public void releaseInternalStates() {
        super.releaseInternalStates();
        //mFollowButtonRxBinding.releaseInternalStates();
    }

    @Override
    public void onClick (View view) {
        //Intent intent = OtherUserActivity.newIntent(mBaseFragment.getContext(), mListItem);
        //mBaseFragment.getContext().startActivity(intent);

        Intent intent = OtherUserActivity.newIntent(mBaseFragment.getContext(), mListItem, getLayoutPosition());
        mBaseFragment.startActivityForResult(intent, OtherUserFragment.REQUEST_USER);
    }
}
