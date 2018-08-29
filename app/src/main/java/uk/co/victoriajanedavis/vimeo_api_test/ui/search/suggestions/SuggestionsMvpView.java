package uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions;

import java.util.List;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.MvpView;

public interface SuggestionsMvpView extends MvpView {

    void showSuggestions(List<String> suggestionsList);
}
