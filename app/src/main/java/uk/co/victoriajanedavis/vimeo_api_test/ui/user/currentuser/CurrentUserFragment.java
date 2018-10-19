package uk.co.victoriajanedavis.vimeo_api_test.ui.user.currentuser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.UUID;

import javax.inject.Inject;

import uk.co.victoriajanedavis.vimeo_api_test.BuildConfig;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.base.UserFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.base.UserPresenter;

public class CurrentUserFragment extends UserFragment {

    private static final String TAG = "CurrentUserFragment";

    @Inject CurrentUserPresenter mPresenter;


    public static CurrentUserFragment newInstance() {
        return new CurrentUserFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter.attachView(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        mLogoutButton.setVisibility(View.VISIBLE);
        mLogoutButton.setOnClickListener(v -> mPresenter.onLogout() );
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void onSignInMessageButtonClick() {
        showSignInWebView(true);
        String authUrl = "https://api.vimeo.com/oauth/authorize?client_id=" + BuildConfig.CLIENT_ID +
                "&response_type=code&redirect_uri=" + BuildConfig.OAUTH_REDIRECT +
                "&scope=private%20interact" +
                "&state=" + UUID.randomUUID().toString();

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(authUrl);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading (WebView webView, String url) {
                if (url.startsWith(BuildConfig.OAUTH_REDIRECT)) {
                    webView.stopLoading();
                    webView.loadUrl("about:blank");
                    showSignInWebView(false);

                    String code = Uri.parse(url).getQueryParameter("code");
                    if (code != null) {
                        mPresenter.getOauthToken(code, BuildConfig.OAUTH_REDIRECT);
                    }
                    else {
                        showUnauthorizedError();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public UserPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public String getCollectionUri() {
        return null;
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showUnauthorizedError() {
        mMessageImage.setImageResource(R.drawable.ic_account_circle_black_48dp);
        mMessageText.setText(R.string.error_not_signed_in);

        mMessageButton.setText(getString(R.string.action_sign_in));
        mMessageButton.setOnClickListener(null);
        mMessageButton.setOnClickListener(v -> onSignInMessageButtonClick());
        showMessageLayout(true);
    }

    @Override
    public void showError(String errorMessage) {
        mMessageImage.setImageResource(R.drawable.ic_error_outline_black_48dp);
        mMessageText.setText(getString(R.string.error_generic_server_error, errorMessage));

        mMessageButton.setText(getString(R.string.action_try_again));
        mMessageButton.setOnClickListener(null);
        mMessageButton.setOnClickListener(v -> onRefreshMessageButtonClick());
        showMessageLayout(true);
    }

    public void showSignInWebView(boolean show) {
        mWebView.setVisibility(show ? View.VISIBLE : View.GONE);
        mMessageLayout.setVisibility(View.GONE); // we always want this gone because the inverse of showing the webview will be showing the regular view
    }
}
