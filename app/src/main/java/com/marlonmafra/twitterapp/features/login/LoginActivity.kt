package com.marlonmafra.twitterapp.features.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marlonmafra.twitterapp.R
import com.marlonmafra.twitterapp.TwitterApp
import com.marlonmafra.twitterapp.features.home.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), ILoginView {

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        TwitterApp.component?.inject(this)

        presenter.attachView(this, lifecycle)

        authenticateButton.setOnClickListener {
            presenter.authenticate()
        }

        accessTokenButton.setOnClickListener {
            presenter.requestAccessToken(verifier.editableText.toString())
        }
    }

    override fun userLogged() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    /* private fun authenticate() {
         unauthenticatedService
             .requestToken()
             .observeOn(AndroidSchedulers.mainThread())
             .subscribeOn(Schedulers.io())
             .map {
                 println(it)
                 it
             }
             .subscribe({
                 println(it)
                 val split = it.string().split('&')
                 requestToken = split.get(0).removePrefix("oauth_token=")

                 openCallBack(requestToken)
             }, {
                 println(it)
             })
     }
 */
    /* private fun requestAccessToken(verifier: String) {
         unauthenticatedService
             .accessToken(verifier, requestToken)
             .observeOn(AndroidSchedulers.mainThread())
             .subscribeOn(Schedulers.io())
             .map {
                 println(it)
                 it
             }
             .subscribe({
                 println(it)

                 val split = it.string().split('&')
                 val oauth_token = split.get(0).removePrefix("oauth_token=")
                 val requestTokenSecret = split.get(1).removePrefix("oauth_token_secret=")

                 Test.oauthToken = oauth_token
                 Test.oauthTokenSecret = requestTokenSecret
             }, {
                 println(it)
             })
     }*/

    /*private fun timeLine() {
        apiService
            .userTimeline()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                println(it)
                it
            }
            .subscribe({
                println(it)
            }, {
                println(it)
            })
    }
*/
    /*  private fun openCallbackURL(oauthToken: String) {
          val url = "https://api.twitter.com/oauth/authenticate?oauth_token=$oauthToken"

          val uri = Uri.parse(url)
          val intent = Intent(Intent.ACTION_VIEW, uri)
          intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
          intent.addFlags(Intent.FLAG_FROM_BACKGROUND)
          startActivity(intent)
      }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val t = intent
        println()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println()
    }

    override fun onResume() {
        super.onResume()
        println()
    }

    override fun openCallBack(oauthToken: String) {
        //val url = "https://api.twitter.com/oauth/authenticate?oauth_token=$oauthToken"
        val url = getString(R.string.callback_url, oauthToken)
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND)
        startActivity(intent)
    }
}