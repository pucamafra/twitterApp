package com.marlonmafra.twitterapp.features.home.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marlonmafra.domain.model.Tweet
import com.marlonmafra.domain.model.User
import com.marlonmafra.twitterapp.R
import com.marlonmafra.twitterapp.extension.getString
import com.marlonmafra.twitterapp.extension.loadUrl
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.twitter_item_list.*

class TweetItemList(
    private val tweet: Tweet,
    private val profileClickObserver: PublishSubject<User>
) : AbstractFlexibleItem<TweetItemList.ViewHolder>() {

    override fun equals(other: Any?): Boolean = when (other) {
        is TweetItemList -> tweet == other.tweet
        else -> false
    }

    override fun hashCode(): Int = tweet.hashCode()

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.bind(tweet, profileClickObserver)
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ViewHolder = ViewHolder(view, adapter)

    override fun getLayoutRes(): Int = R.layout.twitter_item_list

    inner class ViewHolder(
        override val containerView: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ) : FlexibleViewHolder(containerView, adapter),
        LayoutContainer {

        fun bind(
            tweet: Tweet,
            profileClickObserver: PublishSubject<User>
        ) = with(tweet) {
            username.text = user.name
            userId.text = getString(R.string.screen_name, user.screenName)
            tweetText.text = text
            userPicture.loadUrl(user.profileImageUrl)
            userPicture.setOnClickListener {
                profileClickObserver.onNext(user)
            }
        }
    }
}