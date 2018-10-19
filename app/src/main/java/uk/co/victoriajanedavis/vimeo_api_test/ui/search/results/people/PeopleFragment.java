package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.people;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.FollowButtonRxBinding;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabPresenter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.otheruser.OtherUserFragment;

public class PeopleFragment extends ResultsTabFragment<VimeoUser> implements FollowButtonRxBinding.FollowButtonClickListener {

    private static final String TAG = "PeopleFragment";

    @Inject PeoplePresenter mPresenter;


    public static PeopleFragment newInstance() {
        return new PeopleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getPresenter().attachView(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        getPresenter().detachView();
        super.onDestroyView();
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
    }

    @Override
    @StringRes
    public int getOnEmptyListErrorMessageStringRes() {
        return R.string.error_no_users_to_display;
    }

    @Override
    public ResultsTabPresenter<VimeoUser> getPresenter() {
        return mPresenter;
    }

    @Override
    public ListAdapter<VimeoUser> createListAdapter() {
        return new FollowButtonListAdapter<VimeoUser>(this, UserViewHolder::new, this);
    }

    @Override
    protected String getCollectionUri() {
        return "users";
    }

    @Override
    public String getSingularHeaderMetric() {
        return "user";
    }

    @Override
    public String getPluralHeaderMetric() {
        return "users";
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
