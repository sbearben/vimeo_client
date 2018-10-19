package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationScope;

@ApplicationScope
public class VimeoAccessTokenHolder {

    private VimeoAccessToken mAccessToken;

    @Inject
    public VimeoAccessTokenHolder (VimeoAccessToken accessToken) {
        mAccessToken = accessToken;
    }

    @Nullable
    public VimeoAccessToken getVimeoAccessToken() {
        return mAccessToken;
    }

    public void setVimeoAccessToken (VimeoAccessToken accessToken) {
        mAccessToken = accessToken;
    }

    public void login (VimeoAccessToken accessToken) {
        mAccessToken = accessToken;
    }

    public void logout() {
        mAccessToken = VimeoAccessToken.newInstance();
    }
}
