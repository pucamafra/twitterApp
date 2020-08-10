package com.marlonmafra.twitterapp.features.login

import com.marlonmafra.data.di.Test
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
            .map {
                println(it)
                it
            }
            .subscribe({
                println(it)
                // val split = it.string().split('&')
                // val requestToken = split.get(0).removePrefix("oauth_token=")
                requestToken = it.oauthToken
                //openCallbackURL(requestToken)
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
            .map {
                println(it)
                val oauthToken = it.oauthToken
                val requestTokenSecret = it.oauthTokenSecret

                Test.oauthToken = oauthToken
                Test.oauthTokenSecret = requestTokenSecret

                it
            }
            .subscribe({
                println(it)
                view?.userLogged()
            }, {
                println(it)
            })
            .autoDisposable()
    }
}