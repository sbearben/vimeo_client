package uk.co.victoriajanedavis.vimeo_api_test.injection.component;

import dagger.Subcomponent;
import uk.co.victoriajanedavis.vimeo_api_test.injection.PerFragment;
import uk.co.victoriajanedavis.vimeo_api_test.injection.module.FragmentModule;
import uk.co.victoriajanedavis.vimeo_api_test.ui.channel.ChannelFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.explore.ExploreFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.home.HomeFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.ResultsFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.allvideos.AllVideosFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.channels.ChannelsFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.myvideos.MyVideosFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.results.people.PeopleFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.search.suggestions.SuggestionsFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.currentuser.CurrentUserFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.user.otheruser.OtherUserFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.comments.CommentsFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.likes.LikesFragment;
import uk.co.victoriajanedavis.vimeo_api_test.ui.video.upnext.UpNextFragment;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(HomeFragment homeFragment);
    void inject(CurrentUserFragment currentUserFragment);
    void inject(ExploreFragment exploreFragment);

    void inject(SuggestionsFragment suggestionsFragment);
    void inject(ResultsFragment resultsFragment);
    void inject(AllVideosFragment allVideosFragment);
    void inject(MyVideosFragment myVideosFragment);
    void inject(ChannelsFragment channelsFragment);
    void inject(PeopleFragment peopleFragment);

    void inject(UpNextFragment upNextFragment);
    void inject(CommentsFragment commentsFragment);
    void inject(LikesFragment likesFragment);

    void inject(OtherUserFragment otherUserFragment);
    void inject(ChannelFragment channelFragment);
}
