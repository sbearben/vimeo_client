package uk.co.victoriajanedavis.vimeo_api_test.ui.video.likes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoComment;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonRxBinding;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.people.UserViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.otheruser.OtherUserFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.base.VideoTabFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.base.VideoTabPresenter;

public class LikesFragment extends VideoTabFragment<VimeoUser> implements FollowButtonRxBinding.FollowButtonClickListener {

    private static final String TAG = "LikesFragment";

    protected static final String ARG_VIMEO_CONNECTION = "vimeo_connection";

    @Inject LikesPresenter mPresenter;


    public static LikesFragment newInstance (VimeoConnection connection) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_VIMEO_CONNECTION, connection);

        LikesFragment fragment = new LikesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        getPresenter().attachView(this);

        mConnection = (VimeoConnection) getArguments().getParcelable(ARG_VIMEO_CONNECTION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == OtherUserFragment.REQUEST_USER) {
            VimeoUser channel = (VimeoUser) data.getParcelableExtra(OtherUserFragment.EXTRA_VIMEO_USER);
            int position = (int) data.getIntExtra(OtherUserFragment.EXTRA_USER_POSITION, -1);
            mListAdapter.updateItem(position, channel);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getPresenter().detachView();
    }

    @Override
    @StringRes
    public int getOnEmptyListErrorMessageStringRes() {
        return R.string.error_no_likes_to_display;
    }

    @Override
    public VideoTabPresenter<VimeoUser> getPresenter() {
        return mPresenter;
    }

    @Override
    public ListAdapter<VimeoUser> createListAdapter() {
        return new FollowButtonListAdapter<VimeoUser>(this, UserViewHolder::new, this);
    }

    @Override
    public VimeoConnection getConnectionFromVideo (VimeoVideo video) {
        return video.getMetadata().getLikesConnection();
    }

    // The next two Overrides are for FollowButtonViewHolder.FollowButtonClickListener
    @Override
    public Single<Response<Void>> onFollowButtonClick(long user_id) {
        return mPresenter.getFollowUserSingle(user_id);
    }

    @Override
    public Single<Response<Void>> onUnfollowButtonClick(long user_id) {
        return mPresenter.getUnfollowUserSingle(user_id);
    }
}
