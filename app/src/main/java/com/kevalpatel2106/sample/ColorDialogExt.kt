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

import android.app.Activity
import android.graphics.Color
import android.support.annotation.StringRes
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape

/**
 * Created by Kevalpatel2106 on 30-Mar-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

internal const val NOTCH_COLOR_PICKER_DIALOG_ID = 0
internal const val TEXT_COLOR_PICKER_DIALOG_ID = 1
internal const val INDICATOR_COLOR_PICKER_DIALOG_ID = 2

fun createColorPicker(
        activity: Activity,
        @StringRes titleRes: Int,
        dialogId: Int,
        listener: ColorPickerDialogListener
) {
    val dialog = ColorPickerDialog.newBuilder()
            .setDialogTitle(titleRes)
            .setColor(Color.BLUE)
            .setColor(Color.RED)
            .setColor(Color.GRAY)
            .setColor(Color.GREEN)
            .setColor(Color.DKGRAY)
            .setColor(Color.BLACK)
            .setColor(Color.LTGRAY)
            .setColor(Color.CYAN)
            .setColor(Color.MAGENTA)
            .setColor(Color.WHITE)
            .setColor(Color.YELLOW)
            .setAllowCustom(true)
            .setColorShape(ColorShape.CIRCLE)
            .setCustomButtonText(R.string.pick_custom_color_btn_title)
            .setDialogType(ColorPickerDialog.TYPE_PRESETS)
            .setShowAlphaSlider(true)
            .setShowColorShades(true)
            .setDialogId(dialogId)
            .create()

    dialog.setColorPickerDialogListener(listener)
    dialog.show(activity.fragmentManager, dialogId.toString())
}
