package uk.co.victoriajanedavis.vimeo_api_test.ui.explore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.victoriajanedavis.vimeo_api_test.GlideApp;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCategory;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class CategoryViewHolder extends ListItemViewHolder<VimeoCategory> implements View.OnClickListener {

    @BindView(R.id.category_background_imageview) AppCompatImageView mBackGroundImageView;
    @BindView(R.id.category_icon_imageview) AppCompatImageView mIconImageView;
    @BindView(R.id.category_name_textview) TextView mNameTextView;


    public CategoryViewHolder(BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        super (baseFragment, inflater.inflate (R.layout.item_category, parent, false));
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);
    }

    @Override
    public void bind (@NonNull VimeoCategory category) {
        mListItem = category;

        mNameTextView.setText(category.getName());

        GlideApp.with(mBaseFragment)
                .load(category.getPictures().getSizesList().get(1).getLink())
                .transforms(new FitCenter(), new BlurTransformation(5, 3))
                .transition(withCrossFade())
                .into(mBackGroundImageView);

        GlideApp.with(mBaseFragment)
                .load(category.getIcons().getSizesList().get(1).getLink())
                .transition(withCrossFade())
                .into(mIconImageView);
    }

    @Override
    public void recycle() {
        Glide.with(mBaseFragment).clear(mBackGroundImageView);
        Glide.with(mBaseFragment).clear(mIconImageView);
        mBackGroundImageView.setImageDrawable(null);
        mIconImageView.setImageDrawable(null);
    }

    @Override
    public void onClick (View view) {
    }
}
