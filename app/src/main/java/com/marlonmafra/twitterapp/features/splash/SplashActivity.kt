package com.marlonmafra.twitterapp.features.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marlonmafra.twitterapp.R
import com.marlonmafra.twitterapp.TwitterApp
import com.marlonmafra.twitterapp.features.home.MainActivity
import com.marlonmafra.twitterapp.features.login.LoginActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), ISplashView {

    @Inject
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        TwitterApp.component?.inject(this)
        presenter.attachView(this, lifecycle)
        presenter.checkAuthentication()
    }

    override fun userLogged() {
        startActivity(MainActivity.createInstance(this))
    }

    override fun goToLogin() {
        startActivity(LoginActivity.createInstance(this))
    }
}