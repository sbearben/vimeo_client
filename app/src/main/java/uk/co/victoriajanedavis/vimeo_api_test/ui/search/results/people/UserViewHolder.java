package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.people;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import uk.co.victoriajanedavis.vimeo_api_test.GlideApp;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.otheruser.OtherUserActivity;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.otheruser.OtherUserFragment;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoTextUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class UserViewHolder extends FollowButtonViewHolder<VimeoUser> implements View.OnClickListener {

    private static final String TAG = "UserViewHolder";

    @BindView(R.id.item_user_imageview) AppCompatImageView mImageView;
    @BindView(R.id.item_user_name_textview) TextView mNameTextView;
    @BindView(R.id.item_user_videosfollowers_textview) TextView mVideosFollowersTextView;


    public UserViewHolder(BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        super (baseFragment, inflater.inflate (R.layout.item_user, parent, false));

        itemView.setOnClickListener (this);
    }

    @Override
    public void bind (@NonNull VimeoUser vimeoUser) {
        Log.d (TAG, "Bind: " + getLayoutPosition());
        mListItem = vimeoUser;

        mFollowButtonRxBinding.setFollowableItem(mListItem);
        mDisposables.add(mFollowButtonRxBinding.subscribeToStream());

        mNameTextView.setText(mListItem.getName());

        mDisposables.add(Single.fromCallable(() -> VimeoTextUtil.formatVideoCountAndFollowers(mListItem.getMetadata().getVideosConnection().getTotal(),
                mListItem.getMetadata().getFollowersConnection().getTotal()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(str -> mVideosFollowersTextView.setText(str)));


        GlideApp.with(mBaseFragment)
                .load(mListItem.getPictures().getSizesList().get(1).getLink())
                .placeholder(R.drawable.user_image_placeholder)
                .fallback(R.drawable.user_image_placeholder)
                .circleCrop()
                .into(mImageView);
    }

    @Override
    public void recycle() {
        Log.d (TAG, "Recycled: " + getLayoutPosition());
        Glide.with(mBaseFragment)
                .clear(mImageView);
        mImageView.setImageDrawable(null);

        mDisposables.clear();
    }

    @Override
    public void onClick (View view) {
        Intent intent = OtherUserActivity.newIntent(mBaseFragment.getContext(), mListItem, getLayoutPosition());
        mBaseFragment.startActivityForResult(intent, OtherUserFragment.REQUEST_USER);
    }
}
