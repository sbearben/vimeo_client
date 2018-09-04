package uk.co.victoriajanedavis.vimeo_api_test.ui.video.comments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.victoriajanedavis.vimeo_api_test.GlideApp;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoComment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.reply.ReplyActivity;
import uk.co.victoriajanedavis.vimeo_api_test.ui.reply.ReplyFragment;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class CommentViewHolder extends ListItemViewHolder<VimeoComment> {

    @BindView(R.id.item_comment_imageview) AppCompatImageView mImageView;
    @BindView(R.id.item_comment_name_textview) TextView mNameTextView;
    @BindView(R.id.item_comment_age_textview) TextView mCommentAgeTextView;
    @BindView(R.id.item_comment_comment_textview) TextView mCommentTextView;
    @BindView(R.id.item_comment_reply_textview) TextView mReplyButtonTextView;


    public CommentViewHolder(BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        super (baseFragment, inflater.inflate (R.layout.item_comment, parent, false));
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind (@NonNull VimeoComment vimeoComment) {
        mListItem = vimeoComment;

        mReplyButtonTextView.setVisibility(mBaseFragment.isUserLoggedIn() ? View.VISIBLE : View.GONE);

        mNameTextView.setText(mListItem.getUser().getName());
        String commentAge = String.format(Locale.getDefault(), " ⋅ %s", VimeoApiServiceUtil.dateCreatedToTimePassed(mListItem.getCreatedTime()));
        mCommentAgeTextView.setText(commentAge);

        mCommentTextView.setText(mListItem.getText());

        GlideApp.with(mBaseFragment)
                .load(mListItem.getUser().getPictures().getSizesList().get(1).getLink())
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

    //@OnClick(R.id.item_comment_reply_textview)
    public void onReplyButtonClick (View view) {
        Intent intent = ReplyActivity.newIntent(mBaseFragment.getContext(),
                VimeoApiServiceUtil.generateVideoIdFromCommentUri(mListItem.getUri()), mListItem,
                ReplyFragment.RESPONSE_TYPE_REPLY);

        mBaseFragment.startActivityForResult(intent, CommentsFragment.REQUEST_REPLY);
    }
}
