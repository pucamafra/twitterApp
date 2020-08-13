package com.marlonmafra.twitterapp.features.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marlonmafra.twitterapp.R
import com.marlonmafra.twitterapp.TwitterApp
import com.marlonmafra.twitterapp.extension.changeVisibility
import com.marlonmafra.twitterapp.features.home.MainActivity
import com.marlonmafra.twitterapp.features.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var splashViewModelFactory: SplashViewModelFactory

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        TwitterApp.component?.inject(this)
        viewModel = ViewModelProvider(this, splashViewModelFactory).get(SplashViewModel::class.java)
        viewModel.checkAuthentication()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.checkAuthentication.observe(this, Observer {
            val intent = if (it == SplashAction.Login) {
                LoginActivity.createInstance(this)
            } else {
                MainActivity.createInstance(this)
            }
            startActivity(intent)
        })

        viewModel.progressBar.observe(this, Observer {
            progressBar.changeVisibility(it)
        })
    }
}