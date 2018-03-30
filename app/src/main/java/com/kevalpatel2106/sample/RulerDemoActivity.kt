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

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener
import kotlinx.android.synthetic.main.activity_ruler_demo.*

/**
 * Created by Keval on 16/12/17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class RulerDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruler_demo)

        ruler_value_picker.textColor = Color.RED
        ruler_value_picker.indicatorColor = Color.DKGRAY
        ruler_value_picker.setIndicatorIntervalDistance(60)
        ruler_value_picker.notchColor = Color.BLUE
        ruler_value_picker.setTextSize(16)
        ruler_value_picker.setIndicatorWidth(4)
        ruler_value_picker.setMinMaxValue(125, 350)
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

}
