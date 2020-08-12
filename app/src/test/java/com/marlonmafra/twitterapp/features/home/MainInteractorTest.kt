package com.marlonmafra.twitterapp.features.home

import com.marlonmafra.data.repository.twitter.TwitterRepository
import com.marlonmafra.domain.model.Tweet
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class MainInteractorTest {

    @InjectMockKs
    private lateinit var interactor: MainInteractor

    @MockK
    private lateinit var twitterRepository: TwitterRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun fetchHomeTimeline() {
        // Given
        val tweetList = ArrayList<Tweet>()
        every { twitterRepository.fetchHomeTimeline() } returns Single.just(tweetList)
        val testObserver = TestObserver.create<List<Tweet>>()

        // When
        interactor.fetchHomeTimeline().subscribe(testObserver)

        // Then
        testObserver.assertValue(tweetList)
        testObserver.assertComplete()
    }

    @Test
    fun fetchHomeTimeline_exception() {
        // Given
        every { twitterRepository.fetchHomeTimeline() } returns Single.error(Exception())
        val testObserver = TestObserver.create<List<Tweet>>()

        // When
        interactor.fetchHomeTimeline().subscribe(testObserver)

        // Then
        testObserver.assertError(Exception::class.java)
        testObserver.assertNotComplete()
    }
}