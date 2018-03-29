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
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


/**
 * Created by Kevalpatel2106 on 29-Mar-2018.
 *
 *  <li>Diagram:</li>
 * Observable ScrollView
 * |------------------|---------------------\--/----------------------|------------------|<br/>
 * |                  |                      \/                       |                  |<br/>
 * |                  |                                               |                  |<br/>
 * |  Left Spacer     |                 RulerView                     |  Right Spacer    |<br/>
 * |                  |                                               |                  |<br/>
 * |                  |                                               |                  |<br/>
 * |------------------|-----------------------------------------------|------------------|<br/>

 * @see <a href="https://github.com/dwfox/DWRulerView>Original Repo</a>
 */
public class RulerValuePicker extends FrameLayout {

    /**
     * Left side empty view to add padding to the ruler.
     * <p>
     * <li>Diagram:</li>
     * Observable ScrollView
     * |------------------|-----------------------------------------------|------------------|<br/>
     * |                  |                                               |                  |<br/>
     * |                  |                                               |                  |<br/>
     * |  Left Spacer     |                 RulerView                     |  Right Spacer    |<br/>
     * |                  |                                               |                  |<br/>
     * |                  |                                               |                  |<br/>
     * |------------------|-----------------------------------------------|------------------|<br/>
     */
    private View mLeftSpacer;

    /**
     * Right side empty view to add padding to the ruler.
     * <p>
     * <li>Diagram:</li>
     * Observable ScrollView
     * |------------------|-----------------------------------------------|------------------|<br/>
     * |                  |                                               |                  |<br/>
     * |                  |                                               |                  |<br/>
     * |  Left Spacer     |                 RulerView                     |  Right Spacer    |<br/>
     * |                  |                                               |                  |<br/>
     * |                  |                                               |                  |<br/>
     * |------------------|-----------------------------------------------|------------------|<br/>
     */
    private View mRightSpacer;

    /**
     * Ruler view with values.
     * <p>
     * <li>Diagram:</li>
     * Observable ScrollView
     * |------------------|-----------------------------------------------|------------------|<br/>
     * |                  |                                               |                  |<br/>
     * |                  |                                               |                  |<br/>
     * |  Left Spacer     |                 RulerView                     |  Right Spacer    |<br/>
     * |                  |                                               |                  |<br/>
     * |                  |                                               |                  |<br/>
     * |------------------|-----------------------------------------------|------------------|<br/>
     */
    private RulerView mRulerView;

    /**
     * {@link ObservableHorizontalScrollView}, that will host all three components.
     *
     * @see #mLeftSpacer
     * @see #mRightSpacer
     * @see #mRulerView
     */
    private ObservableHorizontalScrollView mHorizontalScrollView;
    private float viewMultipleSize = 3f;

    private float maxValue = 0;
    private float minValue = 0;

    private Paint mNotchPaint;
    private Path mNotchPath;

    public RulerValuePicker(@NonNull final Context context) {
        super(context);
        init();
    }

    public RulerValuePicker(@NonNull final Context context,
                            @Nullable final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RulerValuePicker(@NonNull final Context context,
                            @Nullable final AttributeSet attrs,
                            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RulerValuePicker(@NonNull final Context context,
                            @Nullable final AttributeSet attrs,
                            final int defStyleAttr,
                            final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setOnScrollChangedListener(final ScrollChangedListener onScrollChangedListener) {
        mHorizontalScrollView.setScrollChangedListener(onScrollChangedListener);
    }

    public void setMinMaxValue(int minValue, int maxValue) {
        mRulerView.setValueRange(minValue, maxValue);
    }

    private void init() {
        //Create the horizontal scrollbar
        addScrollbar();

        //Create the linear layout and add it to the horizontal view.
        addRuler();

        //Prepare the notch color.
        mNotchPaint = new Paint();
        mNotchPaint.setColor(Color.WHITE);      //TODO make dynamic
        mNotchPaint.setStrokeWidth(5f);         //TODO make dynamic
        mNotchPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mNotchPath = new Path();
    }

    private void calculateNotchPath() {
        mNotchPath.moveTo(getWidth() / 2 - 30, 0);
        mNotchPath.lineTo(getWidth() / 2, 40);
        mNotchPath.lineTo(getWidth() / 2 + 30, 0);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void addScrollbar() {
        mHorizontalScrollView = new ObservableHorizontalScrollView(getContext());
        mHorizontalScrollView.setHorizontalScrollBarEnabled(false); //Don't display the scrollbar

        addView(mHorizontalScrollView);

        mHorizontalScrollView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mHorizontalScrollView.startScrollerTask();
                }
                return false;
            }
        });
    }

    /**
     * You must have to call {@link #addScrollbar()} before this method to create {@link #mHorizontalScrollView}
     * in which all these views will be added.
     */
    private void addRuler() {
        final LinearLayout rulerContainer = new LinearLayout(getContext());

        //Add left spacing to the container
        mLeftSpacer = new View(getContext());
        rulerContainer.addView(mLeftSpacer);

        //Add ruler to the container
        mRulerView = new RulerView(getContext());
        rulerContainer.addView(mRulerView);

        //Add right spacing to the container
        mRightSpacer = new View(getContext());
        rulerContainer.addView(mRightSpacer);

        //Add this container to the scroll view.
        mHorizontalScrollView.addView(rulerContainer);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //Draw the top notch
        canvas.drawPath(mNotchPath, mNotchPaint);
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (changed) {
            final int width = getWidth();

            //Set width of the left spacer to the half of this view.
            final ViewGroup.LayoutParams leftParams = mLeftSpacer.getLayoutParams();
            leftParams.width = width / 2;
            mLeftSpacer.setLayoutParams(leftParams);

            //Set width of the right spacer to the half of this view.
            final ViewGroup.LayoutParams rightParams = mRightSpacer.getLayoutParams();
            rightParams.width = width / 2;
            mRightSpacer.setLayoutParams(rightParams);

            calculateNotchPath();

            invalidate();
        }
    }

    /**
     * Get the currently selected value in the value picker.
     *
     * @return Current value without offset adjustment.
     */
    public int getCurrentValue(int l) {
        float oneValue = (float) mHorizontalScrollView.getWidth() * viewMultipleSize / (maxValue - minValue);
        int value = Math.round(l / oneValue) + (int) minValue;

        if (value > maxValue) value = (int) maxValue;
        else if (value < minValue) value = (int) minValue;

        return value;
    }

    /**
     * Get the currently selected value in the value picker and move that value to the center of
     * the ruler and adjust offsets.
     *
     * @return value selected with offset adjustment. (This value will be whole integer always.)
     */
    public int getValueAndScrollItemToCenter(int l) {
        float oneValue = (float) mHorizontalScrollView.getWidth() * viewMultipleSize / (maxValue - minValue);
        int value = Math.round(l / oneValue) + (int) minValue;

        //Calculate and adjust the offset
        float offset = (l % oneValue);
        if (offset > oneValue / 2) {
            mHorizontalScrollView.smoothScrollBy(Math.round(oneValue - offset), 0);
        } else {
            mHorizontalScrollView.smoothScrollBy(Math.round(-offset), 0);
        }

        if (value > maxValue) value = (int) maxValue;

        return value;
    }

    /**
     * Scroll the ruler to the given value.
     */
    public synchronized void scrollToValue(final float value) {
        mHorizontalScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                float oneValue = mHorizontalScrollView.getWidth() * viewMultipleSize / (maxValue - minValue);
                float valueWidth = oneValue * (value - minValue);

                mHorizontalScrollView.smoothScrollBy(Math.round(valueWidth), 0);
            }
        }, 400);
    }
}
