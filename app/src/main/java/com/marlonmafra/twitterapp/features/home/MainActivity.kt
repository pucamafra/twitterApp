package com.marlonmafra.twitterapp.features.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marlonmafra.twitterapp.R
import com.marlonmafra.twitterapp.TwitterApp
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IMainView {

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TwitterApp.component?.inject(this)
        presenter.attachView(this, lifecycle)
    }

    override fun showTweetList(items: List<AbstractFlexibleItem<*>>) {

    }
}