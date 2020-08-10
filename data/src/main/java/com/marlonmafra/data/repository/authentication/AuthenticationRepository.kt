package com.marlonmafra.data.repository.authentication

import com.marlonmafra.data.repository.authentication.local.AuthenticationLocalData
import com.marlonmafra.data.repository.authentication.remote.AuthenticationRemoteDataSource
import com.marlonmafra.domain.model.RequestAccessTokenResponse
import com.marlonmafra.domain.model.RequestTokenResponse
import com.marlonmafra.domain.repository.IAuthenticationDataSource
import io.reactivex.Single
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val local: AuthenticationLocalData,
    private val remote: AuthenticationRemoteDataSource
) : IAuthenticationDataSource {

    override fun requestToken(): Single<RequestTokenResponse> {
        return remote.requestToken()
    }

    override fun requestAccessToken(
        oauthVerifier: String,
        requestToken: String
    ): Single<RequestAccessTokenResponse> {
        return remote.requestAccessToken(oauthVerifier, requestToken)
            .doOnSuccess {
                local.saveData(it.oauthToken, it.oauthTokenSecret)
            }
    }

    override fun isAuthenticated(): Boolean {
        val token = local.getToken() != null
        val tokenSecret = local.getTokenSecret() != null
        return token && tokenSecret
    }
}