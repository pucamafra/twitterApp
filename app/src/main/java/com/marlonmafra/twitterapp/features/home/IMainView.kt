package com.marlonmafra.twitterapp.features.home

import com.marlonmafra.domain.model.User
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem

interface IMainView {

    fun showTweetList(items: List<AbstractFlexibleItem<*>>)

    fun changeProgressBarVisibility(show: Boolean)

    fun goToProfileScreen(user: User)

    fun hideRefreshingView()
}