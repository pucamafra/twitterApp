package com.marlonmafra.twitterapp.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(
    private val interactor: SplashInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(interactor) as T
    }
}