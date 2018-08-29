package uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class SuggestionsViewHolder extends ListItemViewHolder<String> implements View.OnClickListener {

    @BindView(R.id.item_suggestion_name_textview) TextView mSuggestionTextView;
    @BindView(R.id.item_suggestion_arrow_imageview) AppCompatImageView mArrowImageView;


    public SuggestionsViewHolder(Context context, BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        super (context, baseFragment, inflater.inflate (R.layout.item_suggestion, parent, false));
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener (this);

    }

    @Override
    public void bind (@NonNull String suggestion) {
        mListItem = suggestion;
        mSuggestionTextView.setText(suggestion);
    }

    @Override
    public void recycle() {
    }

    @OnClick(R.id.item_suggestion_arrow_imageview)
    public void onArrowIconClick() {
        if (mContext != null) {
            OnClickListener listener = (OnClickListener) mContext;
            listener.onArrowIconClick(mListItem);
        }
    }

    @Override
    public void onClick (View view) {
        if (mContext != null) {
            OnClickListener listener = (OnClickListener) mContext;
            listener.onViewHolderClick(mListItem);
        }
    }

    /*
     * Interface that the host Activity will implement, so that when the arrow icon is clicked, we
     * can take the word from this ViewHolder and send it to the Activity, which will put it in
     * the SearchView of the Toolbar.
     */
    public interface OnClickListener {
        void onArrowIconClick (String suggestion);
        void onViewHolderClick (String suggestion);
    }
}
