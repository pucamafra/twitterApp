package com.marlonmafra.twitterapp.features.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.marlonmafra.twitterapp.R
import com.marlonmafra.twitterapp.TwitterApp
import com.marlonmafra.twitterapp.extension.changeVisibility
import com.marlonmafra.twitterapp.features.IntentAction
import com.marlonmafra.twitterapp.features.home.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.loading_progress_bar.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    companion object {
        fun createInstance(context: Context): Intent = Intent(context, LoginActivity::class.java)
    }

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        TwitterApp.component?.inject(this)
        viewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)
        setupLayout()
        setupObservers()
    }

    private fun setupLayout() {
        signInButton.setOnClickListener {
            viewModel.authenticate()
        }
    }

    private fun setupObservers() {
        viewModel.progressBar.observe(this, Observer { show ->
            loadingProgressBar.changeVisibility(show)
        })

        viewModel.showError.observe(this, Observer {
            if (it) {
                Snackbar.make(rootLayout, R.string.something_went_wrong, LENGTH_LONG).show()
            }
        })

        viewModel.openCallback.observe(this, Observer {
            openCallBack(it)
        })

        viewModel.intentAction.observe(this, Observer {
            if (it == IntentAction.Home) {
                startActivity(MainActivity.createInstance(this))
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (intent?.action?.equals(Intent.ACTION_VIEW) == true) {
            val callbackURL = getString(R.string.callback_url)
            viewModel.checkCallbackResponse(intent.data, callbackURL)
        }
    }

    private fun openCallBack(oauthToken: String) {
        val uri = Uri.parse(getString(R.string.authentication_url, oauthToken))
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}