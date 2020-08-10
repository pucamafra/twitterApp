package com.marlonmafra.domain.repository

import com.marlonmafra.domain.model.RequestAccessTokenResponse
import com.marlonmafra.domain.model.RequestTokenResponse
import io.reactivex.Single

interface IAuthenticationDataSource {

    fun requestToken(): Single<RequestTokenResponse>

    fun requestAccessToken(
        oauthVerifier: String,
        requestToken: String
    ): Single<RequestAccessTokenResponse>

    fun isAuthenticated(): Boolean
}