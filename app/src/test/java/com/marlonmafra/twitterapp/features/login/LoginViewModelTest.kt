package com.marlonmafra.twitterapp.features.login

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.marlonmafra.domain.model.RequestAccessTokenResponse
import com.marlonmafra.domain.model.RequestTokenResponse
import com.marlonmafra.twitterapp.common.Const
import com.marlonmafra.twitterapp.features.IntentAction
import com.marlonmafra.twitterapp.features.RxImmediateSchedulerRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    private val token = "token"
    private val verifier = "verifier"
    private val callbackURL =
        "www.twitter.com?${Const.OAUTH_TOKEN}=$token&${Const.OAUTH_VERIFIER}=$verifier"

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var loginViewModel: LoginViewModel

    @MockK
    private lateinit var interactor: LoginInteractor

    @MockK(relaxed = true)
    lateinit var progressBarObserver: Observer<Boolean>

    @MockK(relaxed = true)
    lateinit var openCallbackObserver: Observer<String>

    @MockK(relaxed = true)
    lateinit var showErrorObserver: Observer<Boolean>

    @MockK(relaxed = true)
    lateinit var intentActionObserver: Observer<IntentAction>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loginViewModel = LoginViewModel(interactor)
        loginViewModel.progressBar.observeForever(progressBarObserver)
        loginViewModel.openCallback.observeForever(openCallbackObserver)
        loginViewModel.showError.observeForever(showErrorObserver)
        loginViewModel.intentAction.observeForever(intentActionObserver)
        loginViewModel.showError.observeForever(showErrorObserver)
    }

    @Test
    fun checkAuthentication() {
        // Given
        val oauthToken = "oauthToken"
        val response: RequestTokenResponse = mockk()
        every { interactor.requestToken() } returns Single.just(response)
        every { response.oauthToken } returns oauthToken

        // When
        loginViewModel.authenticate()

        // Then
        verify {
            progressBarObserver.onChanged(true)
            openCallbackObserver.onChanged(oauthToken)
        }
    }

    @Test
    fun checkAuthentication_exception() {
        // Given
        val oauthToken = "oauthToken"
        val response: RequestTokenResponse = mockk()
        every { interactor.requestToken() } returns Single.error(Exception())
        every { response.oauthToken } returns oauthToken

        // When
        loginViewModel.authenticate()

        // Then
        verify {
            progressBarObserver.onChanged(true)
            progressBarObserver.onChanged(false)
            showErrorObserver.onChanged(true)
        }

        verify(exactly = 0) {
            openCallbackObserver.onChanged(oauthToken)
        }
    }

    @Test
    fun requestAccessToken() {
        // Given
        val token = "oauthToken"
        val oauthVerifier = "oauthVerifier"
        val response: RequestAccessTokenResponse = mockk()
        every { interactor.requestAccessToken(any(), any()) } returns Single.just(response)

        // When
        loginViewModel.requestAccessToken(token, oauthVerifier)

        // Then
        verify {
            progressBarObserver.onChanged(true)
            intentActionObserver.onChanged(IntentAction.Home)
            progressBarObserver.onChanged(false)
        }
    }

    @Test
    fun requestAccessToken_exception() {
        // Given
        val token = "oauthToken"
        val oauthVerifier = "oauthVerifier"
        every { interactor.requestAccessToken(any(), any()) } returns Single.error(Exception())

        // When
        loginViewModel.requestAccessToken(token, oauthVerifier)

        // Then
        verify {
            progressBarObserver.onChanged(true)
            progressBarObserver.onChanged(false)
            showErrorObserver.onChanged(true)
        }

        verify(exactly = 0) {
            intentActionObserver.onChanged(IntentAction.Home)
        }
    }

    @Test
    fun checkCallbackResponse() {
        // Given
        val uri: Uri = mockk()
        val response: RequestAccessTokenResponse = mockk()

        every { uri.toString() } returns callbackURL
        every { uri.getQueryParameter(Const.OAUTH_TOKEN) } returns token
        every { uri.getQueryParameter(Const.OAUTH_VERIFIER) } returns verifier
        every { interactor.requestAccessToken(verifier, token) } returns Single.just(response)

        // When
        loginViewModel.checkCallbackResponse(uri, callbackURL)

        // Then
        verify {
            progressBarObserver.onChanged(true)
            intentActionObserver.onChanged(IntentAction.Home)
            progressBarObserver.onChanged(false)
        }

        verify(exactly = 0) {
            showErrorObserver.onChanged(true)
        }
    }

    @Test
    fun checkCallbackResponse_nullUri() {
        // Given
        val uri: Uri? = null

        // When
        loginViewModel.checkCallbackResponse(uri, callbackURL)

        // Then
        verify {
            showErrorObserver.onChanged(true)
        }

        verify(exactly = 0) {
            intentActionObserver.onChanged(IntentAction.Home)
        }
    }

    @Test
    fun checkCallbackResponse_invalidUri() {
        // Given
        val uri: Uri = mockk()
        val response: RequestAccessTokenResponse = mockk()

        every { uri.toString() } returns "differentUri"
        every { uri.getQueryParameter(Const.OAUTH_TOKEN) } returns token
        every { uri.getQueryParameter(Const.OAUTH_VERIFIER) } returns verifier
        every { interactor.requestAccessToken(verifier, token) } returns Single.just(response)

        // When
        loginViewModel.checkCallbackResponse(uri, callbackURL)

        // Then
        verify {
            showErrorObserver.onChanged(true)
        }

        verify(exactly = 0) {
            intentActionObserver.onChanged(IntentAction.Home)
        }
    }
}