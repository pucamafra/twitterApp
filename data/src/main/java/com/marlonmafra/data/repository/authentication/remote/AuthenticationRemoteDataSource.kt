package com.marlonmafra.data.repository.authentication.remote

import com.marlonmafra.data.rest.UnauthenticatedService
import com.marlonmafra.domain.model.RequestAccessTokenResponse
import com.marlonmafra.domain.model.RequestTokenResponse
import io.reactivex.Single
import javax.inject.Inject

class AuthenticationRemoteDataSource @Inject constructor(
    private val unauthenticatedService: UnauthenticatedService,
    private val callbackUrl: String
) : IAuthenticationRemoteDataSource {

    override fun requestToken(): Single<RequestTokenResponse> {
        return unauthenticatedService.requestToken(callbackUrl)
            .map { RequestTokenResponse(it.string()) }
    }

    override fun requestAccessToken(
        oauthVerifier: String, requestToken: String
    ): Single<RequestAccessTokenResponse> {
        return unauthenticatedService.accessToken(oauthVerifier, requestToken)
            .map { RequestAccessTokenResponse(it.string()) }
    }
}