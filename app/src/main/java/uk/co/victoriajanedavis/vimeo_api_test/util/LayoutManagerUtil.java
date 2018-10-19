package uk.co.victoriajanedavis.vimeo_api_test.util;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListAdapter;


public class LayoutManagerUtil {

    public static final int TAB_LAYOUT_SPAN_SIZE = 2;
    public static final int TAB_LAYOUT_ITEM_SPAN_SIZE = 1;


    public static RecyclerView.LayoutManager setUpLayoutManager(Context context, ListAdapter listAdapter, boolean isTabletLayout) {
        RecyclerView.LayoutManager layoutManager;
        if (!isTabletLayout) {
            layoutManager = new LinearLayoutManager(context);
        } else {
            layoutManager = initGridLayoutManager(context, listAdapter);
        }
        return layoutManager;
    }

    private static RecyclerView.LayoutManager initGridLayoutManager(Context context, ListAdapter listAdapter) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, TAB_LAYOUT_SPAN_SIZE);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (listAdapter.getItemViewType(position)) {
                    case ListAdapter.VIEW_TYPE_LOADING:
                        // If it is a loading view we wish to accomplish a single item per row
                        return TAB_LAYOUT_SPAN_SIZE;
                    default:
                        // Else, define the number of items per row as what we originally set.
                        return gridLayoutManager.getSpanCount();
                }
            }
        });
        return gridLayoutManager;
    }

    public static RecyclerView.LayoutManager setUpLayoutManager(Context context, boolean isTabletLayout) {
        RecyclerView.LayoutManager layoutManager;
        if (!isTabletLayout) {
            layoutManager = new LinearLayoutManager(context);
        } else {
            layoutManager = new GridLayoutManager(context, TAB_LAYOUT_SPAN_SIZE);
        }
        return layoutManager;
    }
}
