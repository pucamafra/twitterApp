package com.marlonmafra.twitterapp.features.home

import com.marlonmafra.data.repository.twitter.TwitterRepository
import com.marlonmafra.domain.model.Coordinate
import com.marlonmafra.domain.model.Tweet
import io.reactivex.Single
import javax.inject.Inject
import kotlin.random.Random

class MainInteractor @Inject constructor(
    private val twitterRepository: TwitterRepository
) {

    private val coordinates = mutableListOf<Coordinate>().apply {
        add(Coordinate(arrayOf(19.3356609, -89.4247656)))
        add(Coordinate(arrayOf(19.1281968, -89.7323828)))
        add(Coordinate(arrayOf(18.6916759, -77.9770129)))
        add(Coordinate(arrayOf(12.5906447, -68.1662225)))
        add(Coordinate(arrayOf(8.0506643, -75.0654153)))
        add(Coordinate(arrayOf(6.8708144, -65.4709473)))
        add(Coordinate(arrayOf(6.7999109, -65.2567139)))
        add(Coordinate(arrayOf(3.5178193, -36.4066169)))
        add(Coordinate(arrayOf(24.9218979, -16.6009229)))
        add(Coordinate(arrayOf(33.9976502, 0.9891363)))
        add(Coordinate(arrayOf(38.5677492, -4.8125175)))
        add(Coordinate(arrayOf(43.1434317, 0.9053994)))
        add(Coordinate(arrayOf(44.1526384, 5.5852392)))
        add(Coordinate(arrayOf(43.9268725, 9.695105)))
        add(Coordinate(arrayOf(45.4711933, 15.5819002)))
        add(Coordinate(arrayOf(47.9048229, 27.2383941)))
        add(Coordinate(arrayOf(48.5799791, 39.2168861)))
        add(Coordinate(arrayOf(36.312877, 116.543467)))
        add(Coordinate(arrayOf(45.4581737, 94.9223798)))
        add(Coordinate(arrayOf(-16.356049, 153.352366)))
    }

    fun fetchHomeTimeline(): Single<List<Tweet>> {
        return twitterRepository.fetchHomeTimeline()
            .map { tweetList ->
                tweetList.map { it.coordinates = coordinates[Random.nextInt(0, 19)] }
                tweetList
            }
    }
}