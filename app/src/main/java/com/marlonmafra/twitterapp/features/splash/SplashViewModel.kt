package com.marlonmafra.twitterapp.features.splash

import androidx.lifecycle.MutableLiveData
import com.marlonmafra.twitterapp.features.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val interactor: SplashInteractor
) : BaseViewModel() {

    val checkAuthentication = MutableLiveData<SplashAction>()
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
            SplashAction.Home
        } else {
            SplashAction.Login
        }
        checkAuthentication.value = action
    }

    private fun handleError() {
        checkAuthentication.value = SplashAction.Login
    }
}