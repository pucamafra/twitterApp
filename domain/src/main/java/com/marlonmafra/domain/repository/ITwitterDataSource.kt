package com.marlonmafra.domain.repository

import io.reactivex.Single

interface ITwitterDataSource {

    fun fetchHomeTimeline(): Single<String>
}