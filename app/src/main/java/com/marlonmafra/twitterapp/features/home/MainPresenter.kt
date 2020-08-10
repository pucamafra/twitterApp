package com.marlonmafra.twitterapp.features.home

import androidx.lifecycle.Lifecycle
import com.marlonmafra.domain.model.Tweet
import com.marlonmafra.domain.model.User
import com.marlonmafra.twitterapp.features.LifecyclePresenter
import com.marlonmafra.twitterapp.features.home.ui.TweetItemList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val interactor: MainInteractor
) : LifecyclePresenter<IMainView>() {

    private val profileClickObserver = PublishSubject.create<User>()

    override fun attachView(view: IMainView, lifecycle: Lifecycle) {
        super.attachView(view, lifecycle)
        setupObserver()
        timeLine()
    }

    private fun setupObserver() {
        profileClickObserver
            .subscribe({
                view?.goToProfileScreen(it)
            }, {

            })
            .autoDisposable()
    }

    private fun timeLine() {
        interactor.fetchHomeTimeline()
            .doOnSubscribe { view?.changeProgressBarVisibility(show = true) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doAfterTerminate { view?.changeProgressBarVisibility(show = false) }
            .subscribe({
                handleSuccess(it)
            }, {
                println(it)
            })
            .autoDisposable()
    }

    private fun handleSuccess(tweetList: List<Tweet>) {
        val items = tweetList.map { TweetItemList(it, profileClickObserver) }
        view?.showTweetList(items)
    }
}