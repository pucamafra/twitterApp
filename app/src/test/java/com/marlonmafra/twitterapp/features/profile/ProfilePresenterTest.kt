package com.marlonmafra.twitterapp.features.profile

import com.marlonmafra.domain.model.User
import com.marlonmafra.twitterapp.features.TestWithLifecycle
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ProfilePresenterTest : TestWithLifecycle() {

    @InjectMockKs
    private lateinit var presenter: ProfilePresenter

    @MockK(relaxed = true)
    private lateinit var view: IProfileView

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        presenter.attachView(view, lifecycle)
    }

    @Test
    fun initialize_withProfileBannerUrl() {
        // Given
        val user = mockk<User>()
        every { user.profileBannerUrl } returns "profileBannerUrl"

        // When
        presenter.initialize(user)

        // Then
        verify { view.setupUser(user) }
        verify(exactly = 0) { view.setupProfileBackground(any()) }
    }

    @Test
    fun initialize_noProfileBannerUrlWithLinkColor() {
        // Given
        val user = mockk<User>()
        every { user.profileBannerUrl } returns null
        every { user.profileLinkColor } returns "FFFFFFF"

        // When
        presenter.initialize(user)

        // Then
        verify {
            view.setupUser(user)
            view.setupProfileBackground(any())
        }
    }

    @Test
    fun initialize_noProfileBannerUrlNoLinkColor() {
        // Given
        val user = mockk<User>()
        every { user.profileBannerUrl } returns null
        every { user.profileLinkColor } returns null

        // When
        presenter.initialize(user)

        // Then
        verify { view.setupUser(user) }
        verify(exactly = 0) { view.setupProfileBackground(any()) }
    }
}