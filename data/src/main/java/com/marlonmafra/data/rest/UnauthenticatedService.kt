package com.marlonmafra.data.rest

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface UnauthenticatedService {

    @POST("oauth/request_token")
    fun requestToken(@Query("oauth_callback") test: String): Single<ResponseBody>

    @Headers("Content-Type: application/x-www-form-urlencoded;charset=UTF-8")
    @POST("oauth/access_token")
    fun accessToken(
        @Query("oauth_verifier") oauthVerifier: String,
        @Query("oauth_token") requestToken: String
    ): Single<ResponseBody>
}