package uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow;

public class FollowUiModel {

    /* new state meaning this is the state after the follow/unfollow was clicked - we turn this back
     * to the old state if the network call fails (just use the ! operator on it)
     */
    private boolean mIsFollowingNewState;

    private boolean mSuccess;
    private String mErrorMessage;


    private FollowUiModel (boolean isFollowing, boolean success, String errorMessage) {
        mIsFollowingNewState = isFollowing;
        mSuccess = success;
        mErrorMessage = errorMessage;

    }

    public static FollowUiModel success(boolean isFollowing) {
        return new FollowUiModel(isFollowing, true, null);
    }

    public static FollowUiModel failure(boolean isFollowing, String errorMessage) {
        return new FollowUiModel(isFollowing, false, errorMessage);
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public boolean isFollowing() {
        return mIsFollowingNewState;
    }
}
