package com.marlonmafra.data.di

import com.marlonmafra.data.repository.news.AuthenticationRepository
import com.marlonmafra.data.repository.news.remote.AuthenticationRemoteDataSource
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