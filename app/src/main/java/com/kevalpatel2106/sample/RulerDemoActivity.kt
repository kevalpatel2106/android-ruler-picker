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
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.kevalpatel2106.rulerpicker.ObservableHorizontalScrollView
import com.kevalpatel2106.rulerpicker.RulerValuePicker
import com.kevalpatel2106.rulerpicker.ScrollChangedListener

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

        valuePicker.setMinMaxValue(125, 350)
        valuePicker.setOnScrollChangedListener(object : ScrollChangedListener {
            override fun onScrollChanged(view: ObservableHorizontalScrollView?, l: Int, t: Int) {
                valueTv.text = String.format("%d Kgs", valuePicker.getCurrentValue(l))

            }

            override fun onScrollStopped(l: Int, t: Int) {
                valueTv.text = String.format("%d Kgs\nScroll Stopped.", valuePicker.getValueAndScrollItemToCenter(l))
            }

        })

        Handler().postDelayed({
            valuePicker.scrollToValue(200F)
        }, 1000)
    }

}
