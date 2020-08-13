package com.marlonmafra.twitterapp.features.splash

sealed class SplashAction {

    object Login : SplashAction()

    object Home : SplashAction()
}