package com.marlonmafra.twitterapp.extension

import eu.davidea.viewholders.FlexibleViewHolder

fun FlexibleViewHolder.getString(id: Int, vararg formatArgs: Any): String =
    contentView.getString(id, *formatArgs)