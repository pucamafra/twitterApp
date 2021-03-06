package com.marlonmafra.data.repository.authentication

import com.marlonmafra.data.repository.authentication.local.IAuthenticationLocalDataSource
import com.marlonmafra.data.repository.authentication.remote.IAuthenticationRemoteDataSource
import com.marlonmafra.domain.model.RequestAccessTokenResponse
import com.marlonmafra.domain.model.RequestTokenResponse
import com.marlonmafra.domain.repository.IAuthenticationDataSource
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val local: IAuthenticationLocalDataSource,
    private val remote: IAuthenticationRemoteDataSource
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
        val hasToken = local.getToken() != null
        val hasTokenSecret = local.getTokenSecret() != null
        return hasToken && hasTokenSecret
    }

    override fun logout(): Completable {
        return Completable.fromAction { local.cleanData() }
    }
}