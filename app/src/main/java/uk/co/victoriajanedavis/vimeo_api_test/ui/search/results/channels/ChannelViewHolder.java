package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.channels;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.schedulers.Schedulers;
import uk.co.victoriajanedavis.vimeo_api_test.GlideApp;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoChannel;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowToggleButton;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowUiModel;
import uk.co.victoriajanedavis.vimeo_api_test.ui.channel.ChannelActivity;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class ChannelViewHolder extends FollowButtonViewHolder<VimeoChannel> implements View.OnClickListener {

    private static final String TAG = "ChannelViewHolder";

    @BindView(R.id.item_channel_imageview) AppCompatImageView mImageView;
    @BindView(R.id.item_channel_name_textview) TextView mNameTextView;
    @BindView(R.id.item_channel_videosfollowers_textview) TextView mVideosFollowersTextView;
    @BindView(R.id.item_channel_follow_button_layout) FollowToggleButton mFollowButton;

    private ViewGroup mParent;


    public ChannelViewHolder(Context context, BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        super (context, baseFragment, inflater.inflate (R.layout.item_channel, parent, false));
        ButterKnife.bind(this, itemView);

        mParent = parent;
        itemView.setOnClickListener(this);
    }

    @Override
    public void bind (@NonNull VimeoChannel vimeoChannel) {
        mListItem = vimeoChannel;

        if (mListItem.getMetadata().getFollowInteraction() != null) {
            mFollowButton.setVisibility(View.VISIBLE);
            mFollowButton.setChecked(mListItem.getMetadata().getFollowInteraction().isAdded());
            mOriginalState = mFollowButton.isChecked();
        }

        mDisposable = RxCompoundButton.checkedChanges(mFollowButton)
                .skipInitialValue()
                .takeUntil(RxView.detaches(mParent))
                .switchMapSingle(isChecked -> {
                    mListItem.getMetadata().getFollowInteraction().setAdded(isChecked);
                    if (isChecked) {
                        return mFollowButtonClickListener.onFollowButtonClick(mListItem.getId())
                                .subscribeOn(Schedulers.io())
                                .doOnDispose(() -> Log.d(TAG, "Follow DISPOSED -> channelId: " + mListItem.getId()));
                    }
                    else {
                        return mFollowButtonClickListener.onUnfollowButtonClick(mListItem.getId())
                                .subscribeOn(Schedulers.io())
                                .doOnDispose(() -> Log.d(TAG, "Unfollow DISPOSED -> channelId: " + mListItem.getId()));
                    }
                })
                    .map(voidResponse -> {
                        switch(VimeoApiServiceUtil.responseType(voidResponse)) {
                            case VimeoApiServiceUtil.RESPONSE_OK:
                                return FollowUiModel.success(mListItem.getId(), mFollowButton.isChecked());
                            case VimeoApiServiceUtil.RESPONSE_ERROR:
                            case VimeoApiServiceUtil.RESPONSE_UNAUTHORIZED:
                            default:
                                return FollowUiModel.failure(mListItem.getId(), mFollowButton.isChecked(), voidResponse.message());
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(FollowUiModel.inProgress(mListItem.getId(), mFollowButton.isChecked()))
                .subscribe(model -> {
                    if (model.isInProgress()) {
                        //Log.d (TAG, "this is called");
                    }
                    else {
                        if (model.isSuccess()) {
                            mOriginalState = model.isFollowing();
                        }
                        else {
                            mListItem.getMetadata().getFollowInteraction().setAdded(mOriginalState);
                            mFollowButton.setChecked(mOriginalState, false);
                            //Toast.makeText(mContext, "Failed to ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, t -> { throw new OnErrorNotImplementedException(t); });

        mNameTextView.setText(mListItem.getName());

        String videoCountAndFollowers = VimeoApiServiceUtil.formatVideoCountAndFollowers(mListItem.getMetadata().getVideosConnection().getTotal(),
                mListItem.getMetadata().getUsersConnection().getTotal());
        mVideosFollowersTextView.setText(videoCountAndFollowers);

        GlideApp.with(mBaseFragment)
                .load(mListItem.getHeader().getSizesList().get(0).getLink())
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
        mDisposable.dispose();
    }

    @Override
    public void onClick (View view) {
        Intent intent = ChannelActivity.newIntent(mContext, mListItem);
        mContext.startActivity(intent);
    }
}
