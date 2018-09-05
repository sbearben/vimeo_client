package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.channels;

import android.content.Context;
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
import butterknife.OnCheckedChanged;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import uk.co.victoriajanedavis.vimeo_api_test.GlideApp;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoChannel;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.channel.ChannelActivity;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class ChannelViewHolder extends FollowButtonViewHolder<VimeoChannel> implements View.OnClickListener {

    private static final String TAG = "ChannelViewHolder";

    @BindView(R.id.item_channel_imageview) AppCompatImageView mImageView;
    @BindView(R.id.item_channel_name_textview) TextView mNameTextView;
    @BindView(R.id.item_channel_videosfollowers_textview) TextView mVideosFollowersTextView;


    public ChannelViewHolder(BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        super (baseFragment, inflater.inflate (R.layout.item_channel, parent, false));

        itemView.setOnClickListener(this);
    }

    @Override
    public void bind (@NonNull VimeoChannel vimeoChannel) {
        mListItem = vimeoChannel;

        //mFollowButtonRxBinding.setFollowButton(mFollowButton);
        mFollowButtonRxBinding.setFollowableItem(mListItem);
        mDisposables.add(mFollowButtonRxBinding.subscribeToStream());

        mNameTextView.setText(mListItem.getName());

        mDisposables.add(Single.fromCallable(() -> VimeoApiServiceUtil.formatVideoCountAndFollowers(mListItem.getMetadata().getVideosConnection().getTotal(),
                mListItem.getMetadata().getUsersConnection().getTotal()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(str -> mVideosFollowersTextView.setText(str)));

        GlideApp.with(mBaseFragment)
                .load(mListItem.getHeader().getSizesList().get(0).getLink())
                .placeholder(R.drawable.video_image_placeholder)
                .fallback(R.drawable.video_image_placeholder)
                .fitCenter()
                //.transition(withCrossFade())
                .into(mImageView);
    }

    @Override
    public void recycle() {
        Glide.with(mBaseFragment)
                .clear(mImageView);

        mDisposables.clear();
    }

    @Override
    public void releaseInternalStates() {
        super.releaseInternalStates();
        //mFollowButtonRxBinding.releaseInternalStates();
    }

    @Override
    public void onClick (View view) {
        Intent intent = ChannelActivity.newIntent(mBaseFragment.getContext(), mListItem, getLayoutPosition());
        mBaseFragment.startActivityForResult(intent, ChannelsFragment.REQUEST_CHANNEL);
    }
}
