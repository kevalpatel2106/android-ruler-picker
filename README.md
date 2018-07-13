# Android Ruler Picker

[![Build Status](https://travis-ci.org/kevalpatel2106/android-ruler-picker.svg?branch=master)](https://travis-ci.org/kevalpatel2106/android-ruler-picker) [![API](https://img.shields.io/badge/API-14%2B-orange.svg?style=flat)](https://android-arsenal.com/api?level=14)  <a href="https://www.paypal.me/kevalpatel2106"> <img src="https://img.shields.io/badge/paypal-donate-yellow.svg" /></a> [![Javadoc](https://img.shields.io/badge/JavaDoc-master-brightgreen.svg?style=orange)](http://kevalpatel2106.com/android-ruler-picker/)

#### Android custom view that uses ruler for picking the number from given range.

## Features:
- Easy to integrate. All you have to do is add the view into your XML and listen for the value changes.
- Highly customizable. Change width, height, color, distance between indicators. Change the color and sze of the texts in the ruler in XML or dynamically from your java or kotlin code.
- Extremely lightweight 🏋.

## How to use this library?
- ### Gradle dependency: 
    - Add below dependency into your build.gradle file.
        ```groovy
        compile 'com.kevalpatel2106:ruler-picker:1.1'
        ```
    - For other build systems see [Import.md](/.github/IMPORT.md).

- Add `RulerValuePicker` inside your XML layout.
```xml
<com.kevalpatel2106.rulerpicker.RulerValuePicker
    android:id="@+id/ruler_picker"
    android:layout_width="match_parent"
    android:layout_height="100dp"
    android:background="@android:color/holo_orange_dark"
    app:indicator_color="@android:color/white"
    app:indicator_interval="14dp"
    app:indicator_width="2dp"
    app:max_value="120"
    app:min_value="35"
    app:notch_color="@android:color/white"
    app:ruler_text_size="6sp"/>

```
- Library provides XML attributes to customize the `RulerValuePicker` or you can customize it dynamically using Java/Kotlin code.

|Attribute|Type|Java/Kotlin|Description|
|:---:|:---:|:---:|:---:|
|indicator_color|Color|`setIndicatorColor()`|Change the color of the indicator in the ruler.|
|indicator_interval|Dimensions|`setIndicatorIntervalDistance()`|Change the distance between two indicators in the ruler.|
|indicator_width|Dimensions|`setIndicatorWidth()`|Change the width (thickness) of the indicator.|
|notch_color|Color|`setNotchColor()`|Change the color off the notch at the top of the ruler.|
|ruler_text_size|Dimensions|`setTextSize()`|Change the size of the text that displays the values in the ruler.|
|ruler_text_color|Color|`setTextColor()`|Change the color of the text that displays the values in the ruler.|
|long_height_height_ratio|Fraction|`setIndicatorHeight()`|Change the height of the long indicator. The value is between 0 to 1 where 1 indicates the height of the ruler. This value must be greater than or equal to `long_height_height_ratio`.|
|short_height_height_ratio|Fraction|`setIndicatorHeight()`|Change the height of the short indicator. The value is between 0 to 1 where 1 indicates the height of the ruler. This value must be less than or equal to `short_height_height_ratio`.|
|max_value|Integer|`setMinMaxValue()`|Maximum possible value to display in the ruler. This value must be greater than `min_value`.|
|min_value|Integer|`setMinMaxValue()`|Minimum possible value to display in the ruler. This value must be greater than `max_value`.|

- Set the initially selected value.
```java
rulerValuePicker.selectValue(55 /* Initial value */);
```

- Set up a `RulerValuePickerListener` callback listener to get notify when the selected value changes. Application will receive the final selected value in `onValueChange()` callback.

#### Java:
```java
rulerValuePicker.setValuePickerListener(new RulerValuePickerListener() {
    @Override
    public void onValueChange(final int selectedValue) {
        //Value changed and the user stopped scrolling the ruler.
        //Application can consider this value as final selected value.
    }

    @Override
    public void onIntermediateValueChange(final int selectedValue) {
        //Value changed but the user is still scrolling the ruler.
        //This value is not final value. Application can utilize this value to display the current selected value.
    }
});
```
#### Kotlin:
```kotlin
rulerValuePicker.setValuePickerListener(object : RulerValuePickerListener {
    override fun onValueChange(value: Int) {
        //Value changed and the user stopped scrolling the ruler.
        //You can consider this value as final selected value.
    }

    override fun onIntermediateValueChange(selectedValue: Int) {
        //Value changed but the user is still scrolling the ruler.
        //This value is not final value. You can utilize this value to display the current selected value.
    }
})
```

## Screenshots:

|Sample 1|Sample 2|
|:---:|:---:|
|![profile-demo.gif](/.github/ruler-view-profile-demo-small.gif)|![ruler-view-demo.gif](/.github/ruler-view-demo-small.gif)|


## What to try this out?
- You can download the sample apk from [here](/.github/sample.apk) and play with it.

## Want to contribute?
Every small or large contribution to this project is appreciated. Make sure you read the [contribution guide](/.github/CONTRIBUTING.md) before generating the pull request.

## Questions?🤔
Hit me on twitter [![Twitter](https://img.shields.io/badge/Twitter-@kevalpatel2106-blue.svg?style=flat)](https://twitter.com/kevalpatel2106)

## Acknowledgements:
- This library is based on [dwfox](https://github.com/dwfox)'s library [DWRulerView](https://github.com/dwfox/DWRulerView).

## License
Copyright 2018 Keval Patel

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

<div align="center">
<img src="https://cloud.githubusercontent.com/assets/370176/26526332/03bb8ac2-432c-11e7-89aa-da3cd1c0e9cb.png">
</div>
