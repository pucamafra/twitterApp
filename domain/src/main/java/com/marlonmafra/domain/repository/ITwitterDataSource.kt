package com.marlonmafra.domain.repository

import com.marlonmafra.domain.model.Tweet
import io.reactivex.Single

interface ITwitterDataSource {

    fun fetchHomeTimeline(): Single<List<Tweet>>
}