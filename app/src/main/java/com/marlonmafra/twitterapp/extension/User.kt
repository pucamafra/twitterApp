package com.marlonmafra.twitterapp.extension

import com.marlonmafra.domain.model.User

fun User.formatJoinedDate(): String {
    return createdAt.formatTo("MMMM yyyy")
}