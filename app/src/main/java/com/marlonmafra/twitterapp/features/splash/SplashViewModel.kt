package com.marlonmafra.twitterapp.features.splash

import androidx.lifecycle.MutableLiveData
import com.marlonmafra.twitterapp.features.BaseViewModel
import com.marlonmafra.twitterapp.features.IntentAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val interactor: SplashInteractor
) : BaseViewModel() {

    val intentAction = MutableLiveData<IntentAction>()
    val progressBar = MutableLiveData<Boolean>()

    fun checkAuthentication() {
        interactor.isAuthenticated()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { progressBar.value = true }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { progressBar.value = false }
            .subscribe({ handleSuccess(it) }, { handleError() })
            .autoDisposable()
    }

    private fun handleSuccess(logged: Boolean) {
        val action = if (logged) {
            IntentAction.Home
        } else {
            IntentAction.Login
        }
        intentAction.value = action
    }

    private fun handleError() {
        intentAction.value = IntentAction.Login
    }
}