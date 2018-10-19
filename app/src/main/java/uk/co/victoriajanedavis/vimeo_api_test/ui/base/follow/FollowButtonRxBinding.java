package uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow;

import android.util.Log;
import android.view.View;

import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.util.HttpUtil;

public class FollowButtonRxBinding {

    private static final String TAG = "FollowButtonRxBinding";

    private ListItemFollowInteractor mFollowableItem; //User/Channel model - needed for IDs and 'following' state
    private FollowToggleButton mFollowButton;

    private FollowButtonClickListener mFollowButtonClickListener; // Used in switchMap to make follow/unfollow network calls (in Presenter)
    private NetworkCallFinishedCallback mNetworkCallFinishedCallback; // Used in Detail screen to set the result Bundle with state

    private boolean mOriginalState; // Original state of follow button before the next successful button click occurs


    public FollowButtonRxBinding(FollowToggleButton followButton) {
        this(null, followButton, null, null);
    }

    public FollowButtonRxBinding (ListItemFollowInteractor item, FollowToggleButton followButton,
                                  FollowButtonClickListener followButtonClickListener,
                                  NetworkCallFinishedCallback networkCallFinishedCallback) {
        mFollowButton = followButton;
        mFollowButtonClickListener = followButtonClickListener;
        mNetworkCallFinishedCallback = networkCallFinishedCallback;
        if (item != null) setFollowableItem(item);
    }

    private Observable<FollowUiModel> getRxBindingObservable() {
        return RxCompoundButton.checkedChanges(mFollowButton)
                .skipInitialValue()
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
                    switch(HttpUtil.responseType(voidResponse)) {
                        case HttpUtil.RESPONSE_OK:
                            return FollowUiModel.success(mFollowButton.isChecked());
                        case HttpUtil.RESPONSE_ERROR:
                        case HttpUtil.RESPONSE_UNAUTHORIZED:
                        default:
                            return FollowUiModel.failure(mFollowButton.isChecked(), voidResponse.message());
                    }
                })
                .doOnDispose(() -> Log.d (TAG, "RxBinding disposed!"))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Disposable subscribeToStream() {
        return getRxBindingObservable().subscribe(model -> {
            if (model.isSuccess()) {
                mOriginalState = model.isFollowing();
                if (mNetworkCallFinishedCallback != null) mNetworkCallFinishedCallback.onSuccess();
            }
            else {
                mFollowableItem.getFollowInteraction().setAdded(mOriginalState);
                mFollowButton.setChecked(mOriginalState, false);
                //mErrorToast.cancel();
                //mErrorToast.show();
            }
        }, t -> { throw new OnErrorNotImplementedException(t); });
    }

    public void releaseInternalStates() {
        mFollowButtonClickListener = null;
        mNetworkCallFinishedCallback = null;
        mFollowButton = null;
    }

    public void setFollowableItem(ListItemFollowInteractor followableItem) {
        mFollowableItem = followableItem;
        if (mFollowableItem.getFollowInteraction() != null) {
            mFollowButton.setVisibility(View.VISIBLE);
            mFollowButton.setChecked(mFollowableItem.getFollowInteraction().isAdded(), false);
            mOriginalState = mFollowButton.isChecked();
        }

    }

    public void setFollowButtonClickListener (FollowButtonClickListener listener) {
        mFollowButtonClickListener = listener;
    }

    public interface FollowButtonClickListener {
        Single<Response<Void>> onFollowButtonClick(long id);
        Single<Response<Void>> onUnfollowButtonClick(long id);
    }

    public interface NetworkCallFinishedCallback {
        void onSuccess();
    }
}
