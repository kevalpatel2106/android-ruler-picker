/*
 * Copyright 2018 Keval Patel
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance wit
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
 *  the specific language governing permissions and limitations under the License.
 */

package com.kevalpatel2106.rulerpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.HorizontalScrollView;

/**
 * Created by Kevalpatel2106 on 29-Mar-2018.
 * A {@link HorizontalScrollView} which has ability to detect start/stop scrolling.
 *
 * @see <a href="https://github.com/dwfox/DWRulerView>Original Repo</a>
 */
@SuppressLint("ViewConstructor")
final class ObservableHorizontalScrollView extends HorizontalScrollView {
    private static final long NEW_CHECK_DURATION = 100L;

    private long mLastScrollUpdateMills = -1;

    @Nullable
    private ScrollChangedListener mScrollChangedListener;

    private Runnable mScrollerTask = new Runnable() {

        public void run() {
            if (System.currentTimeMillis() - mLastScrollUpdateMills > NEW_CHECK_DURATION) {
                mLastScrollUpdateMills = -1;
                mScrollChangedListener.onScrollStopped();
            } else {
                //Post next delay
                postDelayed(this, NEW_CHECK_DURATION);
            }
        }
    };

    /**
     * Constructor.
     *
     * @param context  {@link Context} of caller.
     * @param listener {@link ScrollChangedListener} to get callbacks when scroll starts or stops.
     * @see ScrollChangedListener
     */
    public ObservableHorizontalScrollView(@NonNull final Context context,
                                          @NonNull final ScrollChangedListener listener) {
        super(context);
        mScrollChangedListener = listener;
    }

    @Override
    protected void onScrollChanged(final int horizontalOrigin,
                                   final int verticalOrigin,
                                   final int oldHorizontalOrigin,
                                   final int oldVerticalOrigin) {
        super.onScrollChanged(horizontalOrigin, verticalOrigin, oldHorizontalOrigin, oldVerticalOrigin);
        if (mScrollChangedListener == null) return;
        mScrollChangedListener.onScrollChanged();

        if (mLastScrollUpdateMills == -1) postDelayed(mScrollerTask, NEW_CHECK_DURATION);
        mLastScrollUpdateMills = System.currentTimeMillis();
    }

    /**
     * Listener to get callbacks on scrollview scroll events.
     */
    interface ScrollChangedListener {

        /**
         * Called upon change in scroll position.
         */
        void onScrollChanged();

        /**
         * Called when the scrollview stops scrolling.
         */
        void onScrollStopped();
    }
}



