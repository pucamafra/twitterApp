package com.marlonmafra.twitterapp.features.splash

import com.marlonmafra.twitterapp.features.LifecyclePresenter
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val interactor: SplashInteractor
) : LifecyclePresenter<ISplashView>() {

    fun checkAuthentication() {
        interactor.isAuthenticated()
            .subscribe({ handleSuccess(it) }, { handlerError() })
            .autoDisposable()
    }

    private fun handleSuccess(logged: Boolean) {
        if (logged) {
            view?.goToHome()
        } else {
            view?.goToLogin()
        }
    }

    private fun handlerError() {
        view?.goToLogin()
    }
}