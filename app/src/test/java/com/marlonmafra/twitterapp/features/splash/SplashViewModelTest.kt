package com.marlonmafra.twitterapp.features.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.marlonmafra.twitterapp.features.IntentAction
import com.marlonmafra.twitterapp.features.RxImmediateSchedulerRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var splashViewModel: SplashViewModel

    @MockK
    private lateinit var interactor: SplashInteractor

    @MockK(relaxed = true)
    lateinit var checkAuthenticationObserver: Observer<IntentAction>

    @MockK(relaxed = true)
    lateinit var progressBarObserver: Observer<Boolean>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        splashViewModel = SplashViewModel(interactor)
        splashViewModel.intentAction.observeForever(checkAuthenticationObserver)
        splashViewModel.progressBar.observeForever(progressBarObserver)
    }

    @Test
    fun checkAuthentication() {
        // Given
        val homeAction = IntentAction.Home
        every { interactor.isAuthenticated() } returns Single.just(true)

        // When
        splashViewModel.checkAuthentication()

        // Then
        verify {
            progressBarObserver.onChanged(true)
            checkAuthenticationObserver.onChanged(homeAction)
            progressBarObserver.onChanged(false)
        }
    }

    @Test
    fun checkAuthentication_exception() {
        // Given
        val loginAction = IntentAction.Login
        every { interactor.isAuthenticated() } returns Single.error(Exception())

        // When
        splashViewModel.checkAuthentication()

        // Then
        verify {
            progressBarObserver.onChanged(true)
            checkAuthenticationObserver.onChanged(loginAction)
            progressBarObserver.onChanged(false)
        }
    }
}