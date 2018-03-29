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
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by Kevalpatel2106 on 29-Mar-2018.
 *
 * @see <a href="https://github.com/dwfox/DWRulerView>Original Repo</a>
 */
@SuppressLint("ViewConstructor")
final class ObservableHorizontalScrollView extends HorizontalScrollView {
    private static final long NEW_CHECK_DURATION = 100L;

    private int mInitialPosition;

    private Runnable mScrollerTask = new Runnable() {

        public void run() {
            if (mScrollChangedListener == null) return;

            int newPosition = getScrollY();
            if (mInitialPosition - newPosition == 0) {

                //Has stopped
                mScrollChangedListener.onScrollStopped();
            } else {

                mInitialPosition = getScrollY();
                postDelayed(mScrollerTask, NEW_CHECK_DURATION);
            }
        }
    };

    @Nullable
    private ScrollChangedListener mScrollChangedListener;

    public ObservableHorizontalScrollView(@NonNull final Context context,
                                          @NonNull final ScrollChangedListener listener) {
        super(context);
        mScrollChangedListener = listener;

        setOnTouchListener(new OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mInitialPosition = getScrollY();
                    postDelayed(mScrollerTask, NEW_CHECK_DURATION);
                }
                return false;
            }
        });
    }

    void scrollToValue(final int value, final int intervalSize, final int minValue) {
        post(new Runnable() {
            @Override
            public void run() {
                int valuesToScroll = minValue;
                if (value < minValue) valuesToScroll = value - minValue;

                smoothScrollBy(valuesToScroll * intervalSize, 0);
            }
        });
    }

    @Override
    protected void onScrollChanged(final int horizontalOrigin,
                                   final int verticalOrigin,
                                   final int oldHorizontalOrigin,
                                   final int oldVerticalOrigin) {
        super.onScrollChanged(horizontalOrigin, verticalOrigin, oldHorizontalOrigin, oldVerticalOrigin);
        if (mScrollChangedListener == null) return;
        mScrollChangedListener.onScrollChanged();
    }

    void makeOffsetCorrection(int indicatorInterval) {
        int offsetValue = getScrollX() % indicatorInterval;
        System.out.println(offsetValue);

        if (offsetValue < indicatorInterval / 2) {
            scrollBy(-offsetValue, 0);
        } else {
            scrollBy(indicatorInterval - offsetValue, 0);
        }

    }

    public interface ScrollChangedListener {
        void onScrollChanged();

        void onScrollStopped();
    }
}



