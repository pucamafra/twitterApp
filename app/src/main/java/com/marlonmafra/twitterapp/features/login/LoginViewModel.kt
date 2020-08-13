package com.marlonmafra.twitterapp.features.login

import androidx.lifecycle.MutableLiveData
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
            .subscribe({
                openCallback.value = it.oauthToken
            }, {
                handleError()
            })
            .autoDisposable()
    }

    fun requestAccessToken(token: String, oauthVerifier: String) {
        interactor.requestAccessToken(oauthVerifier, token)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { progressBar.value = true }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { progressBar.value = false }
            .subscribe({
                intentAction.value = IntentAction.Home
            }, {
                handleError()
            })
            .autoDisposable()
    }

    private fun handleError() {
        showError.value = true
        progressBar.value = false
    }
}