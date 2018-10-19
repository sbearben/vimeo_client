package uk.co.victoriajanedavis.vimeo_api_test.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoAccessToken;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationContext;
import uk.co.victoriajanedavis.vimeo_api_test.injection.ApplicationScope;

@ApplicationScope
public class PreferencesHelper {

    private static final String PREF_FILE_NAME = "android_vimeo_api_test_pref_file";
    private static final String TOKEN_EMPTY_FIELD = "";

    private static final String PREF_SUGGESTION_SET = "suggestionSet";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public SharedPreferences getSharedPreferences() {
        return mPref;
    }

    public void setAccessToken (VimeoAccessToken vimeoAccessToken) {
        mPref.edit()
                .putString(VimeoAccessToken.FIELD_ACCESS_TOKEN, vimeoAccessToken.getAccessToken())
                .putString(VimeoAccessToken.FIELD_TOKEN_TYPE, vimeoAccessToken.getTokenType())
                .putString(VimeoAccessToken.FIELD_SCOPE, vimeoAccessToken.getScope())
                .putString(VimeoAccessToken.FIELD_TOKEN_AUTH_LEVEL, vimeoAccessToken.getTokenAuthLevel())
                .apply();
    }

    public VimeoAccessToken getAccessToken() {
        VimeoAccessToken accessToken = VimeoAccessToken.newInstance();
        accessToken.setAccessToken(mPref.getString(VimeoAccessToken.FIELD_ACCESS_TOKEN, TOKEN_EMPTY_FIELD));
        accessToken.setTokenType(mPref.getString(VimeoAccessToken.FIELD_TOKEN_TYPE, TOKEN_EMPTY_FIELD));
        accessToken.setScope(mPref.getString(VimeoAccessToken.FIELD_SCOPE, TOKEN_EMPTY_FIELD));
        accessToken.setTokenAuthLevel(mPref.getString(VimeoAccessToken.FIELD_TOKEN_AUTH_LEVEL, TOKEN_EMPTY_FIELD));

        return accessToken;
    }

    public void deleteAccessToken() {
        mPref.edit()
                .remove(VimeoAccessToken.FIELD_ACCESS_TOKEN)
                .remove(VimeoAccessToken.FIELD_TOKEN_TYPE)
                .remove(VimeoAccessToken.FIELD_SCOPE)
                .remove(VimeoAccessToken.FIELD_TOKEN_AUTH_LEVEL)
                .apply();
    }

    public void setSearchSuggestionsSet (Set<String> suggestionSet) {
        mPref.edit()
                .putStringSet(PREF_SUGGESTION_SET, suggestionSet)
                .apply();
    }

    public Set<String> getSearchSuggestionsSet() {
        return mPref.getStringSet(PREF_SUGGESTION_SET, new HashSet<>());
    }

}
