package uk.co.victoriajanedavis.vimeo_api_test.ui.video.comments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.victoriajanedavis.vimeo_api_test.GlideApp;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoComment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class CommentViewHolder extends ListItemViewHolder<VimeoComment> {

    @BindView(R.id.item_comment_imageview) AppCompatImageView mImageView;
    @BindView(R.id.item_comment_name_textview) TextView mNameTextView;
    @BindView(R.id.item_comment_age_textview) TextView mCommentAgeTextView;
    @BindView(R.id.item_comment_comment_textview) TextView mCommentTextView;
    @BindView(R.id.item_comment_reply_textview) TextView mReplyButtonTextView;


    public CommentViewHolder(Context context, BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        super (context, baseFragment, inflater.inflate (R.layout.item_comment, parent, false));
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind (@NonNull VimeoComment vimeoComment) {
        mListItem = vimeoComment;

        mNameTextView.setText(mListItem.getUser().getName());
        String commentAge = String.format(Locale.getDefault(), " ⋅ %s", VimeoApiServiceUtil.dateCreatedToTimePassed(mListItem.getCreatedTime()));
        mCommentAgeTextView.setText(commentAge);

        mCommentTextView.setText(mListItem.getText());

        GlideApp.with(mBaseFragment)
                .load(mListItem.getUser().getPictures().getSizesList().get(2).getLink())
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
}
