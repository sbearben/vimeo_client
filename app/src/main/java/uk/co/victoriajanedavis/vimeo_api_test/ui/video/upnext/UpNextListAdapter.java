package uk.co.victoriajanedavis.vimeo_api_test.ui.video.upnext;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

public class UpNextListAdapter extends ListAdapter<VimeoVideo> {

    private UpNextVideoViewHolder.UpNextVideoClickListener mUpNextVideoClickListener;

    public UpNextListAdapter (Context context, BaseFragment fragment, ListItemViewHolder.ListItemViewHolderGenerator<VimeoVideo> viewHolderGenerator,
                              UpNextVideoViewHolder.UpNextVideoClickListener upNextVideoClickListener) {
        super(context, fragment, viewHolderGenerator);
        mUpNextVideoClickListener = upNextVideoClickListener;
    }

    @NonNull
    @Override
    public ListItemViewHolder<VimeoVideo> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return onIndicationViewHolder(parent);
        }

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        UpNextVideoViewHolder viewHolder = (UpNextVideoViewHolder) mViewHolderGenerator.generateViewHolder(mContext, mBaseFragment, layoutInflater, parent);
        viewHolder.setUpNextVideoClickListener(mUpNextVideoClickListener);

        return viewHolder;
    }
}
