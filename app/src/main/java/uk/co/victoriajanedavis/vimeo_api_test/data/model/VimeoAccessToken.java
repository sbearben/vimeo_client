package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.support.annotation.StringDef;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VimeoAccessToken {

    public static final String FIELD_ACCESS_TOKEN = "access_token";
    public static final String FIELD_TOKEN_TYPE = "token_type";
    public static final String FIELD_SCOPE = "scope";
    public static final String FIELD_TOKEN_AUTH_LEVEL = "token_auth_level";

    public static final String SCOPE_PUBLIC = "public";
    public static final String SCOPE_PRIVATE = "private";
    public static final String SCOPE_PURCHASED = "purchased";
    public static final String SCOPE_CREATE = "create";
    public static final String SCOPE_EDIT = "edit";
    public static final String SCOPE_DELETE = "delete";
    public static final String SCOPE_INTERACT = "interact";
    public static final String SCOPE_UPLOAD = "upload";
    public static final String SCOPE_PROMO_CODES = "promo_codes";
    public static final String SCOPE_VIDEO_FILES = "video_files";

    public static final String TOKEN_LEVEL_AUTHENTICATED = "Authenticated";
    public static final String TOKEN_LEVEL_UNAUTHENTICATED = "Unauthenticated";

    @StringDef({TOKEN_LEVEL_AUTHENTICATED, TOKEN_LEVEL_UNAUTHENTICATED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TokenAuthLevel {
    }

    @SerializedName(FIELD_ACCESS_TOKEN) @Expose private String access_token;
    @SerializedName(FIELD_TOKEN_TYPE) @Expose private String token_type;
    @SerializedName(FIELD_SCOPE) @Expose private String scope;
    private List<String> scopeList;
    private String token_auth_level; // determines if we have an Authenticated token or Unauthenticated token


    public static VimeoAccessToken newInstance() {
        VimeoAccessToken token = new VimeoAccessToken();
        token.access_token = "";
        token.token_type = "";
        token.scope = "";
        token.token_auth_level = "";

        return token;
    }

    private VimeoAccessToken() {
        scopeList = new ArrayList<>();
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getTokenType() {
        // OAuth requires uppercase Authorization HTTP header value for token type
        if(!Character.isUpperCase(token_type.charAt(0))) {
            token_type = Character.toString(token_type.charAt(0)).toUpperCase() + token_type.substring(1);
        }

        return token_type;
    }

    public void setTokenType(String token_type) {
        this.token_type = token_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
        if (this.scope != null) {
            generateScopeList(this.scope);
        }
    }

    private void generateScopeList(String scope) {
        this.scopeList = Arrays.asList(scope.split("\\s+"));
    }

    public List<String> getScopeList() {
        return scopeList;
    }

    public String getTokenAuthLevel() {
        return token_auth_level;
    }

    public void setTokenAuthLevel(@TokenAuthLevel String token_auth_level) {
        this.token_auth_level = token_auth_level;
    }

    public String getAuthorizationHeader() {
        if (TextUtils.isEmpty(token_type)) {
            return "";
        }
        return getTokenType() + " " + getAccessToken();
    }

    @Override
    public String toString() {
        return "VimeoAccessToken:\n" +
                "- access_token: " + getAccessToken() + "\n" +
                "- token_type: " + getTokenType() + "\n" +
                "- scope: " + getScope() + "\n";
    }
}
