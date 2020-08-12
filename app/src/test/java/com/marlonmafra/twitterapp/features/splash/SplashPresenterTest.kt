package com.marlonmafra.twitterapp.features.splash

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class SplashPresenterTest {

    @InjectMockKs
    private lateinit var presenter: SplashPresenter

    @MockK
    private lateinit var interactor: SplashInteractor

    @MockK(relaxed = true)
    private lateinit var view: ISplashView

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun checkAuthentication_true() {
        // Given
        val isAuthenticated = true
        every { interactor.isAuthenticated() } returns Single.just(isAuthenticated)

        // When
        presenter.checkAuthentication()

        // Then
        verify { view.goToHome() }
        verify(exactly = 0) { view.goToLogin() }
    }

    @Test
    fun checkAuthentication_false() {
        // Given
        val isAuthenticated = false
        every { interactor.isAuthenticated() } returns Single.just(isAuthenticated)

        // When
        presenter.checkAuthentication()

        // Then
        verify { view.goToLogin() }
        verify(exactly = 0) { view.goToHome() }
    }

    @Test
    fun checkAuthentication_exception() {
        // Given
        every { interactor.isAuthenticated() } returns Single.error(Exception())

        // When
        presenter.checkAuthentication()

        // Then
        verify { view.goToLogin() }
        verify(exactly = 0) { view.goToHome() }
    }
}