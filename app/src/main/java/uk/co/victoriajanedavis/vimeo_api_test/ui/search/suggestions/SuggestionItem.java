package uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItem;

public class SuggestionItem implements ListItem {

    public static final String PREF_SUGGESTION_SET = "suggestionSet";
    private String suggestion;


    public SuggestionItem (String suggestion) {
        this.suggestion = suggestion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public long getId() {
        return RecyclerView.NO_ID;
    }

    public static Set<String> suggestionItemListToStringSet (List<SuggestionItem> suggestionItemList) {
        Set<String> suggestionSet = new HashSet<>();
        for (SuggestionItem item : suggestionItemList) {
            suggestionSet.add(item.getSuggestion());
        }

        return suggestionSet;
    }

    public static List<SuggestionItem> stringSetToSuggestionItemList (Set<String> suggestionSet) {
        List<SuggestionItem> suggestionItemList = new ArrayList<>();
        for (String str : suggestionSet) {
            suggestionItemList.add(new SuggestionItem(str));
        }

        return suggestionItemList;
    }

}
