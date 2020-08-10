package com.marlonmafra.twitterapp.features.profile

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marlonmafra.domain.model.User
import com.marlonmafra.twitterapp.R
import com.marlonmafra.twitterapp.TwitterApp
import com.marlonmafra.twitterapp.extension.formatJoinedDate
import com.marlonmafra.twitterapp.extension.formatValue
import com.marlonmafra.twitterapp.extension.loadUrl
import kotlinx.android.synthetic.main.activity_profile.*
import javax.inject.Inject

class ProfileActivity : AppCompatActivity(), IProfileView {

    companion object {
        private const val USER_EXTRA = "ProfileActivity.USER_EXTRA"
        fun createInstance(context: Context, user: User): Intent =
            Intent(context, ProfileActivity::class.java).apply {
                putExtra(USER_EXTRA, user)
            }
    }

    @Inject
    lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        TwitterApp.component?.inject(this)
        setupLayout()
        presenter.attachView(this, lifecycle)
        intent.getParcelableExtra<User>(USER_EXTRA)?.let {
            presenter.initialize(it)
        }
    }

    private fun setupLayout() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun setupUser(user: User) {
        username.text = user.name
        screenName.text = getString(R.string.screen_name, user.screenName)
        userDescription.text = user.description
        location.text = user.location
        header.loadUrl(user.profileBannerUrl)
        userPicture.loadUrl(user.profileImageUrl)
        joined.text = getString(R.string.joined, user.formatJoinedDate())
        followingView.setValue(user.following.formatValue())
        followerView.setValue(user.followers.formatValue())
    }

    override fun setupProfileBackground(color: String) {
        appBar.setBackgroundColor(Color.parseColor(color))
        toolbar.setBackgroundColor(Color.parseColor(color))
    }
}