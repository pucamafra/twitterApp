package com.marlonmafra.twitterapp.extension

import java.text.DecimalFormat

fun Int.formatValue(): String {
    var valueTemp = this
    val arr = arrayOf("", "K", "M", "B", "T", "P", "E")
    var index = 0
    while (valueTemp / 1000 >= 1) {
        valueTemp /= 1000
        index++
    }
    val decimalFormat = DecimalFormat("#.##")
    return String.format("%s%s", decimalFormat.format(valueTemp), arr[index])
}