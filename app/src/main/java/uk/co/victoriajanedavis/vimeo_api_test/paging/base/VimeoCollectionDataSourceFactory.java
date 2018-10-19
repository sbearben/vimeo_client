package uk.co.victoriajanedavis.vimeo_api_test.paging.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.os.Parcelable;

public abstract class VimeoCollectionDataSourceFactory<T extends Parcelable> extends DataSource.Factory<String, T> {

    private MutableLiveData<VimeoCollectionDataSource<T>> mCollectionDataSourceLiveData = new MutableLiveData<>();

    private String mInitialUri;
    private String mSearchQuery;


    @Override
    public DataSource<String, T> create() {
        VimeoCollectionDataSource<T> dataSource = generateDataSource();
        dataSource.setInitialUri(mInitialUri);
        dataSource.setSearchQuery(mSearchQuery);

        mCollectionDataSourceLiveData.postValue(dataSource);
        return dataSource;
    }

    public abstract VimeoCollectionDataSource<T> generateDataSource();

    public MutableLiveData<VimeoCollectionDataSource<T>> getCollectionDataSourceLiveData() {
        return mCollectionDataSourceLiveData;
    }

    public void setInitialUri(String uri) {
        mInitialUri = uri;
    }

    public void setSearchQuery(String query) {
        mSearchQuery = query;
    }
}
