package com.marlonmafra.domain.model

import com.marlonmafra.domain.common.Const.OAUTH_TOKEN_PARAM

class RequestTokenResponse(rawResponse: String) {

    private val array: List<Any> = rawResponse.split('&')
    val oauthToken: String = (array[0] as String).removePrefix(OAUTH_TOKEN_PARAM)
}