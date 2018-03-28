package com.kevalpatel2106.rulerview;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Kevalpatel2106 on 28-Mar-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class RulerViewUtils {

    public static int sp2px(@NonNull final Context context,
                            final float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
