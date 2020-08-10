package com.marlonmafra.twitterapp.features.login

import androidx.lifecycle.Lifecycle
import com.marlonmafra.twitterapp.features.LifecyclePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val interactor: LoginInteractor
) : LifecyclePresenter<ILoginView>() {

    private var requestToken: String = ""

    override fun attachView(view: ILoginView, lifecycle: Lifecycle) {
        super.attachView(view, lifecycle)
        checkAuth()
    }

    private fun checkAuth() {
        if (interactor.isAuthenticated()) {
            view?.userLogged()
        }
    }

    fun authenticate() {
        interactor.requestToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                println(it)
                it
            }
            .subscribe({
                println(it)
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
                println(it)
                view?.userLogged()
            }, {
                println(it)
            })
            .autoDisposable()
    }
}