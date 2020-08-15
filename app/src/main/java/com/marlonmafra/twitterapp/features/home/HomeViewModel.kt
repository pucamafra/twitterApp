package com.marlonmafra.twitterapp.features.home

import androidx.lifecycle.MutableLiveData
import com.marlonmafra.domain.model.Tweet
import com.marlonmafra.domain.model.User
import com.marlonmafra.twitterapp.features.BaseViewModel
import com.marlonmafra.twitterapp.features.IntentAction
import com.marlonmafra.twitterapp.features.home.ui.TweetItemList
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val interactor: HomeInteractor
) : BaseViewModel() {

    val userProfileClicked = MutableLiveData<User>()
    val progressBar = MutableLiveData<Boolean>()
    val tweeList = MutableLiveData<List<Tweet>>()
    val tweetListMapped = MutableLiveData<List<AbstractFlexibleItem<*>>>()
    val showError = MutableLiveData<Boolean>()
    val intentAction = MutableLiveData<IntentAction>()

    fun retrieveTimeLine() {
        interactor.fetchHomeTimeline()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { progressBar.value = true }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { progressBar.value = false }
            .subscribe({ handleResult(it) }, { handleError() })
            .autoDisposable()
    }

    private fun handleResult(list: List<Tweet>) {
        tweeList.value = list
        tweetListMapped.value = list.map { TweetItemList(it, userProfileClicked) }
    }

    private fun handleError() {
        showError.value = true
    }

    fun logout() {
        interactor.logout()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { progressBar.value = true }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { progressBar.value = false }
            .subscribe({ handleLogoutSuccess() }, { handleLogoutError() })
            .autoDisposable()
    }

    private fun handleLogoutSuccess() {
        intentAction.value = IntentAction.Login
    }

    private fun handleLogoutError() {
        showError.value = true
    }
}