package com.marlonmafra.data.rest

import com.marlonmafra.domain.model.Tweet
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("1.1/statuses/home_timeline.json")
    fun userTimeline(): Single<List<Tweet>>
}