package com.marlonmafra.data.rest

import com.marlonmafra.data.di.Test
import oauth.signpost.exception.OAuthException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import java.io.IOException
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val consumerKey: String,
    private val consumerSecret: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val accessToken = Test.oauthToken
        val accessTokenSecret = Test.oauthTokenSecret

        val consumer = OkHttpOAuthConsumer(consumerKey, consumerSecret)
        consumer.setTokenWithSecret(accessToken, accessTokenSecret)

        return try {
            chain.proceed(consumer.sign(request).unwrap() as Request)
        } catch (e: OAuthException) {
            throw IOException("Could not sign request", e)
        }
    }
}