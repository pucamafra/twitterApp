package com.marlonmafra.twitterapp.features.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.marlonmafra.twitterapp.R
import com.marlonmafra.twitterapp.TwitterApp
import com.marlonmafra.twitterapp.extension.changeVisibility
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_progress_bar.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IMainView {

    @Inject
    lateinit var presenter: MainPresenter

    private val adapter = FlexibleAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TwitterApp.component?.inject(this)
        setupLayout()
        presenter.attachView(this, lifecycle)
    }

    private fun setupLayout() {
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        tweetListView.layoutManager = layoutManager
        tweetListView.addItemDecoration(dividerItemDecoration)
        tweetListView.adapter = adapter
    }

    override fun showTweetList(items: List<AbstractFlexibleItem<*>>) {
        adapter.updateDataSet(items)
    }

    override fun changeProgressBarVisibility(show: Boolean) {
        loadingProgressBar.changeVisibility(show)
    }
}