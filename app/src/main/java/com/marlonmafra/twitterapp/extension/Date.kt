package com.marlonmafra.twitterapp.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatTo(mask: String): String = SimpleDateFormat(mask, Locale.getDefault())
    .format(this)