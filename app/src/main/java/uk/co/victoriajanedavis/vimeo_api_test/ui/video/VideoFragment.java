package uk.co.victoriajanedavis.vimeo_api_test.ui.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import uk.co.victoriajanedavis.vimeo_api_test.GlideApp;
import uk.co.victoriajanedavis.vimeo_api_test.R;
import uk.co.victoriajanedavis.vimeo_api_test.VimeoApplication;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.ExpandableTextView;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.BaseFragment;
import uk.co.victoriajanedavis.vimeo_api_test.util.DisplayMetricsUtil;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoTextUtil;
import uk.co.victoriajanedavis.vimeo_api_test.util.eventbus.RxPublishEventBus;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class VideoFragment extends BaseFragment {

    private static final String TAG = "VideoFragment";
    public static final String FRAGMENT_VIDEO_TAG = "fragment_video";

    private static final String SAVED_VIMEO_VIDEO = "fragment_video_saved_video";
    private static final String ARG_VIMEO_VIDEO = "vimeo_video";

    private RxPublishEventBus mEventBus;
    private Disposable mDisposable;

    @BindView(R.id.fragment_video_imageview) WebView mImageView;
    //@BindView(R.id.fragment_video_imageview) AppCompatImageView mImageView;
    @BindView(R.id.fragment_video_timelength_textview) TextView mTimeTextView;

    @BindView(R.id.fragment_video_details_layout) ConstraintLayout mDetailsLayout;
    @BindView(R.id.fragment_video_title_textview) TextView mTitleTextView;
    @BindView(R.id.fragment_video_plays_textview) TextView mPlaysAgeTextView;

    @BindView(R.id.layout_expandable_textview_textview) ExpandableTextView mDescriptionTextView;
    @BindView(R.id.layout_expandable_textview_imageview) AppCompatImageView mDescriptionExpandImageView;

    @BindView(R.id.item_user_imageview) AppCompatImageView mUserImageView;
    @BindView(R.id.item_user_name_textview) TextView mUserNameTextView;
    @BindView(R.id.item_user_videosfollowers_textview) TextView mUserVideosFollowersTextView;

    @BindView(R.id.fragment_video_viewpager) ViewPager mViewPager;
    @BindView(R.id.fragment_video_tablayout) TabLayout mTabLayout;

    @BindView(R.id.content_progress) ProgressBar mContentLoadingProgress;
    @BindView(R.id.message_layout) View mMessageLayout;
    @BindView(R.id.message_imageview) ImageView mMessageImage;
    @BindView(R.id.message_textview) TextView mMessageText;
    @BindView(R.id.message_tryagain_button) Button mMessageButton;

    private VideoPagerAdapter mPagerAdapter;
    private VimeoVideo mVideo;

    private DisplayMetricsUtil.Dimensions mScreenDimensions;


    public static VideoFragment newInstance (VimeoVideo video) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_VIMEO_VIDEO, video);

        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEventBus = VimeoApplication.get(getContext()).getComponent().publishEventBus();
        mScreenDimensions = DisplayMetricsUtil.getScreenDimensions();

        mVideo = (VimeoVideo) getArguments().getParcelable(ARG_VIMEO_VIDEO);
        if (savedInstanceState != null) {
            mVideo = savedInstanceState.getParcelable(SAVED_VIMEO_VIDEO);
        }

        mPagerAdapter = new VideoPagerAdapter(getContext(), getFragmentManager(), mVideo.getMetadata());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, v);

        //mEventBus.post(new UpNextVideoClickEvent(mVideo));
        mDisposable = mEventBus.filteredObservable(UpNextVideoClickEvent.class).subscribe(
                event -> {
                    mVideo = event.getVideo();
                    Log.d(TAG, "Video Title: " + mVideo.getName());
                    initViews(getView());
                    //updateUpNextFragment(video);
                    mPagerAdapter.setVideoMetadata(mVideo.getMetadata());
                });

        initPager(v);
        initViews(v);

        return v;
    }

    private void initPager(View v) {
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initViews(View view) {
        // Dynamically set the height of the video container
        int videoHeight;
        if (mScreenDimensions.getWidth() < mScreenDimensions.getHeight()) {
            // We're in portrait mode since screen width < height
            videoHeight = (int)(mScreenDimensions.getWidth()/VimeoVideo.ASPECT_RATIO);
        }
        else {
            // We're in landscape mode
            videoHeight = mScreenDimensions.getHeight() - DisplayMetricsUtil.getStatusBarHeight();
        }
        mImageView.setLayoutParams(new Constraints.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, videoHeight));

        // We need to set the top margin of the details Linear Layout to the calculated height of the video container
        CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) mDetailsLayout.getLayoutParams();
        params.setMargins(0, videoHeight, 0, 0);
        mDetailsLayout.setLayoutParams(params);

        /*
        GlideApp.with(this)
                .load(mVideo.getPictures().getSizesList().get(2).getLinkWithPlayButton())
                .thumbnail(Glide.with(this)
                        .load(mVideo.getPictures().getSizesList().get(0).getLinkWithPlayButton()))
                .placeholder(R.drawable.video_image_placeholder)
                .fallback(R.drawable.video_image_placeholder)
                .fitCenter()
                .into(mImageView);
                */

        initWebView();


        mTimeTextView.setText(VimeoTextUtil.formatSecondsToDuration(mVideo.getDurationSeconds()));
        mTitleTextView.setText(mVideo.getName());
        mPlaysAgeTextView.setText(VimeoTextUtil.formatVideoAgeAndPlays(mVideo.getStats().getPlays(), mVideo.getCreatedTime()));

        VimeoTextUtil.hideOrDisplayTextViewIfNullString(mDescriptionTextView, mVideo.getDescription());
        mDescriptionTextView.setImageIcon(mDescriptionExpandImageView);
        mDescriptionTextView.reinitialize();

        VimeoUser user = mVideo.getUser();

        GlideApp.with(this)
                .load(user.getPictures().getSizesList().get(2).getLink())
                .circleCrop()
                .placeholder(R.drawable.user_image_placeholder)
                .fallback(R.drawable.user_image_placeholder)
                .transition(withCrossFade())
                .into(mUserImageView);

        mUserNameTextView.setText(user.getName());
        String videoCountAndFollowers = VimeoTextUtil.formatVideoCountAndFollowers(user.getMetadata().getVideosConnection().getTotal(),
                user.getMetadata().getFollowersConnection().getTotal());
        mUserVideosFollowersTextView.setText(videoCountAndFollowers);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_VIMEO_VIDEO, mVideo);
    }


    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView() {
        String webplayerUrl = "https://player.vimeo.com/video/" + mVideo.getId();

        mImageView.getSettings().setJavaScriptEnabled(true);
        mImageView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mImageView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        if (Build.VERSION.SDK_INT >= 21) {
            mImageView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(mImageView, true);
        }
        else {
            mImageView.setBackgroundColor(Color.argb(1, 0, 0, 0));
            CookieManager.getInstance().setAcceptCookie(true);
        }

        mImageView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading (WebView webView, String url) {
                if (url.equals(webplayerUrl)) {
                    webView.loadUrl(url);
                    return true;
                }
                return false;
            }
        });

        mImageView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");
        mImageView.loadUrl(webplayerUrl);
    }

    @Override
    public void onDestroyView() {
        mViewPager.setAdapter(null);
        mDisposable.dispose();

        if (!((AppCompatActivity) getContext()).isFinishing()) {
            Glide.with(this).clear(mImageView);
            Glide.with(this).clear(mUserImageView);
        }
        super.onDestroyView();
    }
}
