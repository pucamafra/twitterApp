package com.marlonmafra.twitterapp.di

import android.content.Context
import android.content.res.Resources
import com.marlonmafra.twitterapp.TwitterApp
import dagger.Module
import dagger.Provides

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
}