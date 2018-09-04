package uk.co.victoriajanedavis.vimeo_api_test.ui.reply;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoComment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.MvpView;

public interface ReplyMvpView extends MvpView {

    void setResponseResultAndExit(VimeoComment response);
}
