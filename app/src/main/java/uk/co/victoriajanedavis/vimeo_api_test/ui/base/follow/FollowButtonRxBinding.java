package uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

public class FollowButtonRxBinding {

    private static final String TAG = "FollowButtonRxBinding";

    private ListItemFollowInteractor mFollowableItem;
    private FollowToggleButton mFollowButton;

    private FollowButtonClickListener mFollowButtonClickListener;
    private NetworkCallFinishedCallback mNetworkCallFinishedCallback;
    //private Toast mErrorToast;

    private boolean mOriginalState;


    public FollowButtonRxBinding() {
    }

    public FollowButtonRxBinding (ListItemFollowInteractor item, FollowToggleButton followButton,
                                  FollowButtonClickListener followButtonClickListener) {
        this(item, followButton, followButtonClickListener, null);
    }

    public FollowButtonRxBinding (ListItemFollowInteractor item, FollowToggleButton followButton,
                                  FollowButtonClickListener followButtonClickListener,
                                  NetworkCallFinishedCallback networkCallFinishedCallback) {
        mFollowButton = followButton;
        mFollowButtonClickListener = followButtonClickListener;
        mNetworkCallFinishedCallback = networkCallFinishedCallback;
        if (item != null) setFollowableItem(item);

        //mErrorToast = Toast.makeText(mContext, "Failed to " + ((!mOriginalState) ? "unfollow" : "follow"), Toast.LENGTH_SHORT);
    }

    public Disposable setupFollowButtonRxBindingStream() {
        return RxCompoundButton.checkedChanges(mFollowButton)
                .skipInitialValue()
                //.takeUntil(RxView.detaches(parent))
                .switchMapSingle(isChecked -> {
                    mFollowableItem.getFollowInteraction().setAdded(isChecked);
                    if (isChecked) {
                        return mFollowButtonClickListener.onFollowButtonClick(mFollowableItem.getId())
                                .subscribeOn(Schedulers.io())
                                .doOnDispose(() -> Log.d(TAG, "Follow DISPOSED -> id: " + mFollowableItem.getId()));
                    }
                    else {
                        return mFollowButtonClickListener.onUnfollowButtonClick(mFollowableItem.getId())
                                .subscribeOn(Schedulers.io())
                                .doOnDispose(() -> Log.d(TAG, "Unfollow DISPOSED -> id: " + mFollowableItem.getId()));
                    }
                })
                .map(voidResponse -> {
                    switch(VimeoApiServiceUtil.responseType(voidResponse)) {
                        case VimeoApiServiceUtil.RESPONSE_OK:
                            return FollowUiModel.success(mFollowButton.isChecked());
                        case VimeoApiServiceUtil.RESPONSE_ERROR:
                        case VimeoApiServiceUtil.RESPONSE_UNAUTHORIZED:
                        default:
                            return FollowUiModel.failure(mFollowButton.isChecked(), voidResponse.message());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                //.startWith(FollowUiModel.inProgress(mFollowableItem.getId(), mFollowButton.isChecked()))
                .subscribe(model -> {
                    if (model.isInProgress()) {
                        //Log.d (TAG, "this is called");
                    }
                    else {
                        if (model.isSuccess()) {
                            mOriginalState = model.isFollowing();
                            if (mNetworkCallFinishedCallback != null) mNetworkCallFinishedCallback.onSuccess();
                        }
                        else {
                            mFollowableItem.getFollowInteraction().setAdded(mOriginalState);
                            mFollowButton.setChecked(mOriginalState, false);
                            if (mNetworkCallFinishedCallback != null) mNetworkCallFinishedCallback.onFailure();
                            //mErrorToast.cancel();
                            //mErrorToast.show();
                        }
                    }
                }, t -> { throw new OnErrorNotImplementedException(t); });
    }

    public void releaseInternalStates() {
        mFollowButtonClickListener = null;
        mFollowButton = null;
    }

    public void setFollowableItem(ListItemFollowInteractor followableItem) {
        mFollowableItem = followableItem;
        if (mFollowableItem.getFollowInteraction() != null) {
            mFollowButton.setVisibility(View.VISIBLE);
            mFollowButton.setChecked(mFollowableItem.getFollowInteraction().isAdded());
            mOriginalState = mFollowButton.isChecked();
        }

    }

    public void setFollowButtonClickListener (FollowButtonClickListener listener) {
        mFollowButtonClickListener = listener;
    }

    public void setFollowButton (FollowToggleButton followButton) {
        mFollowButton = followButton;
    }

    public interface FollowButtonClickListener {
        Single<Response<Void>> onFollowButtonClick(long id);
        Single<Response<Void>> onUnfollowButtonClick(long id);
    }

    public interface NetworkCallFinishedCallback {
        void onSuccess();
        void onFailure();
    }
}
