package com.marlonmafra.domain.model

class RequestAccessTokenResponse(rawResponse: String) {

    private val array: List<Any> = rawResponse.split('&')
    val oauthToken: String = (array[0] as String).removePrefix("oauth_token=")
    val oauthTokenSecret: String = (array[1] as String).removePrefix("oauth_token_secret=")
}