package uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

public class FollowButtonListAdapter<T extends ListItemFollowInteractor> extends ListAdapter<T> {

    private FollowButtonRxBinding.FollowButtonClickListener mFollowButtonClickListener;


    public FollowButtonListAdapter (BaseFragment fragment, ListItemViewHolder.ListItemViewHolderGenerator<T> viewHolderGenerator,
                                    FollowButtonRxBinding.FollowButtonClickListener followButtonClickListener) {
        super(fragment, viewHolderGenerator);
        mFollowButtonClickListener = followButtonClickListener;
    }

    @NonNull
    @Override
    public ListItemViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return onIndicationViewHolder(parent);
        }

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FollowButtonViewHolder<T> viewHolder = (FollowButtonViewHolder<T>) mViewHolderGenerator.generateViewHolder(mBaseFragment, layoutInflater, parent);
        viewHolder.setFollowButtonClickListener(mFollowButtonClickListener);

        return viewHolder;
    }


}
