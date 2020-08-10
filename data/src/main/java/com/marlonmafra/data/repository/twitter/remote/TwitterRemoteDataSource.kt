package com.marlonmafra.data.repository.twitter.remote

import com.marlonmafra.data.rest.ApiService
import io.reactivex.Single
import javax.inject.Inject

class TwitterRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : ITwitterRemoteDataSource {
    override fun fetchHomeTimeline(): Single<String> {
        return apiService.userTimeline()
            .map { it.string() }
    }
}