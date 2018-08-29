package uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

public class FollowButtonListAdapter<T extends ListItemFollowInteractor> extends ListAdapter<T> {

    private FollowButtonRxBinding.FollowButtonClickListener mFollowButtonClickListener;


    public FollowButtonListAdapter (Context context, BaseFragment fragment, ListItemViewHolder.ListItemViewHolderGenerator<T> viewHolderGenerator,
                                    FollowButtonRxBinding.FollowButtonClickListener followButtonClickListener) {
        super(context, fragment, viewHolderGenerator);
        mFollowButtonClickListener = followButtonClickListener;
    }

    @NonNull
    @Override
    public ListItemViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return onIndicationViewHolder(parent);
        }

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FollowButtonViewHolder<T> viewHolder = (FollowButtonViewHolder<T>) mViewHolderGenerator.generateViewHolder(mContext, mBaseFragment, layoutInflater, parent);
        viewHolder.setFollowButtonClickListener(mFollowButtonClickListener);

        return viewHolder;
    }


}
