package com.marlonmafra.data.repository.twitter

import com.marlonmafra.data.repository.twitter.remote.ITwitterRemoteDataSource
import com.marlonmafra.domain.model.Tweet
import com.marlonmafra.domain.repository.ITwitterDataSource
import io.reactivex.Single
import javax.inject.Inject

class TwitterRepository @Inject constructor(
    private val remote: ITwitterRemoteDataSource
) : ITwitterDataSource {

    override fun fetchHomeTimeline(): Single<List<Tweet>> {
        return remote.fetchHomeTimeline()
    }
}