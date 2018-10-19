package uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow;

import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

public abstract class FollowButtonViewHolder<T extends ListItemFollowInteractor> extends ListItemViewHolder<T> {

    private static final String TAG = "FollowButtonViewHolder";

    @BindView(R.id.item_follow_button_layout) protected FollowToggleButton mFollowButton;

    //private Toast mErrorToast;

    protected FollowButtonRxBinding mFollowButtonRxBinding;
    protected CompositeDisposable mDisposables;


    public FollowButtonViewHolder (BaseFragment baseFragment, View itemView) {
        super(baseFragment, itemView);
        ButterKnife.bind(this, itemView);

        mDisposables = new CompositeDisposable();
        mFollowButtonRxBinding = new FollowButtonRxBinding(mFollowButton);

        //mErrorToast = Toast.makeText(mContext, "Failed to " + ((!mOriginalState) ? "unfollow" : "follow"), Toast.LENGTH_SHORT);
    }

    public void setFollowButtonClickListener (FollowButtonRxBinding.FollowButtonClickListener listener) {
        mFollowButtonRxBinding.setFollowButtonClickListener(listener);
    }
}
