package com.marlonmafra.twitterapp.features.home

import com.marlonmafra.data.repository.authentication.AuthenticationRepository
import com.marlonmafra.data.repository.twitter.TwitterRepository
import com.marlonmafra.domain.model.Tweet
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val twitterRepository: TwitterRepository,
    private val authenticationRepository: AuthenticationRepository
) {

    fun fetchHomeTimeline(): Single<List<Tweet>> {
        return twitterRepository.fetchHomeTimeline()
    }

    fun logout(): Completable {
        return authenticationRepository.logout()
    }
}