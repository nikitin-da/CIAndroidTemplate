package com.example.testjacoco.test_jacoco;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Ivan_Kobzarev on 07.03.2014
 */

/**
 * Utility class for views manipulation.
 */
public final class ViewUtil {

    /**
     * For screens with density 160dpi 1dp = 1px.
     */
    private static final float DP_STRAIGHT_RATIO_DENSITY = 160f;

    /**
     * Due to class is utility - private empty constructor.
     */
    private ViewUtil() {
    }

    /**
     * If param visibility True and view.getVisibility not VISIBLE => view setted visibility VISIBLE,
     * if param visibility False and view.getVisibility not GONE => view setted visibility GONE.
     *
     * @param view       View
     * @param visibility visibility flag
     */
    public static void setVisibility(View view, boolean visibility) {

        if (view == null) {
            return;
        }

        boolean currentVisibility = view.getVisibility() == View.VISIBLE;
        if (currentVisibility != visibility) {
            if (visibility) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return dp * (metrics.densityDpi / DP_STRAIGHT_RATIO_DENSITY);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / DP_STRAIGHT_RATIO_DENSITY);
    }


    /**
     * Set enabled state recursively for all descendants of ViewGroup.
     *
     * @param viewGroup view group
     * @param enabled   enabled value to set
     */
    public static void setEnabled(ViewGroup viewGroup, boolean enabled) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            child.setEnabled(enabled);
            if (child instanceof ViewGroup) {
                setEnabled((ViewGroup) child, enabled);
            }
        }
    }
}
