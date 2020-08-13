package com.marlonmafra.twitterapp.model

import android.net.Uri
import com.marlonmafra.twitterapp.common.Const.OAUTH_TOKEN
import com.marlonmafra.twitterapp.common.Const.OAUTH_VERIFIER

class CallbackResponse(uri: Uri) {
    val oauthToken: String? = uri.getQueryParameter(OAUTH_TOKEN)
    val oauthVerifier: String? = uri.getQueryParameter(OAUTH_VERIFIER)
}