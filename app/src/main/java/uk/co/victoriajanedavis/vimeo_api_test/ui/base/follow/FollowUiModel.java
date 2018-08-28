package uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow;

public class FollowUiModel {

    private long mVimeoItemId;
    /* new state meaning this is the state after the follow/unfollow was clicked - we turn this back
     * to the old state if the network call fails (just use the ! operator on it)
     */
    private boolean mIsFollowingNewState;

    private boolean mInProgress;
    private boolean mSuccess;
    private String mErrorMessage;


    private FollowUiModel (long itemId, boolean isFollowing, boolean inProgress,
                           boolean success, String errorMessage) {
        mVimeoItemId = itemId;
        mIsFollowingNewState = isFollowing;
        mInProgress = inProgress;
        mSuccess = success;
        mErrorMessage = errorMessage;

    }

    public static FollowUiModel inProgress(long itemId, boolean isFollowing) {
        return new FollowUiModel(itemId, isFollowing, true, false, null);
    }

    public static FollowUiModel success(long itemId, boolean isFollowing) {
        return new FollowUiModel(itemId, isFollowing, false, true, null);
    }

    public static FollowUiModel failure(long itemId, boolean isFollowing, String errorMessage) {
        return new FollowUiModel(itemId, isFollowing, false, false, errorMessage);
    }

    public boolean isInProgress() {
        return mInProgress;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public boolean isFollowing() {
        return mIsFollowingNewState;
    }
}
