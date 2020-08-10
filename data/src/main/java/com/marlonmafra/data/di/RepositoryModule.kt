package com.marlonmafra.data.di

import android.content.Context
import com.marlonmafra.data.repository.authentication.AuthenticationRepository
import com.marlonmafra.data.repository.authentication.local.AuthenticationLocalData
import com.marlonmafra.data.repository.authentication.remote.AuthenticationRemoteDataSource
import com.marlonmafra.domain.repository.IAuthenticationDataSource
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
    fun providesAuthenticationLocalData(context: Context): AuthenticationLocalData {
        return AuthenticationLocalData(context)
    }
}