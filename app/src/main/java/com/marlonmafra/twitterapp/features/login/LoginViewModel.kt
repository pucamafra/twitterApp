package com.marlonmafra.twitterapp.features.login

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.marlonmafra.domain.model.RequestTokenResponse
import com.marlonmafra.twitterapp.extension.isValid
import com.marlonmafra.twitterapp.extension.toCallbackResponse
import com.marlonmafra.twitterapp.features.BaseViewModel
import com.marlonmafra.twitterapp.features.IntentAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val interactor: LoginInteractor
) : BaseViewModel() {

    val progressBar = MutableLiveData<Boolean>()
    val openCallback = MutableLiveData<String>()
    val showError = MutableLiveData<Boolean>()
    val intentAction = MutableLiveData<IntentAction>()

    fun authenticate() {
        interactor.requestToken()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { progressBar.value = true }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ handleAuthenticationSuccess(it) }, { handleError() })
            .autoDisposable()
    }

    private fun handleAuthenticationSuccess(it: RequestTokenResponse) {
        openCallback.value = it.oauthToken
    }

    fun checkCallbackResponse(uri: Uri?, callbackURL: String) {
        uri?.let {
            if (it.isValid(callbackURL)) {
                val callbackResponse = it.toCallbackResponse()
                val token = callbackResponse.oauthToken
                val verifier = callbackResponse.oauthVerifier
                if (token != null && verifier != null) {
                    requestAccessToken(token, verifier)
                    return
                }
            }
        }
        showError.value = true
    }

    fun requestAccessToken(token: String, oauthVerifier: String) {
        interactor.requestAccessToken(oauthVerifier, token)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { progressBar.value = true }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { progressBar.value = false }
            .subscribe({ handleAccessTokenSuccess() }, { handleError() })
            .autoDisposable()
    }

    private fun handleAccessTokenSuccess() {
        intentAction.value = IntentAction.Home
    }

    private fun handleError() {
        showError.value = true
        progressBar.value = false
    }
}