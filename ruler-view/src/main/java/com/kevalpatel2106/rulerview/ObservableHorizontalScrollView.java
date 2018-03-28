/*
 *  Copyright 2018 Keval Patel.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.kevalpatel2106.rulerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by DWFOX on 2016-09-01.
 *
 * @see <a href="https://github.com/dwfox/DWRulerView>Original Repo</a>
 */
public class ObservableHorizontalScrollView extends HorizontalScrollView {

    private Runnable scrollerTask;
    private int initialPosition;

    private int newCheck = 100;
    private OnScrollChangedListener mOnScrollChangedListener;


    public ObservableHorizontalScrollView(Context context) {
        super(context);
        init();
    }

    public ObservableHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ObservableHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ObservableHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setOnScrollChangedListener(OnScrollChangedListener l) {
        mOnScrollChangedListener = l;
    }

    private void init() {
        scrollerTask = () -> {
            int newPosition = getScrollX();
            if (initialPosition - newPosition == 0) {//has stopped

                if (mOnScrollChangedListener != null) {
                    mOnScrollChangedListener.onScrollStopped(getScrollX(), getScrollY());
                }
            } else {
                initialPosition = getScrollX();
                ObservableHorizontalScrollView.this.postDelayed(scrollerTask, newCheck);
            }
        };
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t);
        }
    }

    public void startScrollerTask() {

        initialPosition = getScrollY();
        ObservableHorizontalScrollView.this.postDelayed(scrollerTask, newCheck);
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(ObservableHorizontalScrollView view, int l, int t);

        void onScrollStopped(int l, int t);
    }
}



