package com.marlonmafra.data.repository.twitter.remote

import com.marlonmafra.data.rest.ApiService
import com.marlonmafra.domain.model.Coordinate
import com.marlonmafra.domain.model.Tweet
import io.reactivex.Single
import javax.inject.Inject
import kotlin.random.Random

class TwitterRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : ITwitterRemoteDataSource {

    override fun fetchHomeTimeline(): Single<List<Tweet>> {
        return apiService.homeTimeline()
            .map { tweetList ->
                tweetList.map { it.coordinates = generateCoordinate() }
                tweetList
            }
    }

    private fun generateCoordinate(): Coordinate {
        val latitude = generateLatitude()
        val longitude = generateLongitude()
        return Coordinate(arrayOf(latitude, longitude))
    }

    private fun generateLatitude(): Double {
        return Random.nextDouble(-90.0, 90.0)
    }

    private fun generateLongitude(): Double {
        return Random.nextDouble(-180.0, 180.0)
    }

}