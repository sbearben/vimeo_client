package uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.victoriajanedavis.vimeo_api_test.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class SuggestionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.item_suggestion_name_textview) TextView mSuggestionTextView;
    @BindView(R.id.item_suggestion_arrow_imageview) AppCompatImageView mArrowImageView;

    private String mSuggestion;


    public SuggestionsViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super (inflater.inflate (R.layout.item_suggestion, parent, false));
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener (this);

    }

    public void bind (@NonNull String suggestion) {
        mSuggestion = suggestion;
        mSuggestionTextView.setText(suggestion);
    }

    @OnClick(R.id.item_suggestion_arrow_imageview)
    public void onArrowIconClick() {
        if (itemView.getContext() != null) {
            OnClickListener listener = (OnClickListener) itemView.getContext();
            listener.onArrowIconClick(mSuggestion);
        }
    }

    @Override
    public void onClick (View view) {
        if (itemView.getContext() != null) {
            OnClickListener listener = (OnClickListener) itemView.getContext();
            listener.onViewHolderClick(mSuggestion);
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
