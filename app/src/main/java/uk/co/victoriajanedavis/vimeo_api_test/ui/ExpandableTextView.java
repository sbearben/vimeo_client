package uk.co.victoriajanedavis.vimeo_api_test.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

// To be used with ExpandableTextViewUtil
public class ExpandableTextView extends AppCompatTextView {

    private static final String TAG = "ExpandableTextView";
    private static final int LINE_COUNT_UNINITIALIZED = -1;

    private int mLineCount = LINE_COUNT_UNINITIALIZED;
    private OnLayoutListener mLayoutListener;


    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mLineCount == LINE_COUNT_UNINITIALIZED && getLineCount() != 0 && mLayoutListener != null) {
            mLineCount = this.getLineCount();
            mLayoutListener.onLayout(this);
            mLayoutListener = null;
            mLineCount = LINE_COUNT_UNINITIALIZED;
        }
    }

    public void setOnLayoutListener(OnLayoutListener listener) {
        mLayoutListener = listener;
    }

    public interface OnLayoutListener {
        void onLayout(View v);
    }
}
