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

package com.kevalpatel2106.sample;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

public class RulerDemoActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruler_demo2);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        final TextInputEditText nameEt = findViewById(R.id.profile_name_et);
        nameEt.setText("Audrey Jessica");

        //Set the height picker
        final TextView heightPickerValueTv = findViewById(R.id.height_value_tv);
        final RulerValuePicker heightPicker = findViewById(R.id.height_ruler_picker);
        heightPicker.selectValue(156);
        heightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                heightPickerValueTv.setText(selectedValue + " cms");
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                heightPickerValueTv.setText(selectedValue + " cms");
            }
        });

        //Set the weight picker
        final TextView weightPickerValueTv = findViewById(R.id.weight_value_tv);
        final RulerValuePicker weightPicker = findViewById(R.id.weight_ruler_picker);
        weightPicker.selectValue(55);
        weightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                weightPickerValueTv.setText(selectedValue + " kgs");

            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                weightPickerValueTv.setText(selectedValue + " kgs");

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
