package com.marlonmafra.data.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.marlonmafra.data.BuildConfig
import com.marlonmafra.data.R
import com.marlonmafra.data.rest.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule(
    private val context: Context
) {
    companion object {
        private const val TIMEOUT_DEFAULT_TIME_IN_SECONDS = 30L
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    // REST
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(TIMEOUT_DEFAULT_TIME_IN_SECONDS, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(TIMEOUT_DEFAULT_TIME_IN_SECONDS, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(TIMEOUT_DEFAULT_TIME_IN_SECONDS, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        okHttpClientBuilder.addInterceptor(interceptor)

        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.api_base_url))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}