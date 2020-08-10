package com.marlonmafra.twitterapp.di

import com.marlonmafra.data.di.NetworkModule
import com.marlonmafra.data.di.RepositoryModule
import com.marlonmafra.twitterapp.TwitterApp
import com.marlonmafra.twitterapp.features.home.MainActivity
import com.marlonmafra.twitterapp.features.login.LoginActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, RepositoryModule::class])
interface ApplicationComponent {
    fun inject(twitterApp: TwitterApp)

    fun inject(mainActivity: MainActivity)

    fun inject(loginActivity: LoginActivity)
}