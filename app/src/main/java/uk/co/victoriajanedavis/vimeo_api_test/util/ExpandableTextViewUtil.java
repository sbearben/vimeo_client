package uk.co.victoriajanedavis.vimeo_api_test.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import uk.co.victoriajanedavis.vimeo_api_test.ui.ExpandableTextView;

public class ExpandableTextViewUtil {

    private static final String TAG = "ExpandableTextViewUtil";

    private static final int COLLPASED_TEXTVIEW_MAX_LINES = 2;
    private static final int ANIMATION_DURATION = 75;
    private static final boolean ANIMATE_FORWARDS = true;
    private static final boolean ANIMATE_BACKWARDS = false ;

    private ExpandableTextView mTextView;
    private ImageView mImageView;

    private boolean mAnimationDirection;

    private int mStartingHeight = -1;
    private int mMaxHeight;


    public ExpandableTextViewUtil (@NonNull ExpandableTextView tv, @NonNull ImageView iv) {
        mTextView = tv;
        mImageView = iv;
        mImageView.setVisibility(View.VISIBLE);

        mAnimationDirection = ANIMATE_FORWARDS;

        setTextViewClickListener();
        setTextViewLayoutListener();
        setTextViewGlobalLayoutListener();
    }

    private void setTextViewClickListener() {
        mTextView.setOnClickListener (v -> {
            if (mAnimationDirection == ANIMATE_FORWARDS) {
                expandTextView();
                mAnimationDirection = ANIMATE_BACKWARDS;
            } else {
                collapseTextView();
                mAnimationDirection = ANIMATE_FORWARDS;
            }
        });
    }

    private void setTextViewGlobalLayoutListener() {
        ViewTreeObserver vto = mTextView .getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mStartingHeight > 0) {
                    mTextView.setHeight(mStartingHeight);
                    ViewTreeObserver obs = mTextView.getViewTreeObserver();
                    obs.removeOnGlobalLayoutListener(this);
                }
            }

        });
    }

    private void setTextViewLayoutListener() {
        mTextView.setOnLayoutListener(v -> {

            /*
            Log.d(TAG, "getHeight(): " + mTextView.getHeight());
            Log.d(TAG, "getLineHeight(): " + mTextView.getLineHeight());
            Log.d(TAG, "getMaxHeight(): " + mTextView.getMaxHeight());
            Log.d(TAG, "getMinHeight(): " + mTextView.getMinHeight());
            Log.d(TAG, "getLineCount(): " + mTextView.getLineCount());
            Log.d(TAG, "getMaxLines(): " + mTextView.getMaxLines());
            Log.d(TAG, "getMinLines(): " + mTextView.getMinLines());
            */

            // This is a hacky way to get the max height since mTextView.getHeight() isn't working properly after the first time.
            mMaxHeight = mTextView.getLineCount()*mTextView.getLineHeight();
            mStartingHeight = Math.min(mMaxHeight, mTextView.getLineHeight()*COLLPASED_TEXTVIEW_MAX_LINES);

            /* The actual computations of getHeight() (when they work) always return 6 more than the algorithms above (mMaxHeight's calculation)
             * - I'm pretty sure this extra height is a function of lineHeight so that's what I'm attempting to replicate below
             * - for our font and textsize lineHeight is returning 40, and the extra height given by the system is 6 (15% of lineheight),
             *   so we're adding [15% * lineHeight] to the height
             */
            int extraHeight = (int) (0.15 * mTextView.getLineHeight());
            mMaxHeight += extraHeight;
            mStartingHeight += extraHeight;

            //Log.d(TAG, "maxHeight: " + mMaxHeight);
            //Log.d(TAG, "startingHeight: " + mStartingHeight);

            mTextView.setHeight(mStartingHeight);

            if (mStartingHeight == mMaxHeight) {
                mImageView.setVisibility(View.GONE);
                mTextView.setOnClickListener(null);
            }
            mTextView.setOnLayoutListener(null);
        });
    }

    private void expandTextView() {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator textAnimation = ObjectAnimator.ofInt(mTextView, "height", mMaxHeight);
        textAnimation.setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateInterpolator());

        ObjectAnimator iconAnimation = ObjectAnimator.ofFloat(mImageView, "rotation", 0f, 180f);
        iconAnimation.setDuration(ANIMATION_DURATION);

        animatorSet.play(textAnimation)
                .with(iconAnimation);
        animatorSet.start();
    }

    private void collapseTextView() {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator textAnimation = ObjectAnimator.ofInt(mTextView, "height", mStartingHeight);
        textAnimation.setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateInterpolator());

        ObjectAnimator iconAnimation = ObjectAnimator.ofFloat(mImageView, "rotation", 180f, 0f);
        iconAnimation.setDuration(ANIMATION_DURATION);

        animatorSet.play(textAnimation)
                .with(iconAnimation);
        animatorSet.start();
    }

    public void clear() {
        //collapseTextView();
        /*ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) mTextView.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mTextView.setLayoutParams(params);*/

        mTextView.setOnClickListener(null);
        mTextView = null;
        mImageView = null;
    }
}
