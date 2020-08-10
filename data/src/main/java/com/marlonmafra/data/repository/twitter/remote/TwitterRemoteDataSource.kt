package com.marlonmafra.data.repository.twitter.remote

import com.marlonmafra.data.rest.ApiService
import com.marlonmafra.domain.model.Tweet
import io.reactivex.Single
import javax.inject.Inject

class TwitterRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : ITwitterRemoteDataSource {

    override fun fetchHomeTimeline(): Single<List<Tweet>> {
        return apiService.userTimeline()
    }
}