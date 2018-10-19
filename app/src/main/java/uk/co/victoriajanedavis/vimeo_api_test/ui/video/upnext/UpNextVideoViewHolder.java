package uk.co.victoriajanedavis.vimeo_api_test.ui.video.upnext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.victoriajanedavis.vimeo_api_test.VimeoApplication;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.UserVideoViewHolder;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.UpNextVideoClickEvent;
import uk.co.victoriajanedavis.vimeo_api_test.util.eventbus.RxBehaviourEventBus;
import uk.co.victoriajanedavis.vimeo_api_test.util.eventbus.RxPublishEventBus;

public class UpNextVideoViewHolder extends UserVideoViewHolder {

    private RxPublishEventBus mEventBus;


    public UpNextVideoViewHolder(BaseFragment baseFragment, LayoutInflater inflater, ViewGroup parent) {
        super (baseFragment, inflater, parent);
        mEventBus = VimeoApplication.get(parent.getContext()).getComponent().publishEventBus();
    }

    @Override
    public void onClick (View view) {
        mEventBus.post(new UpNextVideoClickEvent(mListItem));
    }
}
