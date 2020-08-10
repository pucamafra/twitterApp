package com.marlonmafra.data.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.marlonmafra.data.BuildConfig
import com.marlonmafra.data.R
import com.marlonmafra.data.repository.authentication.local.AuthenticationLocalData
import com.marlonmafra.data.rest.ApiService
import com.marlonmafra.data.rest.AuthInterceptor
import com.marlonmafra.data.rest.UnauthInterceptor
import com.marlonmafra.data.rest.UnauthenticatedService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule(
    private val context: Context
) {
    companion object {
        private const val TIMEOUT_DEFAULT_TIME_IN_SECONDS = 30L
    }

    private val consumerKey =
        context.resources.getString(R.string.consumer_key)
    private val consumerSecret =
        context.resources.getString(R.string.consumer_secret)

    private val accessToken =
        context.resources.getString(R.string.access_token)
    private val accessTokenSecret =
        context.resources.getString(R.string.access_token_secret)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    // REST
    @Singleton
    @Provides
    fun provideUnauthInterceptor(): UnauthInterceptor {
        val consumer = OkHttpOAuthConsumer(consumerKey, consumerSecret)
        consumer.setTokenWithSecret(accessToken, accessTokenSecret)
        return UnauthInterceptor(consumer)
    }

    @Singleton
    @Provides
    @Named("unauth-client")
    fun provideOkHttpClient(unauthInterceptor: UnauthInterceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(TIMEOUT_DEFAULT_TIME_IN_SECONDS, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(TIMEOUT_DEFAULT_TIME_IN_SECONDS, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(TIMEOUT_DEFAULT_TIME_IN_SECONDS, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(interceptor)
        okHttpClientBuilder.addInterceptor(unauthInterceptor)
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    @Named("unauth-retrofit")
    fun provideRetrofitBuilder(gson: Gson, @Named("unauth-client") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.api_base_url))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideUnauthenticatedService(@Named("unauth-retrofit") retrofit: Retrofit): UnauthenticatedService {
        return retrofit.create(UnauthenticatedService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(authenticationLocalData: AuthenticationLocalData): AuthInterceptor {
        return AuthInterceptor(consumerKey, consumerSecret, authenticationLocalData)
    }

    @Singleton
    @Provides
    @Named("auth-client")
    fun provideAuthOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(TIMEOUT_DEFAULT_TIME_IN_SECONDS, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(TIMEOUT_DEFAULT_TIME_IN_SECONDS, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(TIMEOUT_DEFAULT_TIME_IN_SECONDS, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        okHttpClientBuilder.addInterceptor(interceptor)
        okHttpClientBuilder.addInterceptor(authInterceptor)

        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    @Named("auth-retrofit")
    fun provideAuthRetrofitBuilder(
        gson: Gson,
        @Named("auth-client") client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.api_base_url))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(@Named("auth-retrofit") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}