package com.marlonmafra.data.repository.authentication.remote

import com.marlonmafra.data.rest.UnauthenticatedService
import com.marlonmafra.domain.model.RequestAccessTokenResponse
import com.marlonmafra.domain.model.RequestTokenResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test

class AuthenticationRemoteDataSourceTest {
    private val REQUEST_TOKEN_RESPONSE =
        "oauth_token=Z6eEdO8MOmk394WozF5oKyuAv855l4Mlqo7hhlSLik&oauth_token_secret=Kd75W4OQfb2oJTV0vzGzeXftVAwgMnEK9MumzYcM&oauth_callback_confirmed=true"
    private val ACCESS_TOKEN_RESPONSE =
        "oauth_token=6253282-eWudHldSbIaelX7swmsiHImEL4KinwaGloHANdrY&oauth_token_secret=2EEfA6BG5ly3sR3XjE0IBSnlQu4ZrUzPiYTmrkVU&user_id=6253282&screen_name=twitterapi"

    @InjectMockKs
    lateinit var authenticationRemoteDataSource: AuthenticationRemoteDataSource

    @MockK
    lateinit var anauthenticatedService: UnauthenticatedService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun requestToken() {
        // Given
        val rawResponse = mockk<ResponseBody>()
        val testObserver = TestObserver.create<RequestTokenResponse>()

        every { rawResponse.string() } returns REQUEST_TOKEN_RESPONSE
        every { anauthenticatedService.requestToken() } returns Single.just(rawResponse)

        // When
        authenticationRemoteDataSource.requestToken().subscribe(testObserver)

        // Then
        testObserver.assertComplete()
    }

    @Test
    fun requestToken_exception() {
        // Given
        val testObserver = TestObserver.create<RequestTokenResponse>()
        every { anauthenticatedService.requestToken() } returns Single.error(Exception())

        // When
        authenticationRemoteDataSource.requestToken().subscribe(testObserver)

        // Then
        testObserver.assertError(Exception::class.java)
        testObserver.assertNotComplete()
    }

    @Test
    fun requestAccessToken() {
        // Given
        val response = mockk<ResponseBody>()
        val oauthVerifier = "oauthVerifier"
        val requestToken = "requestToken"

        every { response.string() } returns ACCESS_TOKEN_RESPONSE
        every {
            anauthenticatedService.accessToken(
                oauthVerifier,
                requestToken
            )
        } returns Single.just(
            response
        )
        val testObserver = TestObserver.create<RequestAccessTokenResponse>()

        // When
        authenticationRemoteDataSource.requestAccessToken(oauthVerifier, requestToken)
            .subscribe(testObserver)

        // Then
        //  testObserver.assertValue(response)
        testObserver.assertComplete()
    }

    @Test
    fun requestAccessToken_exception() {
        // Given
        val oauthVerifier = "oauthVerifier"
        val requestToken = "requestToken"

        every {
            anauthenticatedService.accessToken(
                oauthVerifier,
                requestToken
            )
        } returns Single.error(
            Exception()
        )
        val testObserver = TestObserver.create<RequestAccessTokenResponse>()

        // When
        authenticationRemoteDataSource.requestAccessToken(oauthVerifier, requestToken)
            .subscribe(testObserver)

        // Then
        testObserver.assertError(Exception::class.java)
        testObserver.assertNotComplete()
    }
}