package com.marlonmafra.data.repository.authentication.remote

import com.marlonmafra.domain.model.RequestAccessTokenResponse
import com.marlonmafra.domain.model.RequestTokenResponse
import io.reactivex.Single

interface IAuthenticationRemoteDataSource {

    fun requestToken(): Single<RequestTokenResponse>

    fun requestAccessToken(
        oauthVerifier: String,
        requestToken: String
    ): Single<RequestAccessTokenResponse>
}