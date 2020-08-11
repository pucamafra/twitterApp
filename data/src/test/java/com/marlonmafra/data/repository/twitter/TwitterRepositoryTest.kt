package com.marlonmafra.data.repository.twitter

import com.marlonmafra.data.repository.twitter.remote.TwitterRemoteDataSource
import com.marlonmafra.domain.model.Tweet
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class TwitterRepositoryTest {

    @InjectMockKs
    lateinit var twitterRepository: TwitterRepository

    @MockK
    lateinit var twitterRemoteDataSource: TwitterRemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun fetchHomeTimeline() {
        // Given
        val tweetList = ArrayList<Tweet>()
        every { twitterRemoteDataSource.fetchHomeTimeline() } returns Single.just(tweetList)
        val testObserver = TestObserver.create<List<Tweet>>()

        // When
        twitterRepository.fetchHomeTimeline().subscribe(testObserver)

        // Then
        testObserver.assertValue(tweetList)
        testObserver.assertComplete()
    }

    @Test
    fun fetchHomeTimeline_exception() {
        // Given
        val tweetList = ArrayList<Tweet>()
        every { twitterRemoteDataSource.fetchHomeTimeline() } returns Single.error(Exception())
        val testObserver = TestObserver.create<List<Tweet>>()

        // When
        twitterRepository.fetchHomeTimeline().subscribe(testObserver)

        // Then
        testObserver.assertError(Exception::class.java)
        testObserver.assertNotComplete()
    }
}