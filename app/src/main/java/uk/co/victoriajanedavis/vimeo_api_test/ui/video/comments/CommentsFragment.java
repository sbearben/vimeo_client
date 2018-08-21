package uk.co.victoriajanedavis.vimeo_api_test.ui.video.comments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoComment;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListAdapter;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItemViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.base.VideoTabFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.base.VideoTabPresenter;

public class CommentsFragment extends VideoTabFragment<VimeoComment> {

    private static final String TAG = "CommentsFragment";

    protected static final String ARG_VIMEO_CONNECTION = "vimeo_connection";

    @Inject CommentsPresenter mPresenter;


    public static CommentsFragment newInstance (VimeoConnection connection) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_VIMEO_CONNECTION, connection);

        CommentsFragment fragment = new CommentsFragment();
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
    public void onDetach() {
        super.onDetach();
        getPresenter().detachView();
    }

    @Override
    @StringRes
    public int getOnEmptyListErrorMessageStringRes() {
        return R.string.error_no_comments_to_display;
    }

    @Override
    public VideoTabPresenter<VimeoComment> getPresenter() {
        return mPresenter;
    }

    @Override
    public ListAdapter<VimeoComment> createListAdapter() {
        return new ListAdapter<>(getContext(), this, CommentViewHolder::new);
    }

    @Override
    public VimeoConnection getConnectionFromVideo (VimeoVideo video) {
        return video.getMetadata().getCommentsConnection();
    }
}
