package uk.co.victoriajanedavis.vimeo_api_test.ui.video;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;

public class UpNextVideoClickEvent {

    private VimeoVideo mVideo;


    public UpNextVideoClickEvent(VimeoVideo video) {
        mVideo = video;
    }

    public VimeoVideo getVideo() {
        return mVideo;
    }

    public void setVideo(VimeoVideo video) {
        mVideo = video;
    }
}
