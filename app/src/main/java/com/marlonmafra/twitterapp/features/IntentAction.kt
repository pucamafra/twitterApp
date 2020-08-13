package com.marlonmafra.twitterapp.features

sealed class IntentAction {

    object Login : IntentAction()

    object Home : IntentAction()
}