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
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by DWFOX on 2016-08-31.
 *
 * @see <a href="https://github.com/dwfox/DWRulerView>Original Repo</a>
 */

public class LineRulerView extends View {

    public static final int DISPLAY_NUMBER_TYPE_SPACIAL_COUNT = 1;
    public static final int DISPLAY_NUMBER_TYPE_MULTIPLE = 2;
    private final float PX_14SP;
    private Paint paint;
    private Paint textPaint;
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


    public LineRulerView(Context context) {
        super(context);
        PX_14SP = sp2px(14);
        init(context);
    }

    public LineRulerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        PX_14SP = sp2px(14);
        init(context);
    }

    public LineRulerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        PX_14SP = sp2px(14);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LineRulerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        PX_14SP = sp2px(14);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStrokeWidth(5f);
        paint.isAntiAlias();
        paint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setColor(0xFFFFFFFF);
        textPaint.isAntiAlias();
        textPaint.setTextSize(PX_14SP);
        textPaint.setTextAlign(Paint.Align.CENTER);

        invalidate();
    }

    public void setMaxValue(float maxValue) {
        this.MAX_DATA = maxValue;
        invalidate();
    }

    public void setMinValue(float minValue) {
        this.MIN_DATA = minValue;
        invalidate();
    }

    public void setValueMultiple(int valueMultiple) {
        this.valueMultiple = valueMultiple;
        invalidate();
    }

    public void setMultipleTypeValue(int valueTypeMultiple) {
        this.displayNumberType = DISPLAY_NUMBER_TYPE_MULTIPLE;
        this.valueTypeMultiple = valueTypeMultiple;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        viewHeight = getMeasuredHeight();
        viewWidth = getMeasuredWidth();

        float viewInterval = (float) viewWidth / (MAX_DATA - MIN_DATA);
        canvas.drawLine(0, 0, 0, viewHeight / longHeightRatio * baseHeightRatio, paint);

        for (int i = 1; i < (MAX_DATA - MIN_DATA); i++) {
            if (displayNumberType == DISPLAY_NUMBER_TYPE_MULTIPLE) {

                if (((int) (i + MIN_DATA) * valueMultiple) % valueTypeMultiple == 0) {
                    canvas.drawLine(viewInterval * i, 0, viewInterval * i, viewHeight / shortHeightRatio * baseHeightRatio, paint);
                    canvas.drawText("" + ((int) (i + MIN_DATA) * valueMultiple), viewInterval * i, viewHeight / shortHeightRatio * baseHeightRatio + PX_14SP, textPaint);
                } else {
                    canvas.drawLine(viewInterval * i, 0, viewInterval * i, viewHeight / longHeightRatio * baseHeightRatio, paint);
                }

            } else {
                if (i % 5 == 0) {
                    canvas.drawLine(viewInterval * i, 0, viewInterval * i, viewHeight / shortHeightRatio * baseHeightRatio, paint);
                    canvas.drawText("" + ((int) (i + MIN_DATA) * valueMultiple), viewInterval * i, viewHeight / shortHeightRatio * baseHeightRatio + PX_14SP, textPaint);
                } else {
                    canvas.drawLine(viewInterval * i, 0, viewInterval * i, viewHeight / longHeightRatio * baseHeightRatio, paint);
                }
            }
        }
        canvas.drawLine(viewWidth, 0, viewWidth, viewHeight / longHeightRatio * baseHeightRatio, paint);
        super.onDraw(canvas);
    }

    private int sp2px(@SuppressWarnings("SameParameterValue") float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}

