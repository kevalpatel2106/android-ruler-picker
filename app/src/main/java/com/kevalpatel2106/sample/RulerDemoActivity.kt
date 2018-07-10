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

package com.kevalpatel2106.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener
import com.xw.repo.BubbleSeekBar
import kotlinx.android.synthetic.main.activity_ruler_demo.*

/**
 * Created by Keval on 16/12/17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class RulerDemoActivity : AppCompatActivity() {

    private var colorPickerListener = object : ColorPickerDialogListener {
        override fun onDialogDismissed(dialogId: Int) {
            //Do nothing
        }

        override fun onColorSelected(dialogId: Int, color: Int) {
            when (dialogId) {
                NOTCH_COLOR_PICKER_DIALOG_ID -> {
                    ruler_value_picker.notchColor = color
                    notch_color_panel.color = color
                }
                TEXT_COLOR_PICKER_DIALOG_ID -> {
                    ruler_value_picker.textColor = color
                    ruler_text_color_panel.color = color
                }
                INDICATOR_COLOR_PICKER_DIALOG_ID -> {
                    ruler_value_picker.indicatorColor = color
                    indicator_color_panel.color = color
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruler_demo)

        //Change notch color
        notch_color_panel.color = ruler_value_picker.notchColor
        notch_color_panel.setOnClickListener {
            createColorPicker(
                    activity = this@RulerDemoActivity,
                    titleRes = R.string.color_picker_title_pick_notch_color,
                    dialogId = NOTCH_COLOR_PICKER_DIALOG_ID,
                    listener = colorPickerListener
            )
        }

        //Change the text color in ruler
        ruler_text_color_panel.color = ruler_value_picker.textColor
        ruler_text_color_panel.setOnClickListener {
            createColorPicker(
                    activity = this@RulerDemoActivity,
                    titleRes = R.string.color_picker_title_pick_text_color,
                    dialogId = TEXT_COLOR_PICKER_DIALOG_ID,
                    listener = colorPickerListener
            )
        }

        //Change the text size
        ruler_text_size_picker.setProgress(ruler_value_picker.textSize)
        ruler_text_size_picker.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                ruler_value_picker.setTextSize(progress)
            }

            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                //Do nothing
            }

            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                //Do nothing
            }
        }

        //Change the indicator color
        ruler_text_color_panel.color = ruler_value_picker.indicatorColor
        indicator_color_panel.setOnClickListener {
            createColorPicker(
                    activity = this@RulerDemoActivity,
                    titleRes = R.string.color_picker_title_pick_indicator_color,
                    dialogId = INDICATOR_COLOR_PICKER_DIALOG_ID,
                    listener = colorPickerListener
            )
        }

        //Change the long indicator size
        long_indicator_size_picker.setProgress(ruler_value_picker.longIndicatorHeightRatio)
        long_indicator_size_picker.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                ruler_value_picker.setIndicatorHeight(progressFloat, short_indicator_size_picker.progressFloat)
            }

            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                //Do nothing
            }

            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                //Do nothing
            }
        }

        //Change the short indicator size
        short_indicator_size_picker.setProgress(ruler_value_picker.longIndicatorHeightRatio)
        short_indicator_size_picker.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                ruler_value_picker.setIndicatorHeight(long_indicator_size_picker.progressFloat, progressFloat)
            }

            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                //Do nothing
            }

            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                //Do nothing
            }
        }

        //Change the indicator width
        indicator_width_picker.setProgress(ruler_value_picker.indicatorWidth)
        indicator_width_picker.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                ruler_value_picker.setIndicatorWidth(progress)
            }

            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                //Do nothing
            }

            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                //Do nothing
            }
        }

        //Change the indicator interval
        indicator_interval_picker.setProgress(ruler_value_picker.indicatorIntervalWidth.toFloat())
        indicator_interval_picker.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                ruler_value_picker.setIndicatorIntervalDistance(progress)
                ruler_value_picker.selectValue(130)
            }

            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                //Do nothing
            }

            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                //Do nothing
            }
        }

        //Set minimum value
        min_value_et.setText(resources.getInteger(R.integer.min_value).toString())
        min_value_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //Do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                ruler_value_picker.setMinMaxValue(min_value_et.text.toString().toSafeInt(),
                        max_value_et.text.toString().toSafeInt())
            }

        })

        //Set maximum value
        max_value_et.setText(resources.getInteger(R.integer.max_value).toString())
        max_value_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //Do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                ruler_value_picker.setMinMaxValue(min_value_et.text.toString().toSafeInt(),
                        max_value_et.text.toString().toSafeInt())
            }

        })

        ruler_value_picker.setMinMaxValue(min_value_et.text.toString().toSafeInt(),
                max_value_et.text.toString().toSafeInt())
        ruler_value_picker.selectValue(130)

        ruler_value_picker.setValuePickerListener(object : RulerValuePickerListener {
            override fun onValueChange(value: Int) {
                Toast.makeText(this@RulerDemoActivity, "User height is :$value cms", Toast.LENGTH_LONG).show()
                current_value_tv.text = "$value"
            }

            override fun onIntermediateValueChange(selectedValue: Int) {
                current_value_tv.text = "$selectedValue"
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

}
