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

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


/**
 * Created by DWFOX on 2016-09-01.
 *
 * @see <a href="https://github.com/dwfox/DWRulerView>Original Repo</a>
 */
public class ScrollingValuePicker extends FrameLayout {

    private View mLeftSpacer;
    private View mRightSpacer;

    private LineRulerView lineRulerView;
    private ObservableHorizontalScrollView mHorizontalScrollView;
    private float viewMultipleSize = 3f;

    private float maxValue = 0;
    private float minValue = 0;

    private int valueMultiple = 1;
    private Paint mPathPaint;
    private Path mRulerStrokePath;

    public ScrollingValuePicker(Context context) {
        super(context);
        init(context);
    }

    public ScrollingValuePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollingValuePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollingValuePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void setOnScrollChangedListener(final ObservableHorizontalScrollView.OnScrollChangedListener onScrollChangedListener) {
        mHorizontalScrollView.setOnScrollChangedListener(onScrollChangedListener);
    }

    public void setMinMaxValue(float minValue, float maxValue) {
        setMinMaxValue(minValue, maxValue, 1);
    }

    public void setMinMaxValue(float minValue, float maxValue, @SuppressWarnings("SameParameterValue") int valueMultiple) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.valueMultiple = valueMultiple;
        lineRulerView.setMaxValue(this.maxValue);
        lineRulerView.setMinValue(this.minValue);
        lineRulerView.setValueMultiple(this.valueMultiple);
    }

    public void setValueTypeMultiple(int valueTypeMultiple) {
        this.valueMultiple = valueTypeMultiple;
        lineRulerView.setMultipleTypeValue(valueTypeMultiple);
    }

    public float getViewMultipleSize() {
        return this.viewMultipleSize;
    }

    public void setViewMultipleSize(float size) {
        this.viewMultipleSize = size;
    }

    private void init(Context context) {
        //Create the horizontal scrollbar
        addScrollbar(context);

        //Create the linear layout and add it to the horizontal view.
        prepareRuler(context);

        mPathPaint = new Paint();
        mPathPaint.setColor(Color.WHITE);
        mPathPaint.setStrokeWidth(5f);
        mPathPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mRulerStrokePath = new Path();
    }

    private void calculateRulerStrokePath() {
        mRulerStrokePath.moveTo(getWidth() / 2 - 30, 0);
        mRulerStrokePath.lineTo(getWidth() / 2, 40);
        mRulerStrokePath.lineTo(getWidth() / 2 + 30, 0);
    }

    private void prepareRuler(final Context context) {
        final LinearLayout container = new LinearLayout(context);
        mHorizontalScrollView.addView(container);

        mLeftSpacer = new View(context);
        mRightSpacer = new View(context);

        lineRulerView = new LineRulerView(context);
        container.addView(lineRulerView);
        container.addView(mLeftSpacer, 0);
        container.addView(mRightSpacer);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addScrollbar(final Context context) {
        mHorizontalScrollView = new ObservableHorizontalScrollView(context);
        mHorizontalScrollView.setHorizontalScrollBarEnabled(false);
        addView(mHorizontalScrollView);

        mHorizontalScrollView.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                mHorizontalScrollView.startScrollerTask();
            }
            return false;
        });
    }

    public ObservableHorizontalScrollView getHorizontalScrollView() {
        return mHorizontalScrollView;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mRulerStrokePath, mPathPaint);
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (changed) {
            final int width = getWidth();

            final ViewGroup.LayoutParams leftParams = mLeftSpacer.getLayoutParams();
            leftParams.width = width / 2;
            mLeftSpacer.setLayoutParams(leftParams);

            final ViewGroup.LayoutParams rulerViewParams = lineRulerView.getLayoutParams();
            rulerViewParams.width = (int) (width * viewMultipleSize);  // set RulerView Width
            lineRulerView.setLayoutParams(rulerViewParams);
            lineRulerView.invalidate();


            final ViewGroup.LayoutParams rightParams = mRightSpacer.getLayoutParams();
            rightParams.width = width / 2;
            mRightSpacer.setLayoutParams(rightParams);

            calculateRulerStrokePath();

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
    public synchronized void scrollToValue(float value) {
        mHorizontalScrollView.postDelayed(() -> {
            float oneValue = mHorizontalScrollView.getWidth() * viewMultipleSize / (maxValue - minValue);
            float valueWidth = oneValue * (value - minValue);

            mHorizontalScrollView.smoothScrollBy(Math.round(valueWidth), 0);
        }, 400);
    }
}
