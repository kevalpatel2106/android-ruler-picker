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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by Kevalpatel2106 on 29-Mar-2018.
 *
 * @see <a href="https://github.com/dwfox/DWRulerView>Original Repo</a>
 */
public final class ObservableHorizontalScrollView extends HorizontalScrollView {

    private Runnable scrollerTask;

    private int initialPosition;

    private int newCheck = 100;

    @Nullable
    private ScrollChangedListener mScrollChangedListener;


    public ObservableHorizontalScrollView(@NonNull final Context context) {
        super(context);
        init();
    }

    public ObservableHorizontalScrollView(@NonNull final Context context,
                                          @Nullable final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ObservableHorizontalScrollView(@NonNull final Context context,
                                          @Nullable final AttributeSet attrs,
                                          final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ObservableHorizontalScrollView(@NonNull final Context context,
                                          @Nullable final AttributeSet attrs,
                                          final int defStyleAttr,
                                          final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        scrollerTask = new Runnable() {
            @Override
            public void run() {
                int newPosition = getScrollX();
                if (initialPosition - newPosition == 0) {//has stopped

                    if (mScrollChangedListener != null) {
                        mScrollChangedListener.onScrollStopped(getScrollX(), getScrollY());
                    }
                } else {
                    initialPosition = getScrollX();
                    ObservableHorizontalScrollView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };
    }

    @Override
    protected void onScrollChanged(final int horizontalOrigin,
                                   final int verticalOrigin,
                                   final int oldHorizontalOrigin,
                                   final int oldVerticalOrigin) {
        super.onScrollChanged(horizontalOrigin, verticalOrigin, oldHorizontalOrigin, oldVerticalOrigin);
        if (mScrollChangedListener != null) {
            mScrollChangedListener.onScrollChanged(this, horizontalOrigin, verticalOrigin);
        }
    }

    public void startScrollerTask() {
        initialPosition = getScrollX();
        ObservableHorizontalScrollView.this.postDelayed(scrollerTask, newCheck);
    }

    public void setScrollChangedListener(@NonNull final ScrollChangedListener listener) {
        mScrollChangedListener = listener;
    }
}



