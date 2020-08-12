package com.marlonmafra.twitterapp.features.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.marlonmafra.domain.model.User
import com.marlonmafra.twitterapp.R
import com.marlonmafra.twitterapp.TwitterApp
import com.marlonmafra.twitterapp.extension.changeVisibility
import com.marlonmafra.twitterapp.features.profile.ProfileActivity
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_progress_bar.*
import kotlinx.android.synthetic.main.toolbar.toolbar
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
        presenter.retrieveTimeLine()
    }

    private fun setupLayout() {
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        tweetListView.layoutManager = layoutManager
        tweetListView.addItemDecoration(dividerItemDecoration)
        tweetListView.adapter = adapter
        setSupportActionBar(toolbar)
        swipeRefreshLayout.setOnRefreshListener {
            presenter.retrieveTimeLine()
        }
    }

    override fun showTweetList(items: List<AbstractFlexibleItem<*>>) {
        adapter.updateDataSet(items)
    }

    override fun changeProgressBarVisibility(show: Boolean) {
        loadingProgressBar.changeVisibility(show)
    }

    override fun hideRefreshingView() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun goToProfileScreen(user: User) {
        startActivity(ProfileActivity.createInstance(this, user))
    }

    override fun onRetrieveTweetError() {
        Snackbar.make(swipeRefreshLayout, R.string.something_went_wrong, Snackbar.LENGTH_LONG)
            .show()
    }
}