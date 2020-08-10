package com.marlonmafra.twitterapp.features.home

import com.marlonmafra.data.repository.news.AuthenticationRepository
import com.marlonmafra.data.repository.twitter.TwitterRepository
import com.marlonmafra.domain.model.RequestAccessTokenResponse
import com.marlonmafra.domain.model.RequestTokenResponse
import io.reactivex.Single
import javax.inject.Inject

class MainInteractor @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val twitterRepository: TwitterRepository
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

    fun fetchHomeTimeline(): Single<String> {
        return twitterRepository.fetchHomeTimeline()
    }
}