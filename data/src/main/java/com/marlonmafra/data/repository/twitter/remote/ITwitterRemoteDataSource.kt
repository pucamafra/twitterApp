package com.marlonmafra.data.repository.twitter.remote

import com.marlonmafra.domain.model.Tweet
import io.reactivex.Single

interface ITwitterRemoteDataSource {

    fun fetchHomeTimeline(): Single<List<Tweet>>
}