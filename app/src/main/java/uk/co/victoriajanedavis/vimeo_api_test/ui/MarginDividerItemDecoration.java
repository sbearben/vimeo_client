package uk.co.victoriajanedavis.vimeo_api_test.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.support.annotation.DimenRes;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import uk.co.victoriajanedavis.vimeo_api_test.R;

public class MarginDividerItemDecoration extends DividerItemDecoration {

    public MarginDividerItemDecoration (Context context, int orientation, @DimenRes int id) {
        super(context, orientation);

        int[] ATTRS = new int[]{android.R.attr.listDivider};

        TypedArray a = context.obtainStyledAttributes(ATTRS);
        Drawable divider = a.getDrawable(0);
        int inset = context.getResources().getDimensionPixelSize(id);
        InsetDrawable insetDivider = new InsetDrawable(divider, inset, 0, inset, 0);
        a.recycle();

        setDrawable(insetDivider);
    }
}
