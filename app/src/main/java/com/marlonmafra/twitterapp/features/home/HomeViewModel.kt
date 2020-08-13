package com.marlonmafra.twitterapp.features.home

import androidx.lifecycle.MutableLiveData
import com.marlonmafra.domain.model.Tweet
import com.marlonmafra.domain.model.User
import com.marlonmafra.twitterapp.features.BaseViewModel
import com.marlonmafra.twitterapp.features.home.ui.TweetItemList
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val interactor: MainInteractor
) : BaseViewModel() {

    private var profileClickObserver = PublishSubject.create<User>()
    val userClicked = MutableLiveData<User>()
    val progressBar = MutableLiveData<Boolean>()
    val tweeList = MutableLiveData<List<Tweet>>()
    val tweetListMapped = MutableLiveData<List<AbstractFlexibleItem<*>>>()

    init {
        retrieveTimeLine()
        setupObserver()
    }

    private fun setupObserver() {
        profileClickObserver
            .subscribe({
                userClicked.value = it
            }, {
            })
            .autoDisposable()
    }

    fun retrieveTimeLine() {
        interactor.fetchHomeTimeline()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { progressBar.value = true }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { progressBar.value = false }
            .subscribe({
                handleResult(it)
            }, {
                //view?.onRetrieveTweetError()
            })
            .autoDisposable()
    }

    private fun handleResult(list: List<Tweet>) {
        tweeList.value = list
        tweetListMapped.value = list.map { TweetItemList(it, profileClickObserver) }
    }
}