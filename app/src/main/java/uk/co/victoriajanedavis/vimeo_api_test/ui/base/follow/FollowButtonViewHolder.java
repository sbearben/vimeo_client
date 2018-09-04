package uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

public abstract class FollowButtonViewHolder<T extends ListItemFollowInteractor> extends ListItemViewHolder<T> {

    private static final String TAG = "FollowButtonViewHolder";

    @BindView(R.id.item_follow_button_layout) protected FollowToggleButton mFollowButton;

    //private Toast mErrorToast;

    protected FollowButtonRxBinding mFollowButtonRxBinding;
    protected Disposable mDisposable;
    protected boolean mOriginalState;


    public FollowButtonViewHolder (BaseFragment baseFragment, View itemView) {
        super(baseFragment, itemView);
        ButterKnife.bind(this, itemView);

        mFollowButtonRxBinding = new FollowButtonRxBinding();

        //mErrorToast = Toast.makeText(mContext, "Failed to " + ((!mOriginalState) ? "unfollow" : "follow"), Toast.LENGTH_SHORT);
    }

    protected Disposable setUpFollowButtonRxBindingStream() {
        return mFollowButtonRxBinding.setupFollowButtonRxBindingStream();
    }

    public void setFollowButtonClickListener (FollowButtonRxBinding.FollowButtonClickListener listener) {
        mFollowButtonRxBinding.setFollowButtonClickListener(listener);
    }
}
