package com.marlonmafra.data.repository.twitter.remote

import io.reactivex.Single

interface ITwitterRemoteDataSource {

    fun fetchHomeTimeline(): Single<String>
}