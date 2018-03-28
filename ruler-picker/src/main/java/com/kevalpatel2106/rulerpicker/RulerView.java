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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Keval Patel on 28 Mar 2018.
 * <p>
 * This is custom {@link View} which will draw a ruler with indicators.
 * There are two types of indicators:
 * <li><b>Long Indicators:</b> These indicators marks specific important value after some periodic interval.
 * e.g. Long indicator represents evert 10th (10, 20, 30...) value.</li>
 * <li><b>Short Indicators:</b> There indicators represents single value.</li>
 */

final class RulerView extends View {

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
     *
     * @see #refreshPaint()
     */
    private Paint mIndicatorPaint;

    /**
     * {@link Paint} to display the text on the ruler view.
     *
     * @see #refreshPaint()
     */
    private Paint mTextPaint;

    /**
     * Distance interval between two subsequent indicators on the ruler.
     *
     * @see #setIndicatorIntervalDistance(int)
     * @see #getIndicatorIntervalWidth()
     */
    private int mIndicatorInterval = 14 /* Default value */;

    /**
     * Minimum value. This value will be displayed at the left-most end of the ruler. This value
     * must be less than {@link #mMaxValue}.
     *
     * @see #setValueRange(int, int)
     * @see #getMinValue()
     */
    private int mMinValue = 0 /* Default value */;

    /**
     * Maximum value. This value will be displayed at the right-most end of the ruler. This value
     * must be greater than {@link #mMinValue}.
     *
     * @see #setValueRange(int, int)
     * @see #getMaxValue()
     */
    private int mMaxValue = 100 /* Default maximum value */;

    /**
     * Ratio of long indicator height to the ruler height. This value must be between 0 to 1. The
     * value should greater than {@link #mShortIndicatorHeight}. Default value is 0.6 (i.e. 60%).
     * If the value is 0, indicator won't be displayed. If the value is 1, indicator height will be
     * same as the ruler height.
     *
     * @see #setIndicatorHeight(float, float)
     * @see #getLongIndicatorHeightRatio()
     */
    private float mLongIndicatorHeightRatio = 0.6f /* Default value */;

    /**
     * Ratio of short indicator height to the ruler height. This value must be between 0 to 1. The
     * value should less than {@link #mLongIndicatorHeight}. Default value is 0.4 (i.e. 40%).
     * If the value is 0, indicator won't be displayed. If the value is 1, indicator height will be
     * same as the ruler height.
     *
     * @see #setIndicatorHeight(float, float)
     * @see #getShortIndicatorHeightRatio()
     */
    private float mShortIndicatorHeightRatio = 0.4f /* Default value */;

    /**
     * Actual height of the long indicator in pixels. This height is derived from
     * {@link #mLongIndicatorHeightRatio}.
     *
     * @see #updateIndicatorHeight(float, float)
     */
    private int mLongIndicatorHeight = 0;

    /**
     * Actual height of the short indicator in pixels. This height is derived from
     * {@link #mShortIndicatorHeightRatio}.
     *
     * @see #updateIndicatorHeight(float, float)
     */
    private int mShortIndicatorHeight = 0;

    /**
     * Integer color of the text, that is displayed on the ruler.
     *
     * @see #setTextColor(int)
     * @see #setTextColorRes(int)
     * @see #getTextColor()
     */
    @ColorInt
    private int mTextColor = Color.WHITE;

    /**
     * Integer color of the indicators.
     *
     * @see #setIndicatorColor(int)
     * @see #setIndicatorColorRes(int)
     * @see #getIndicatorColor()
     */
    @ColorInt
    private int mIndicatorColor = Color.WHITE;

    /**
     * Height of the text, that is displayed on ruler in dp.
     *
     * @see #setTextSize(int)
     * @see #setTextSizeRes(int)
     * @see #getTextSize()
     */
    @Dimension
    private float mTextSize = 14f;

    /**
     * Width of the indicator in dp.
     *
     * @see #setIndicatorWidth(int)
     * @see #setIndicatorWidthRes(int)
     * @see #getIndicatorWidth()
     */
    @Dimension
    private float mIndicatorWidth = 4f;

    public RulerView(@NonNull final Context context) {
        super(context);
        refreshPaint();
    }

    public RulerView(@NonNull final Context context,
                     @NonNull final AttributeSet attrs) {
        super(context, attrs);
        refreshPaint();
    }

    public RulerView(@NonNull final Context context,
                     @NonNull final AttributeSet attrs,
                     final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        refreshPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RulerView(@NonNull final Context context,
                     @NonNull final AttributeSet attrs,
                     int defStyleAttr,
                     int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
        refreshPaint();
    }

    /**
     * Create the indicator paint and value text color.
     */
    private void refreshPaint() {
        mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mIndicatorPaint.setColor(mIndicatorColor);
        mIndicatorPaint.setStrokeWidth(RulerViewUtils.sp2px(getContext(), mIndicatorWidth));
        mIndicatorPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(RulerViewUtils.sp2px(getContext(), mTextSize));
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Iterate through all value
        for (int value = 1; value < mMaxValue - mMinValue; value++) {

            if (value % 5 == 0) {
                drawLongIndicator(canvas, value);
                drawValueText(canvas, value);
            } else {
                drawSmallIndicator(canvas, value);
            }
        }

        //Draw the first indicator.
        drawSmallIndicator(canvas, 0);

        //Draw the last indicator.
        drawSmallIndicator(canvas, mViewWidth);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Measure dimensions
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);

        updateIndicatorHeight(mLongIndicatorHeightRatio, mShortIndicatorHeightRatio);

        this.setMeasuredDimension(mViewWidth, mViewHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Calculate and update the height of the long and the short indicators based on new ratios.
     *
     * @param longIndicatorHeightRatio  Ratio of long indicator height to the ruler height.
     * @param shortIndicatorHeightRatio Ratio of short indicator height to the ruler height.
     */
    private void updateIndicatorHeight(final float longIndicatorHeightRatio,
                                       final float shortIndicatorHeightRatio) {
        mLongIndicatorHeight = (int) (mViewHeight * longIndicatorHeightRatio);
        mShortIndicatorHeight = (int) (mViewHeight * shortIndicatorHeightRatio);

    }

    /**
     * Draw the vertical short line at every value.
     *
     * @param canvas {@link Canvas} on which the line will be drawn.
     * @param value  Value to calculate the position of the indicator.
     */
    private void drawSmallIndicator(@NonNull final Canvas canvas,
                                    final int value) {
        canvas.drawLine(mIndicatorInterval * value,
                0,
                mIndicatorInterval * value,
                mShortIndicatorHeight,
                mIndicatorPaint);
    }

    /**
     * Draw the vertical long line.
     *
     * @param canvas {@link Canvas} on which the line will be drawn.
     * @param value  Value to calculate the position of the indicator.
     */
    private void drawLongIndicator(@NonNull final Canvas canvas,
                                   final int value) {
        canvas.drawLine(mIndicatorInterval * value,
                0,
                mIndicatorInterval * value,
                mLongIndicatorHeight,
                mIndicatorPaint);
    }

    /**
     * Draw the value number below the longer indicator. This will use {@link #mTextPaint} to draw
     * the text.
     *
     * @param canvas {@link Canvas} on which the text will be drawn.
     * @param value  Value to draw.
     */
    private void drawValueText(@NonNull final Canvas canvas,
                               final int value) {
        canvas.drawText(String.valueOf(value + mMinValue),
                mIndicatorInterval * value,
                mLongIndicatorHeight + mTextPaint.getTextSize(),
                mTextPaint);
    }


    /////////////////////// Properties getter/setter ///////////////////////

    /**
     * Set the color of the text to display on the ruler.
     *
     * @param color Color resource id.
     */
    public void setTextColorRes(@ColorRes int color) {
        mTextColor = ContextCompat.getColor(getContext(), color);
        refreshPaint();
    }

    /**
     * Set the size of the text to display on the ruler.
     *
     * @param dimension Text size dimension resource.
     */
    public void setTextSizeRes(@DimenRes int dimension) {
        mTextSize = getContext().getResources().getDimension(dimension);
        refreshPaint();
    }

    /**
     * Set the indicator color.
     *
     * @param color Color resource id.
     */
    public void setIndicatorColorRes(@ColorRes int color) {
        mIndicatorColor = ContextCompat.getColor(getContext(), color);
        refreshPaint();
    }

    /**
     * Set the width of the indicator line in the ruler.
     *
     * @param width Dimension resource for indicator width.
     */
    public void setIndicatorWidthRes(@DimenRes int width) {
        mIndicatorWidth = getContext().getResources().getDimension(width);
        refreshPaint();
    }

    /**
     * Set the maximum value to display on the ruler. This will decide the range of values and number
     * of indicators that ruler will draw.
     *
     * @param minValue Value to display at the left end of the ruler. This can be positive, negative
     *                 or zero. Default minimum value is 0.
     * @param maxValue Value to display at the right end of the ruler. This can be positive, negative
     *                 or zero.This value must be greater than min value. Default minimum value is 100.
     */
    public void setValueRange(final int minValue, final int maxValue) {
        mMinValue = minValue;
        mMaxValue = maxValue;
        invalidate();
    }

    /**
     * Set the spacing between two vertical lines/indicators. Default value is 14 pixels.
     *
     * @param indicatorIntervalPx Distance in pixels. This cannot be negative number or zero.
     *
     * @throws IllegalArgumentException if interval is negative or zero.
     */
    public void setIndicatorIntervalDistance(final int indicatorIntervalPx) {
        if (indicatorIntervalPx <= 0)
            throw new IllegalArgumentException("Interval cannot be negative or zero.");

        mIndicatorInterval = indicatorIntervalPx;
        invalidate();
    }

    /**
     * Set the height of the long and short indicators.
     *
     * @param longHeightRatio  Ratio of long indicator height to the ruler height. This value must
     *                         be between 0 to 1. The value should greater than {@link #mShortIndicatorHeight}.
     *                         Default value is 0.6 (i.e. 60%). If the value is 0, indicator won't
     *                         be displayed. If the value is 1, indicator height will be same as the
     *                         ruler height.
     * @param shortHeightRatio Ratio of short indicator height to the ruler height. This value must
     *                         be between 0 to 1. The value should less than {@link #mLongIndicatorHeight}.
     *                         Default value is 0.4 (i.e. 40%). If the value is 0, indicator won't
     *                         be displayed. If the value is 1, indicator height will be same as
     *                         the ruler height.
     *
     * @throws IllegalArgumentException if any of the parameter is invalid.
     */
    public void setIndicatorHeight(final float longHeightRatio,
                                   final float shortHeightRatio) {

        if (shortHeightRatio < 0 || shortHeightRatio > 1) {
            throw new IllegalArgumentException("Sort indicator height must be between 0 to 1.");
        }

        if (longHeightRatio < 0 || longHeightRatio > 1) {
            throw new IllegalArgumentException("Long indicator height must be between 0 to 1.");
        }

        if (shortHeightRatio > longHeightRatio) {
            throw new IllegalArgumentException("Long indicator height cannot be less than sort indicator height.");
        }

        mLongIndicatorHeightRatio = longHeightRatio;
        mShortIndicatorHeightRatio = shortHeightRatio;

        updateIndicatorHeight(mLongIndicatorHeightRatio, mShortIndicatorHeightRatio);

        invalidate();
    }

    /**
     * @return Color integer value of the indicator color.
     *
     * @see #setIndicatorColor(int)
     * @see #setIndicatorColorRes(int)
     */
    @CheckResult
    @ColorInt
    private int getIndicatorColor() {
        return mIndicatorColor;
    }

    /**
     * Set the indicator color.
     *
     * @param color Color integer value.
     */
    public void setIndicatorColor(@ColorInt int color) {
        mIndicatorColor = color;
        refreshPaint();
    }

    /**
     * @return Width of the indicator in dp.
     *
     * @see #setIndicatorWidth(int)
     * @see #setIndicatorWidthRes(int)
     */
    @CheckResult
    private float getIndicatorWidth() {
        return mIndicatorWidth;
    }

    /**
     * Set the width of the indicator line in the ruler.
     *
     * @param widthDp Width in dp.
     */
    public void setIndicatorWidth(@DimenRes int widthDp) {
        mIndicatorWidth = widthDp;
        refreshPaint();
    }

    /**
     * @return Color integer value of the ruler text color.
     *
     * @see #setTextColor(int)
     * @see #setTextColorRes(int)
     */
    @CheckResult
    @ColorInt
    private int getTextColor() {
        return mIndicatorColor;
    }

    /**
     * Set the color of the text to display on the ruler.
     *
     * @param color Color integer value.
     */
    public void setTextColor(@ColorInt int color) {
        mTextColor = color;
        refreshPaint();
    }

    /**
     * @return Size of the text of ruler in dp.
     *
     * @see #setTextSize(int)
     * @see #setTextSizeRes(int)
     */
    @CheckResult
    private float getTextSize() {
        return mTextSize;
    }

    /**
     * Set the size of the text to display on the ruler.
     *
     * @param dimensionDp Text size dimension in dp.
     */
    public void setTextSize(@DimenRes int dimensionDp) {
        mTextSize = dimensionDp;
        refreshPaint();
    }

    /**
     * @return Get distance between two indicator in pixels.
     *
     * @see #setIndicatorIntervalDistance(int)
     */
    @CheckResult
    private int getIndicatorIntervalWidth() {
        return mIndicatorInterval;
    }

    /**
     * @return Get the minimum value displayed on the ruler.
     *
     * @see #setValueRange(int, int)
     */
    @CheckResult
    private int getMinValue() {
        return mMinValue;
    }

    /**
     * @return Get the maximum value displayed on the ruler.
     *
     * @see #setValueRange(int, int)
     */
    @CheckResult
    private int getMaxValue() {
        return mMaxValue;
    }

    /**
     * @return Ratio of long indicator height to the ruler height.
     *
     * @see #setIndicatorHeight(float, float)
     */
    @CheckResult
    private float getLongIndicatorHeightRatio() {
        return mLongIndicatorHeightRatio;
    }

    /**
     * @return Ratio of short indicator height to the ruler height.
     *
     * @see #setIndicatorHeight(float, float)
     */
    @CheckResult
    private float getShortIndicatorHeightRatio() {
        return mShortIndicatorHeightRatio;
    }
}