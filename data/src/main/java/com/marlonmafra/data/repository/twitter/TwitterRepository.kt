package com.marlonmafra.data.repository.twitter

import com.marlonmafra.data.repository.twitter.remote.TwitterRemoteDataSource
import com.marlonmafra.domain.model.Tweet
import com.marlonmafra.domain.repository.ITwitterDataSource
import io.reactivex.Single
import javax.inject.Inject

class TwitterRepository @Inject constructor(
    private val remote: TwitterRemoteDataSource
) : ITwitterDataSource {

    override fun fetchHomeTimeline(): Single<List<Tweet>> {
        return remote.fetchHomeTimeline()
    }
}