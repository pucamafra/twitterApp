package com.marlonmafra.twitterapp.di

import android.content.Context
import android.content.res.Resources
import com.marlonmafra.twitterapp.TwitterApp
import com.marlonmafra.twitterapp.features.home.HomeViewModelFactory
import com.marlonmafra.twitterapp.features.home.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val app: TwitterApp
) {

    @Provides
    fun provideContext(): Context {
        return app.applicationContext
    }

    @Provides
    fun provideResources(context: Context): Resources {
        return context.resources
    }

    @Provides
    @Singleton
    fun provideHomeViewModelFactory(interactor: MainInteractor): HomeViewModelFactory {
        return HomeViewModelFactory(interactor)
    }
}