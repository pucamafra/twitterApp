package com.marlonmafra.twitterapp.features.splash

import com.marlonmafra.data.repository.authentication.AuthenticationRepository
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashInteractor @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    fun isAuthenticated(): Single<Boolean> {
        return Single.just(authenticationRepository.isAuthenticated())
            .delay(3, TimeUnit.SECONDS)
    }
}