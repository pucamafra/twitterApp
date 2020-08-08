package com.marlonmafra.twitterapp

import android.app.Application
import com.marlonmafra.data.di.NetworkModule
import com.marlonmafra.twitterapp.di.ApplicationComponent
import com.marlonmafra.twitterapp.di.ApplicationModule
import com.marlonmafra.twitterapp.di.DaggerApplicationComponent

class TwitterApp : Application() {

    var component: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        configureInjection()
    }

    private fun configureInjection() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .networkModule(NetworkModule(applicationContext))
            .build()
    }
}