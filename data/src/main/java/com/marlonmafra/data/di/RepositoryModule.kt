package com.marlonmafra.data.di

import android.content.Context
import com.marlonmafra.data.repository.authentication.AuthenticationRepository
import com.marlonmafra.data.repository.authentication.local.AuthenticationLocalData
import com.marlonmafra.data.repository.authentication.local.IAuthenticationLocalDataSource
import com.marlonmafra.data.repository.authentication.remote.AuthenticationRemoteDataSource
import com.marlonmafra.data.repository.authentication.remote.IAuthenticationRemoteDataSource
import com.marlonmafra.data.repository.twitter.TwitterRepository
import com.marlonmafra.data.repository.twitter.remote.ITwitterRemoteDataSource
import com.marlonmafra.data.repository.twitter.remote.TwitterRemoteDataSource
import com.marlonmafra.data.rest.ApiService
import com.marlonmafra.data.rest.UnauthenticatedService
import com.marlonmafra.domain.repository.IAuthenticationDataSource
import com.marlonmafra.domain.repository.ITwitterDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideIAuthenticationDataSource(
        localDataSource: AuthenticationLocalData,
        remoteDataSource: AuthenticationRemoteDataSource
    ): IAuthenticationDataSource {
        return AuthenticationRepository(localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideAuthenticationRemoteDataSource(unauthenticatedService: UnauthenticatedService): IAuthenticationRemoteDataSource {
        return AuthenticationRemoteDataSource(unauthenticatedService)
    }

    @Provides
    @Singleton
    fun providesAuthenticationLocalData(context: Context): IAuthenticationLocalDataSource {
        return AuthenticationLocalData(context)
    }

    @Provides
    @Singleton
    fun provideTwitterRepository(twitterRemoteDataSource: TwitterRemoteDataSource): ITwitterDataSource {
        return TwitterRepository(twitterRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideTwitterRemoteDataSource(apiService: ApiService): ITwitterRemoteDataSource {
        return TwitterRemoteDataSource(apiService)
    }
}