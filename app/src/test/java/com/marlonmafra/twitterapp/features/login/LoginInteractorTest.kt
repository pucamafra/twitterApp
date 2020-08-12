package com.marlonmafra.twitterapp.features.login

import com.marlonmafra.data.repository.authentication.AuthenticationRepository
import com.marlonmafra.domain.model.RequestAccessTokenResponse
import com.marlonmafra.domain.model.RequestTokenResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class LoginInteractorTest {

    @InjectMockKs
    private lateinit var interactor: LoginInteractor

    @MockK
    private lateinit var authenticationRepository: AuthenticationRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun requestToken() {
        // Given
        val response: RequestTokenResponse = mockk()

        every { authenticationRepository.requestToken() } returns Single.just(response)
        val testObserver = TestObserver.create<RequestTokenResponse>()
        // When
        interactor.requestToken().subscribe(testObserver)

        // Then
        testObserver.assertValue(response)
        testObserver.assertComplete()
    }

    @Test
    fun requestToken_exception() {
        // Given
        every { authenticationRepository.requestToken() } returns Single.error(Exception())
        val testObserver = TestObserver.create<RequestTokenResponse>()

        // When
        interactor.requestToken().subscribe(testObserver)

        // Then
        testObserver.assertError(Exception::class.java)
        testObserver.assertNotComplete()
    }

    @Test
    fun requestAccessToken() {
        // Given
        val oauthVerifier = "oauthVerifier"
        val requestToken = "requestToken"
        val response: RequestAccessTokenResponse = mockk()

        every {
            authenticationRepository.requestAccessToken(
                oauthVerifier,
                requestToken
            )
        } returns Single.just(
            response
        )
        val testObserver = TestObserver.create<RequestAccessTokenResponse>()

        // When
        interactor.requestAccessToken(oauthVerifier, requestToken).subscribe(testObserver)

        // Then
        testObserver.assertValue(response)
        testObserver.assertComplete()
    }

    @Test
    fun requestAccessToken_exception() {
        // Given
        val oauthVerifier = "oauthVerifier"
        val requestToken = "requestToken"

        every {
            authenticationRepository.requestAccessToken(
                oauthVerifier,
                requestToken
            )
        } returns Single.error(Exception())
        val testObserver = TestObserver.create<RequestAccessTokenResponse>()

        // When
        interactor.requestAccessToken(oauthVerifier, requestToken).subscribe(testObserver)

        // Then
        testObserver.assertError(Exception::class.java)
        testObserver.assertNotComplete()
    }
}