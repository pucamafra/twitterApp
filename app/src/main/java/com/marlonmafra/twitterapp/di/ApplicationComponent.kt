package com.marlonmafra.twitterapp.di

import com.marlonmafra.data.di.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {

}