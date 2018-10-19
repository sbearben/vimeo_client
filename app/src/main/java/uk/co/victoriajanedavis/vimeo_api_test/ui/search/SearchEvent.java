package uk.co.victoriajanedavis.vimeo_api_test.ui.search;

public class SearchEvent {

    private String mQuery;


    SearchEvent(String query) {
        mQuery = query;
    }

    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String query) {
        mQuery = query;
    }
}
