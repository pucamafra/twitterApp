package com.marlonmafra.twitterapp.features.profile

import com.marlonmafra.domain.model.User

interface IProfileView {

    fun setupUser(user: User)

    fun setupProfileBackground(color: String)
}