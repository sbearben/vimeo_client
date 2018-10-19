package uk.co.victoriajanedavis.vimeo_api_test.ui.base;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

public class ExpandableTextView extends AppCompatTextView {

    private static final String TAG = "ExpandableTextView";

    private static final int STARTING_HEIGHT_UNINITIALIZED = -1;
    private static final int COLLAPSED_TEXTVIEW_MAX_LINES = 2;

    private static final int ANIMATION_DURATION = 75;
    private static final boolean ANIMATE_FORWARDS = true;
    private static final boolean ANIMATE_BACKWARDS = false ;

    private ImageView mIconImageView;

    private boolean mAnimationDirection = ANIMATE_FORWARDS;

    private int mMinHeight = STARTING_HEIGHT_UNINITIALIZED;
    private int mMaxHeight;

    private boolean mListenersInitialized = false;
    private boolean mLayoutCalcsInitialized = false;


    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeListeners();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (!mLayoutCalcsInitialized && getLineCount() != 0) {
            onLayoutCalculations();
            // Prevent this if statement from being called again unless the TextView is refreshed
            // (such as in the case where we click on an 'Up Next' video in VideoFragment
            mLayoutCalcsInitialized = true;
        }
    }

    public void setImageIcon (ImageView imageIcon) {
        mIconImageView = imageIcon;
    }

    public void reinitialize() {
        if (!mListenersInitialized) {
            initializeListeners();
        }
        mLayoutCalcsInitialized = false;
        mIconImageView.setRotation(0);
        mAnimationDirection = ANIMATE_FORWARDS;
    }

    private void initializeListeners() {
        setTextViewClickListener();
        setGlobalLayoutListener();
        mListenersInitialized = true;
    }

    private void setTextViewClickListener() {
        setOnClickListener(null);
        setOnClickListener (v -> {
            if (mIconImageView == null) {
                throw new NullPointerException("Error: imageIcon hasn't been set on ExpandableTextView");
            }

            if (mAnimationDirection == ANIMATE_FORWARDS) {
                expandTextView();
                mAnimationDirection = ANIMATE_BACKWARDS;
            } else {
                collapseTextView();
                mAnimationDirection = ANIMATE_FORWARDS;
            }
        });
    }

    private void setGlobalLayoutListener() {
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                /* Need this check because when we use ExpandableTextView in the UserFragment, this
                 * callback is executed before onLayout (not sure why), and as a result mMinHeight == -1
                 * and getLineCount returns 0, not allowing us to set the proper starting height on the TextView.
                 * This doesn't happen in VideoFragment, where onLayout is executed before this callback */
                if (mMinHeight > 0) {
                    setHeight(mMinHeight);
                    ViewTreeObserver obs = getViewTreeObserver();
                    obs.removeOnGlobalLayoutListener(this);
                    mListenersInitialized = false;
                }
            }
        });
    }

    private void onLayoutCalculations() {
        int lineHeight = getLineHeight();
        int extraHeight = (int) (0.15 * lineHeight);

        mMaxHeight = getLineCount()*lineHeight + extraHeight;
        mMinHeight = Math.min(mMaxHeight, lineHeight*COLLAPSED_TEXTVIEW_MAX_LINES + extraHeight);

        // minHeight == maxHeight so this textView won't be expandable (it's <= 2 lines in height)
        if (mMinHeight == mMaxHeight) {
            mIconImageView.setVisibility(View.GONE);
            setOnClickListener(null);
        }
    }

    private void expandTextView() {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator textAnimation = ObjectAnimator.ofInt(this, "height", mMaxHeight);
        textAnimation.setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateInterpolator());

        ObjectAnimator iconAnimation = ObjectAnimator.ofFloat(mIconImageView, "rotation", 0f, 180f);
        iconAnimation.setDuration(ANIMATION_DURATION);

        animatorSet.play(textAnimation)
                .with(iconAnimation);
        animatorSet.start();
    }

    private void collapseTextView() {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator textAnimation = ObjectAnimator.ofInt(this, "height", mMinHeight);
        textAnimation.setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateInterpolator());

        ObjectAnimator iconAnimation = ObjectAnimator.ofFloat(mIconImageView, "rotation", 180f, 0f);
        iconAnimation.setDuration(ANIMATION_DURATION);

        animatorSet.play(textAnimation)
                .with(iconAnimation);
        animatorSet.start();
    }
}
