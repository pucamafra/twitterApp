package com.marlonmafra.twitterapp.features

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class LifecyclePresenter<View> : LifecycleObserver {

    var view: View? = null
        private set

    private val compositeDisposable = CompositeDisposable()

    open fun attachView(view: View, lifecycle: Lifecycle) {
        this.view = view
        lifecycle.addObserver(this)
    }

    protected fun Disposable.autoDisposable() {
        compositeDisposable.add(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected fun onViewDestroyed() {
        compositeDisposable.dispose()
        view = null
    }
}