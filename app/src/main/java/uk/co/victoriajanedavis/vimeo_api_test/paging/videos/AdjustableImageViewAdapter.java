package uk.co.victoriajanedavis.vimeo_api_test.paging.videos;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.paging.base.VimeoCollectionAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.home.VideoFeedViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.util.DisplayMetricsUtil;

public class AdjustableImageViewAdapter extends VimeoCollectionAdapter<VimeoVideo> {

    private DisplayMetricsUtil.Dimensions mScreenDimensions;


    public AdjustableImageViewAdapter(BaseFragment fragment, ListItemViewHolder.ListItemViewHolderGenerator<VimeoVideo> viewHolderGenerator,
                                      DisplayMetricsUtil.Dimensions screenDimensions) {
        super(fragment, viewHolderGenerator);
        mScreenDimensions = screenDimensions;
    }

    @NonNull
    @Override
    public ListItemViewHolder<VimeoVideo> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        VideoFeedViewHolder viewHolder = (VideoFeedViewHolder) mViewHolderGenerator.generateViewHolder(mBaseFragment, layoutInflater, parent);
        viewHolder.setImageViewDimensions(mScreenDimensions.getWidth(), mScreenDimensions.getHeight());

        return viewHolder;
    }
}
