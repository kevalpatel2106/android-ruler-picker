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
    private final float PX_14SP;

    public static final int DISPLAY_NUMBER_TYPE_SPACIAL_COUNT = 1;
    public static final int DISPLAY_NUMBER_TYPE_MULTIPLE = 2;
    private Paint mLinePaint;
    private Paint mTextPaint;
    private float MAX_DATA = 100;
    private float MIN_DATA = 0;
    @SuppressWarnings("FieldCanBeLocal")
    private int viewHeight = 0;
    @SuppressWarnings("FieldCanBeLocal")
    private int viewWidth = 0;
    private int valueMultiple = 1;
    private int displayNumberType = 1;
    private int valueTypeMultiple = 5;
    @SuppressWarnings("FieldCanBeLocal")
    private int longHeightRatio = 10;
    @SuppressWarnings("FieldCanBeLocal")
    private int shortHeightRatio = 5;
    @SuppressWarnings("FieldCanBeLocal")
    private int baseHeightRatio = 3;


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
        mLinePaint.setStrokeWidth(5f);      //TODO make it dynamic
        mLinePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);   //TODO make it dynamic
        mTextPaint.setTextSize(PX_14SP);    //TODO make it dynamic
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * Set the maximum value to display on the ruler.
     *
     * @param maxValue Value to display at the right end of the ruler. This can be positive, negative or
     *                 zero.
     */
    public void setMaxValue(final float maxValue) {
        this.MAX_DATA = maxValue;
        invalidate();
    }

    /**
     * Set the minimum value to display on the ruler.
     *
     * @param minValue Value to display at the left end of the ruler. This can be positive, negative or
     *                 zero.
     */
    public void setMinValue(final float minValue) {
        this.MIN_DATA = minValue;
        invalidate();
    }

    public void setValueMultiple(final int valueMultiple) {
        this.valueMultiple = valueMultiple;
        invalidate();
    }

    public void setMultipleTypeValue(final int valueTypeMultiple) {
        this.displayNumberType = DISPLAY_NUMBER_TYPE_MULTIPLE;
        this.valueTypeMultiple = valueTypeMultiple;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        viewHeight = getMeasuredHeight();
        viewWidth = getMeasuredWidth();

        float viewInterval = (float) viewWidth / (MAX_DATA - MIN_DATA);


        canvas.drawLine(0,
                0,
                0,
                viewHeight / longHeightRatio * baseHeightRatio, mLinePaint);

        for (int i = 1; i < (MAX_DATA - MIN_DATA); i++) {

            if (displayNumberType == DISPLAY_NUMBER_TYPE_MULTIPLE) {

                if (((int) (i + MIN_DATA) * valueMultiple) % valueTypeMultiple == 0) {
                    canvas.drawLine(viewInterval * i, 0, viewInterval * i, viewHeight / shortHeightRatio * baseHeightRatio, mLinePaint);
                    canvas.drawText("" + ((int) (i + MIN_DATA) * valueMultiple), viewInterval * i, viewHeight / shortHeightRatio * baseHeightRatio + PX_14SP, mTextPaint);
                } else {
                    canvas.drawLine(viewInterval * i, 0, viewInterval * i, viewHeight / longHeightRatio * baseHeightRatio, mLinePaint);
                }

            } else {
                if (i % 5 == 0) {
                    canvas.drawLine(viewInterval * i, 0, viewInterval * i, viewHeight / shortHeightRatio * baseHeightRatio, mLinePaint);
                    canvas.drawText("" + ((int) (i + MIN_DATA) * valueMultiple), viewInterval * i, viewHeight / shortHeightRatio * baseHeightRatio + PX_14SP, mTextPaint);
                } else {
                    canvas.drawLine(viewInterval * i, 0, viewInterval * i, viewHeight / longHeightRatio * baseHeightRatio, mLinePaint);
                }
            }
        }
        canvas.drawLine(viewWidth, 0, viewWidth, viewHeight / longHeightRatio * baseHeightRatio, mLinePaint);
        super.onDraw(canvas);
    }
}

