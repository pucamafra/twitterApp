package com.marlonmafra.twitterapp.features.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.marlonmafra.twitterapp.R
import com.marlonmafra.twitterapp.TwitterApp
import com.marlonmafra.twitterapp.extension.changeVisibility
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_progress_bar.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        fun createInstance(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TwitterApp.component?.inject(this)
        viewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
        viewModel.retrieveTimeLine()
        setupLayout()
        setupObserver()
    }

    private fun setupLayout() {
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.container)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_timeline, R.id.navigation_map)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomBar.setupWithNavController(navController)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.retrieveTimeLine()
        }
    }

    private fun setupObserver() {
        viewModel.progressBar.observe(this, Observer { show ->
            loadingProgressBar.changeVisibility(show)
            if (!show) {
                swipeRefreshLayout.isRefreshing = false
            }
        })

        viewModel.showError.observe(this, Observer {
            if (it) {
                Snackbar.make(swipeRefreshLayout, R.string.something_went_wrong, LENGTH_LONG).show()
            }
        })
    }

    @TestOnly
    fun setTestViewModel(testViewModel: HomeViewModel) {
        viewModel = testViewModel
    }
}