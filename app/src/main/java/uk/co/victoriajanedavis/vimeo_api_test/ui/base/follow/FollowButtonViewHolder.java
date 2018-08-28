package uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow;

import android.content.Context;
import android.view.View;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItem;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;

public abstract class FollowButtonViewHolder<T extends ListItem> extends ListItemViewHolder<T> {

    protected FollowButtonClickListener mFollowButtonClickListener;

    protected Disposable mDisposable;
    protected boolean mOriginalState;


    public FollowButtonViewHolder (Context context, BaseFragment baseFragment, View itemView) {
        super(context, baseFragment, itemView);
    }

    public void setFollowButtonClickListener (FollowButtonClickListener listener) {
        mFollowButtonClickListener = listener;
    }

    public interface FollowButtonClickListener {
        Single<Response<Void>> onFollowButtonClick(long channel_id);
        Single<Response<Void>> onUnfollowButtonClick(long channel_id);
    }
}
