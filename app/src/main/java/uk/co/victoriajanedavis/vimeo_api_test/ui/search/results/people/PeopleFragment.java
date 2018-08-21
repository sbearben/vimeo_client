package uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.people;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.base.ResultsTabPresenter;

public class PeopleFragment extends ResultsTabFragment<VimeoUser> {

    private static final String TAG = "PeopleFragment";

    @Inject PeoplePresenter mPresenter;


    public static PeopleFragment newInstance() {
        return new PeopleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        getPresenter().attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getPresenter().detachView();
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
        return new ListAdapter<>(getContext(), this, UserViewHolder::new);
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
}
