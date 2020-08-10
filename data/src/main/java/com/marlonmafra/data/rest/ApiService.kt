package com.marlonmafra.data.rest

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

interface ApiService {

    @GET("1.1/statuses/home_timeline.json")
    fun userTimeline(): Single<ResponseBody>
}