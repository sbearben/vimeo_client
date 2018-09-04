package uk.co.victoriajanedavis.vimeo_api_test.ui.video.upnext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.UserVideoViewHolder;

public class UpNextVideoViewHolder extends UserVideoViewHolder {

    private UpNextVideoClickListener mUpNextVideoClickListener;


    public UpNextVideoViewHolder(BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        super (baseFragment, inflater, parent);
    }

    public void setUpNextVideoClickListener (UpNextVideoClickListener upNextVideoClickListener) {
        mUpNextVideoClickListener = upNextVideoClickListener;
    }

    @Override
    public void onClick (View view) {
        mUpNextVideoClickListener.onUpNextVideoClick(mListItem);
    }

    /*
     * Interface that the root Fragment has to implement (in this case, VideoFragment since it
     * contains the TabLayout that has all the child Fragments
     */
    public interface UpNextVideoClickListener {
        void onUpNextVideoClick (VimeoVideo video);
    }
}
