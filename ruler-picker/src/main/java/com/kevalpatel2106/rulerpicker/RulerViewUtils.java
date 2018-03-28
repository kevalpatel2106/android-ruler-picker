package com.kevalpatel2106.rulerpicker;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Kevalpatel2106 on 28-Mar-18.
 * Utils classes for this library.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
final class RulerViewUtils {

    /**
     * Convert SP to pixel.
     *
     * @param context Context.
     * @param spValue Value in sp to convert.
     *
     * @return Value in pixels.
     */
    static int sp2px(@NonNull final Context context,
                     final float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
