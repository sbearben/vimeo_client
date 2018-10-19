/*
 * Copyright (c) Joaquim Ley 2016. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.victoriajanedavis.vimeo_api_test.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class DisplayMetricsUtil {

    private static final int SCREEN_TABLET_DP_WIDTH = 600;
    public static float VIDEO_ASPECT_RATIO = ((float)16)/9;


    public static boolean isTabletLayout() {
        return isScreenW(SCREEN_TABLET_DP_WIDTH);
    }

    // Return true if the width in DP of the device is equal or greater than the given value
    private static boolean isScreenW(int widthDp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels / displayMetrics.density;
        return screenWidth >= widthDp;
    }

    public static Dimensions getScreenDimensions() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return new Dimensions(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public static Dimensions getDimensionsRequiredToFillWidthMaintainingAspectRatio(
            Dimensions screenDimensions, boolean isTabletLayout) {
        int width, height;

        if (isTabletLayout) width = screenDimensions.getWidth()/LayoutManagerUtil.TAB_LAYOUT_SPAN_SIZE;
        else width = screenDimensions.getWidth();
        height = (int) (width/VIDEO_ASPECT_RATIO);

        return new Dimensions(width, height);
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static class Dimensions {

        private int mWidth;
        private int mHeight;

        public Dimensions(int width, int height) {
            mWidth = width;
            mHeight = height;
        }

        public int getWidth() {
            return mWidth;
        }

        public int getHeight() {
            return mHeight;
        }
    }

}