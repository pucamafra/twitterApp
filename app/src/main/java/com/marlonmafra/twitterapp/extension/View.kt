package com.marlonmafra.twitterapp.extension

import android.view.View
import eu.davidea.viewholders.FlexibleViewHolder

fun View.getString(id: Int, vararg formatArgs: Any): String = context.getString(id, *formatArgs)

fun View.changeVisibility(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}