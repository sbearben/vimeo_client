package uk.co.victoriajanedavis.vimeo_api_test.ui.home;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonRxBinding;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.ListItemFollowInteractor;

public class AdjustableImageViewListAdapter extends ListAdapter<VimeoVideo> {

    private int mImageViewWidth, mImageViewHeight;


    public AdjustableImageViewListAdapter(BaseFragment fragment, ListItemViewHolder.ListItemViewHolderGenerator<VimeoVideo> viewHolderGenerator,
                                          int imageViewWidth, int imageViewHeight) {
        super(fragment, viewHolderGenerator);
        mImageViewWidth = imageViewWidth;
        mImageViewHeight = imageViewHeight;
    }

    @NonNull
    @Override
    public ListItemViewHolder<VimeoVideo> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return onIndicationViewHolder(parent);
        }

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        VideoFeedViewHolder viewHolder = (VideoFeedViewHolder) mViewHolderGenerator.generateViewHolder(mBaseFragment, layoutInflater, parent);
        viewHolder.setImageViewDimensions(mImageViewWidth, mImageViewHeight);

        return viewHolder;
    }


}
