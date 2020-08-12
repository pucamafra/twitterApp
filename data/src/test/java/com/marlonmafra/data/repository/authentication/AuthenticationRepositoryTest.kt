package com.marlonmafra.data.repository.authentication

import com.marlonmafra.data.repository.authentication.local.AuthenticationLocalDataSource
import com.marlonmafra.data.repository.authentication.remote.AuthenticationRemoteDataSource
import com.marlonmafra.domain.model.RequestAccessTokenResponse
import com.marlonmafra.domain.model.RequestTokenResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AuthenticationRepositoryTest {

    @InjectMockKs
    private lateinit var authenticationRepository: AuthenticationRepository

    @MockK
    private lateinit var remote: AuthenticationRemoteDataSource

    @MockK
    private lateinit var local: AuthenticationLocalDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun requestToken() {
        // Given
        val response = mockk<RequestTokenResponse>()
        val testObserver = TestObserver.create<RequestTokenResponse>()

        every { remote.requestToken() } returns Single.just(response)

        // When
        authenticationRepository.requestToken().subscribe(testObserver)

        // Then
        testObserver.assertValue(response)
        testObserver.assertComplete()
    }

    @Test
    fun requestToken_exception() {
        // Given
        val testObserver = TestObserver.create<RequestTokenResponse>()

        every { remote.requestToken() } returns Single.error(Exception())

        // When
        authenticationRepository.requestToken().subscribe(testObserver)

        // Then
        testObserver.assertError(Exception::class.java)
        testObserver.assertNotComplete()
    }

    @Test
    fun requestAccessToken() {
        // Given
        val response = mockk<RequestAccessTokenResponse>()
        val oauthVerifier = "oauthVerifier"
        val requestToken = "requestToken"
        val oauthToken = "oauthToken"
        val oauthTokenSecret = "oauthTokenSecret"

        every { response.oauthToken } returns oauthToken
        every { response.oauthTokenSecret } returns oauthTokenSecret

        every { remote.requestAccessToken(oauthVerifier, requestToken) } returns Single.just(
            response
        )
        every { local.saveData(oauthToken, oauthTokenSecret) } returns mockk()
        val testObserver = TestObserver.create<RequestAccessTokenResponse>()

        // When
        authenticationRepository.requestAccessToken(oauthVerifier, requestToken)
            .subscribe(testObserver)

        // Then
        testObserver.assertValue(response)
        testObserver.assertComplete()
    }

    @Test
    fun requestAccessToken_exception() {
        // Given
        val oauthVerifier = "oauthVerifier"
        val requestToken = "requestToken"
        val oauthToken = "oauthToken"
        val oauthTokenSecret = "oauthTokenSecret"

        every { remote.requestAccessToken(oauthVerifier, requestToken) } returns Single.error(
            Exception()
        )
        every { local.saveData(oauthToken, oauthTokenSecret) } returns mockk()
        val testObserver = TestObserver.create<RequestAccessTokenResponse>()

        // When
        authenticationRepository.requestAccessToken(oauthVerifier, requestToken)
            .subscribe(testObserver)

        // Then
        testObserver.assertError(Exception::class.java)
        testObserver.assertNotComplete()
        verify(exactly = 0) {
            local.saveData(oauthToken, oauthTokenSecret)
        }
    }

    @Test
    fun isAuthenticated() {
        // Given
        val oauthToken = "oauthToken"
        val oauthTokenSecret = "oauthTokenSecret"

        every { local.getToken() } returns oauthToken
        every { local.getTokenSecret() } returns oauthTokenSecret

        // When
        val result = authenticationRepository.isAuthenticated()

        // Then
        Assert.assertEquals(true, result)
    }
}