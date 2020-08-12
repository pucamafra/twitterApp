package com.marlonmafra.twitterapp.features.splash

import com.marlonmafra.data.repository.authentication.AuthenticationRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SplashInteractorTest {

    @MockK
    private lateinit var authenticationRepository: AuthenticationRepository

    @InjectMockKs
    private lateinit var interactor: SplashInteractor

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun isAuthenticated() {
        // Given
        val isAuthenticated = true
        every { authenticationRepository.isAuthenticated() } returns isAuthenticated

        // When
        val result = interactor.isAuthenticated().blockingGet()

        // Then
        Assert.assertEquals(isAuthenticated, result)
    }
}