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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Keval Patel on 28 Mar 2018.
 */

public class RulerView extends View {
    //TODO remove
    private final float PX_14SP;

    public static final int DISPLAY_NUMBER_TYPE_MULTIPLE = 2;
    private final int longHeightRatio = 6;
    private final int shortHeightRatio = 4;
    /**
     * Width of the view. This view height is measured in {@link #onMeasure(int, int)}.
     *
     * @see #onMeasure(int, int)
     */
    private int mViewWidth;
    /**
     * Height of the view. This view height is measured in {@link #onMeasure(int, int)}.
     *
     * @see #onMeasure(int, int)
     */
    private int mViewHeight;
    /**
     * {@link Paint} for the line in the ruler view.
     */
    private Paint mLinePaint;
    /**
     * {@link Paint} to display the text on the ruler view.
     */
    private Paint mTextPaint;
    /**
     * Distance interval between two subsequent dash on the ruler.
     */
    private int mDashInterval = 14 /* Default dash interval */;
    private int mMinValue = 0 /* Default minimum value */;
    private int mMaxValue = 100 /* Default maximum value */;


    public RulerView(@NonNull final Context context) {
        super(context);
        PX_14SP = RulerViewUtils.sp2px(context, 14);

        init();
    }

    public RulerView(@NonNull final Context context,
                     @NonNull final AttributeSet attrs) {
        super(context, attrs);
        PX_14SP = RulerViewUtils.sp2px(context, 14);

        init();
    }

    public RulerView(@NonNull final Context context,
                     @NonNull final AttributeSet attrs,
                     final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        PX_14SP = RulerViewUtils.sp2px(context, 14);

        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RulerView(@NonNull final Context context,
                     @NonNull final AttributeSet attrs,
                     int defStyleAttr,
                     int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
        PX_14SP = RulerViewUtils.sp2px(context, 14);

        init();
    }

    /**
     * Initialize the line ruler view.
     */
    private void init() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.WHITE);   //TODO make it dynamic
        mLinePaint.setStrokeWidth(4f);      //TODO make it dynamic
        mLinePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);   //TODO make it dynamic
        mTextPaint.setTextSize(PX_14SP);    //TODO make it dynamic
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * Set the maximum value to display on the ruler.
     *
     * @param minValue Value to display at the left end of the ruler. This can be positive, negative
     *                 or zero.
     * @param maxValue Value to display at the right end of the ruler. This can be positive, negative
     *                 or zero.This value must be greater than min value.
     */
    public void setValueRange(final int minValue, final int maxValue) {
        mMinValue = minValue;
        mMaxValue = maxValue;
        invalidate();
    }

    public void setDashInterval(final int dashIntervalPx) {
        mDashInterval = dashIntervalPx;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Draw the first dash
        drawSmallDash(canvas, 0);

        //Iterate through all value
        for (int value = 1; value < mMaxValue - mMinValue; value++) {

            if (value % 5 == 0) {
                drawLongDash(canvas, value);
                drawDashText(canvas, value);
            } else {
                drawSmallDash(canvas, value);
            }
        }

        //Draw the last dash.
        drawSmallDash(canvas, mViewWidth);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Measure dimensions
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);

        this.setMeasuredDimension(mViewWidth, mViewHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void drawSmallDash(@NonNull final Canvas canvas,
                               final int value) {
        canvas.drawLine(mDashInterval * value,
                0,
                mDashInterval * value,
                mViewHeight / longHeightRatio,
                mLinePaint);
    }

    private void drawLongDash(@NonNull final Canvas canvas,
                              final int value) {
        canvas.drawLine(mDashInterval * value,
                0,
                mDashInterval * value,
                mViewHeight / shortHeightRatio,
                mLinePaint);
    }

    private void drawDashText(@NonNull final Canvas canvas,
                              final int value) {
        canvas.drawText(String.valueOf((value + mMinValue)),
                mDashInterval * value,
                mViewHeight / shortHeightRatio + PX_14SP,
                mTextPaint);
    }
}

