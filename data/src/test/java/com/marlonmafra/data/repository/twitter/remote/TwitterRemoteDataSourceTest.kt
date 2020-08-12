package com.marlonmafra.data.repository.twitter.remote

import com.marlonmafra.data.rest.ApiService
import com.marlonmafra.domain.model.Tweet
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class TwitterRemoteDataSourceTest {

    @InjectMockKs
    private lateinit var twitterRemoteDataSource: TwitterRemoteDataSource

    @MockK
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun fetchHomeTimeline() {
        // Given
        val tweetList = ArrayList<Tweet>()
        every { apiService.homeTimeline() } returns Single.just(tweetList)
        val testObserver = TestObserver.create<List<Tweet>>()

        // When
        twitterRemoteDataSource.fetchHomeTimeline().subscribe(testObserver)

        // Then
        testObserver.assertValue(tweetList)
        testObserver.assertComplete()
    }

    @Test
    fun fetchHomeTimeline_exception() {
        // Given
        every { apiService.homeTimeline() } returns Single.error(Exception())
        val testObserver = TestObserver.create<List<Tweet>>()

        // When
        twitterRemoteDataSource.fetchHomeTimeline().subscribe(testObserver)

        // Then
        testObserver.assertError(Exception::class.java)
        testObserver.assertNotComplete()
    }
}