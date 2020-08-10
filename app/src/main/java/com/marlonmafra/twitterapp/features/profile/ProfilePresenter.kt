package com.marlonmafra.twitterapp.features.profile

import com.marlonmafra.domain.model.User
import com.marlonmafra.twitterapp.features.LifecyclePresenter
import javax.inject.Inject

class ProfilePresenter @Inject constructor() : LifecyclePresenter<IProfileView>() {

    fun initialize(user: User) {
        view?.setupUser(user)

        if (user.profileBannerUrl.isNullOrBlank()) {
            user.profileLinkColor?.let {
                view?.setupProfileBackground("#$it")
            }
        }
    }
}