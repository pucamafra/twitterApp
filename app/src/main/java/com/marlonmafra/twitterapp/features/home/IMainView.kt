package com.marlonmafra.twitterapp.features.home

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem

interface IMainView {

    fun showTweetList(items: List<AbstractFlexibleItem<*>>)
}