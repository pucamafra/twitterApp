package com.marlonmafra.twitterapp.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.marlonmafra.domain.model.Tweet
import com.marlonmafra.twitterapp.features.RxImmediateSchedulerRule
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var homeViewModel: HomeViewModel

    @MockK
    private lateinit var interactor: MainInteractor

    @MockK(relaxed = true)
    lateinit var tweeListObserver: Observer<List<Tweet>>

    @MockK(relaxed = true)
    lateinit var progressBarObserver: Observer<Boolean>

    @MockK(relaxed = true)
    lateinit var showErrorObserver: Observer<Boolean>

    @MockK(relaxed = true)
    lateinit var tweeListMappedObserver: Observer<List<AbstractFlexibleItem<*>>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        homeViewModel = HomeViewModel(interactor)
        homeViewModel.tweeList.observeForever(tweeListObserver)
        homeViewModel.tweetListMapped.observeForever(tweeListMappedObserver)
        homeViewModel.progressBar.observeForever(progressBarObserver)
        homeViewModel.showError.observeForever(showErrorObserver)
    }

    @Test
    fun retrieveTimeLine() {
        // Given
        val tweetList = ArrayList<Tweet>()
        val tweeListMapped = listOf<AbstractFlexibleItem<*>>()
        every { interactor.fetchHomeTimeline() } returns Single.just(tweetList)

        // When
        homeViewModel.retrieveTimeLine()

        // Then
        verify {
            progressBarObserver.onChanged(true)
            tweeListObserver.onChanged(tweetList)
            tweeListMappedObserver.onChanged(tweeListMapped)
            progressBarObserver.onChanged(false)
        }
        verify(exactly = 0) {
            showErrorObserver.onChanged(true)
        }
    }

    @Test
    fun retrieveTimeLine_exception() {
        // Given
        val tweetList = ArrayList<Tweet>()
        val tweeListMapped = listOf<AbstractFlexibleItem<*>>()
        every { interactor.fetchHomeTimeline() } returns Single.error(Exception("Error on loading timeline"))

        // When
        homeViewModel.retrieveTimeLine()

        // Then
        verify(exactly = 0) {
            tweeListObserver.onChanged(tweetList)
            tweeListMappedObserver.onChanged(tweeListMapped)
        }

        verify {
            progressBarObserver.onChanged(true)
            showErrorObserver.onChanged(true)
            progressBarObserver.onChanged(false)
        }
    }
}