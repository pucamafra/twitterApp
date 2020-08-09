package com.marlonmafra.data.repository.news

import com.marlonmafra.data.repository.news.remote.AuthenticationRemoteDataSource
import com.marlonmafra.domain.model.RequestAccessTokenResponse
import com.marlonmafra.domain.model.RequestTokenResponse
import com.marlonmafra.domain.repository.IAuthenticationDataSource
import io.reactivex.Single
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val remoteDataSource: AuthenticationRemoteDataSource
) : IAuthenticationDataSource {

    override fun requestToken(): Single<RequestTokenResponse> {
        return remoteDataSource.requestToken()
    }

    override fun requestAccessToken(
        oauthVerifier: String,
        requestToken: String
    ): Single<RequestAccessTokenResponse> {
        return remoteDataSource.requestAccessToken(oauthVerifier, requestToken)
    }
}