package com.marlonmafra.data.di

import com.marlonmafra.data.repository.authentication.AuthenticationRepository
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
        remoteDataSource: AuthenticationRemoteDataSource
    ): IAuthenticationDataSource {
        return AuthenticationRepository(remoteDataSource)
    }
}