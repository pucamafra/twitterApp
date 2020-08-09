package com.marlonmafra.data.rest

import oauth.signpost.exception.OAuthException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import java.io.IOException
import javax.inject.Inject

class UnauthInterceptor @Inject constructor(
    private val consumer: OkHttpOAuthConsumer
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        return try {
            chain.proceed(consumer.sign(request).unwrap() as Request)
        } catch (e: OAuthException) {
            throw IOException("Could not sign request", e)
        }
    }
}