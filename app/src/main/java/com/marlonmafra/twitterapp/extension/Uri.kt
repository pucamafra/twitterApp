package com.marlonmafra.twitterapp.extension

import android.net.Uri
import com.marlonmafra.twitterapp.model.CallbackResponse

fun Uri.isValid(callbackURL: String): Boolean {
    return toString().startsWith(callbackURL)
}

fun Uri.toCallbackResponse(): CallbackResponse {
    return CallbackResponse(this)
}