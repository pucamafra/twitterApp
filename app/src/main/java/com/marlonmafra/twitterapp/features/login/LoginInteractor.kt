package com.marlonmafra.twitterapp.features.login

import com.marlonmafra.data.repository.authentication.AuthenticationRepository
import com.marlonmafra.domain.model.RequestAccessTokenResponse
import com.marlonmafra.domain.model.RequestTokenResponse
import io.reactivex.Single
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    fun requestToken(): Single<RequestTokenResponse> {
        return authenticationRepository.requestToken()
    }

    fun requestAccessToken(
        oauthVerifier: String,
        requestToken: String
    ): Single<RequestAccessTokenResponse> {
        return authenticationRepository.requestAccessToken(oauthVerifier, requestToken)
    }
}