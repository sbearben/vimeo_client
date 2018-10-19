package uk.co.victoriajanedavis.vimeo_api_test.ui.user.otheruser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonRxBinding;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.base.UserFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.base.UserPresenter;

import static android.app.Activity.RESULT_OK;

public class OtherUserFragment extends UserFragment {

    private static final String TAG = "OtherUserFragment";
    private static final String ARG_VIMEO_USER = "vimeo_user";
    private static final String ARG_USER_POSITION = "user_position";

    public static final String EXTRA_VIMEO_USER = "uk.co.victoriajanedavis.vimeo_api_test.OtherUserFragment.vimeo_user";
    public static final String EXTRA_USER_POSITION = "uk.co.victoriajanedavis.vimeo_api_test.OtherUserFragment.user_position";

    public static final String FRAGMENT_OTHER_USER_TAG = "fragment_other_user";

    public static final int REQUEST_USER = 1;

    @Inject OtherUserPresenter mPresenter;

    private int mPosition;


    public static OtherUserFragment newInstance (VimeoUser user, int position) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_VIMEO_USER, user);
        args.putInt(ARG_USER_POSITION, position);

        OtherUserFragment fragment = new OtherUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        mPresenter.attachView(this);

        mUser = (VimeoUser) getArguments().getParcelable(ARG_VIMEO_USER);
        mPosition = (int) getArguments().getInt(ARG_USER_POSITION);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        mFollowButtonRxBinding = new FollowButtonRxBinding(mUser, mFollowButton, new FollowButtonRxBinding.FollowButtonClickListener() {
            @Override
            public Single<Response<Void>> onFollowButtonClick(long user_id) {
                return mPresenter.getFollowUserSingle(user_id);
            }

            @Override
            public Single<Response<Void>> onUnfollowButtonClick(long user_id) {
                return mPresenter.getUnfollowUserSingle(user_id);
            }
        }, this::setResponseResult);

        mFollowButtonDisposable = mFollowButtonRxBinding.subscribeToStream();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFollowButtonDisposable.dispose();
        mFollowButtonRxBinding.releaseInternalStates();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    @Override
    public UserPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public String getCollectionUri() {
        return mUser.getMetadata().getVideosConnection().getUri();
    }

    public void setResponseResult() {
        Intent data = new Intent();
        data.putExtra (EXTRA_VIMEO_USER, mUser);
        data.putExtra (EXTRA_USER_POSITION, mPosition);

        if (getActivity() != null) {
            getActivity().setResult(RESULT_OK, data);
        }
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showUnauthorizedError() {
        mMessageImage.setImageResource(R.drawable.ic_error_outline_black_48dp);
        mMessageText.setText(getString(R.string.error_generic_server_error, "Unauthorized"));
        mMessageButton.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showError(String errorMessage) {
        mMessageImage.setImageResource(R.drawable.ic_error_outline_black_48dp);
        mMessageText.setText(getString(R.string.error_generic_server_error, errorMessage));
        mMessageButton.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }
}
