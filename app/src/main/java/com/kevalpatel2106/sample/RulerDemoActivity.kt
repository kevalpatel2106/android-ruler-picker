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
import android.widget.TextView
import android.widget.Toast
import com.kevalpatel2106.rulerpicker.RulerValuePicker
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener

/**
 * Created by Keval on 16/12/17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class RulerDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruler_demo)

        val valueTv = findViewById<TextView>(R.id.selected_ruler_value_tv)
        val valuePicker = findViewById<RulerValuePicker>(R.id.ruler_view_demo)

        valuePicker.textColor = Color.RED
        valuePicker.indicatorColor = Color.DKGRAY
        valuePicker.setIndicatorIntervalDistance(60)
        valuePicker.notchColor = Color.BLUE
        valuePicker.setTextSize(16)
        valuePicker.setIndicatorWidth(4)
        valuePicker.setMinMaxValue(125, 350)
        valuePicker.selectValue(130)

        valuePicker.setValuePickerListener(object : RulerValuePickerListener {
            var selectedValue = 0

            override fun onScrollStopped() {
                Toast.makeText(this@RulerDemoActivity, "User height is :$selectedValue cms", Toast.LENGTH_LONG).show()
                valueTv.text = "Value: $selectedValue cms\nScroll stopped."
            }

            override fun onValueChanged(selectedValue: Int) {
                this.selectedValue = selectedValue
                valueTv.text = "Value: $selectedValue cms"
            }

        })
    }

}
