package uk.co.victoriajanedavis.vimeo_api_test.ui.explore;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCategory;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoCollection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;

public class VimeoCategoryAndVideoCollectionHolder {

    private VimeoCollection<VimeoCategory> categoriesCollection;
    private VimeoCollection<VimeoVideo> videosCollection;


    public VimeoCategoryAndVideoCollectionHolder (VimeoCollection<VimeoCategory> categoriesCollection,
                                                  VimeoCollection<VimeoVideo> videosCollection) {
        this.categoriesCollection = categoriesCollection;
        this.videosCollection = videosCollection;
    }

    public VimeoCollection<VimeoCategory> getCategoriesCollection() {
        return categoriesCollection;
    }

    public void setCategoriesCollection(VimeoCollection<VimeoCategory> categoriesCollection) {
        this.categoriesCollection = categoriesCollection;
    }

    public VimeoCollection<VimeoVideo> getVideosCollection() {
        return videosCollection;
    }

    public void setVideosCollection(VimeoCollection<VimeoVideo> videosCollection) {
        this.videosCollection = videosCollection;
    }
}
