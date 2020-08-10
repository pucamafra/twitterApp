package com.marlonmafra.twitterapp.features.home

import com.marlonmafra.data.repository.twitter.TwitterRepository
import com.marlonmafra.domain.model.Tweet
import io.reactivex.Single
import javax.inject.Inject

class MainInteractor @Inject constructor(
    private val twitterRepository: TwitterRepository
) {

    fun fetchHomeTimeline(): Single<List<Tweet>> {
        return twitterRepository.fetchHomeTimeline()
    }
}