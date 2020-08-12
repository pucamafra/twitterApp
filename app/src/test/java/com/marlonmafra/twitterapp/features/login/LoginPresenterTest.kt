package com.marlonmafra.twitterapp.features.login

import com.marlonmafra.domain.model.RequestAccessTokenResponse
import com.marlonmafra.domain.model.RequestTokenResponse
import com.marlonmafra.twitterapp.features.TestWithLifecycle
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class LoginPresenterTest : TestWithLifecycle() {

    @MockK
    private lateinit var interactor: LoginInteractor

    @MockK(relaxed = true)
    private lateinit var view: ILoginView

    @InjectMockKs
    private lateinit var presenter: LoginPresenter

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        presenter.attachView(view, lifecycle)
    }

    @Test
    fun authenticate() {
        // Given
        val oauthToken = "oauthToken"
        val response: RequestTokenResponse = mockk()
        every { interactor.requestToken() } returns Single.just(response)
        every { response.oauthToken } returns oauthToken

        // When
        presenter.authenticate()

        // Then
        verify { view.openCallBack(oauthToken) }
        verify(exactly = 0) { view.onLoginError() }
    }

    @Test
    fun authenticate_exception() {
        // Given
        val oauthToken = "oauthToken"
        val response: RequestTokenResponse = mockk()
        every { interactor.requestToken() } returns Single.error(Exception())
        every { response.oauthToken } returns oauthToken

        // When
        presenter.authenticate()

        // Then
        verify { view.onLoginError() }
        verify(exactly = 0) { view.openCallBack(oauthToken) }
    }

    @Test
    fun requestAccessToken() {
        // Given
        val oauthVerifier = "oauthVerifier"
        val requestToken = "requestToken"

        val response: RequestAccessTokenResponse = mockk()
        every { interactor.requestAccessToken(oauthVerifier, requestToken) } returns Single.just(
            response
        )

        // When
        presenter.requestToken = requestToken
        presenter.requestAccessToken(oauthVerifier)

        // Then
        verify { view.goToHome() }
        verify(exactly = 0) { view.onLoginError() }
    }

    @Test
    fun requestAccessToken_exception() {
        // Given
        val oauthVerifier = "oauthVerifier"
        val requestToken = "requestToken"

        every { interactor.requestAccessToken(oauthVerifier, requestToken) } returns Single.error(
            Exception()
        )

        // When
        presenter.requestToken = requestToken
        presenter.requestAccessToken(oauthVerifier)

        // Then
        verify(exactly = 0) { view.goToHome() }
        verify { view.onLoginError() }
    }
}