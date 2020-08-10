package com.marlonmafra.domain.model

class RequestTokenResponse(rawResponse: String) {

    private val array: List<Any> = rawResponse.split('&')
    val oauthToken: String = (array[0] as String).removePrefix("oauth_token=")
    val oauthTokenSecret: String = (array[1] as String).removePrefix("oauth_token_secret=")
    val oauthCallbackConfirmed: Boolean =
        (array[2] as String).removePrefix("oauth_callback_confirmed=").toBoolean()
}