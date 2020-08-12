package com.marlonmafra.twitterapp.features.login

import com.marlonmafra.twitterapp.features.LifecyclePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val interactor: LoginInteractor
) : LifecyclePresenter<ILoginView>() {

    private var requestToken: String = ""

    fun authenticate() {
        interactor.requestToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                requestToken = it.oauthToken
                view?.openCallBack(requestToken)
            }, {
                println(it)
            })
            .autoDisposable()
    }

    fun requestAccessToken(oauthVerifier: String) {
        interactor.requestAccessToken(oauthVerifier, requestToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                view?.goToHome()
            }, {
                println(it)
            })
            .autoDisposable()
    }
}