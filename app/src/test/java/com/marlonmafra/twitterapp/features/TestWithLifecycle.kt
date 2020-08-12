package com.marlonmafra.twitterapp.features

import androidx.lifecycle.Lifecycle
import io.mockk.impl.annotations.MockK
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.TrampolineScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before

abstract class TestWithLifecycle {

    @MockK(relaxed = true)
    protected lateinit var lifecycle: Lifecycle

    @Before
    open fun setupPlugins() {
        RxJavaPlugins.setInitIoSchedulerHandler { TrampolineScheduler.instance() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    open fun resetPlugins() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}
