package com.marlonmafra.twitterapp.features.home

import com.marlonmafra.data.di.Test
import com.marlonmafra.twitterapp.features.LifecyclePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val interactor: MainInteractor
) : LifecyclePresenter<IMainView>() {

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

    fun requestAccessToken(
        oauthVerifier: String
    ) {
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
            .flatMap {
                interactor.fetchHomeTimeline()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
            }
            .subscribe({
                println(it)
            }, {
                println(it)
            })
            .autoDisposable()
    }

    fun timeLine() {
        interactor.fetchHomeTimeline()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                println(it)
                it
            }
            .subscribe({
                println(it)
            }, {
                println(it)
            })
            .autoDisposable()
    }
}