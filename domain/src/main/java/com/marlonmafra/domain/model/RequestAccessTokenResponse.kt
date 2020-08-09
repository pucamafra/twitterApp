package com.marlonmafra.domain.model

class RequestAccessTokenResponse(rawResponse: String) {

    private val array: List<Any> = rawResponse.split('&')
    private val oauthToken: String = (array[0] as String).removePrefix("oauth_token=")
    private val oauthTokenSecret: String = (array[1] as String).removePrefix("oauth_token_secret=")
}