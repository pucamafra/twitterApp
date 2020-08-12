package com.marlonmafra.twitterapp.features.home

import com.marlonmafra.domain.model.Tweet
import com.marlonmafra.domain.model.User
import com.marlonmafra.twitterapp.features.TestWithLifecycle
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test

class MainPresenterTest : TestWithLifecycle() {

    private var profileClickObserver = PublishSubject.create<User>()

    private lateinit var presenter: MainPresenter

    @MockK
    private lateinit var interactor: MainInteractor

    @MockK(relaxed = true)
    private lateinit var view: IMainView

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        presenter = MainPresenter(interactor, profileClickObserver)
    }

    @Test
    fun retrieveTimeLine() {
        // Given
        val tweetList = ArrayList<Tweet>()
        every { interactor.fetchHomeTimeline() } returns Single.just(tweetList)

        // When
        presenter.attachView(view, lifecycle)
        presenter.retrieveTimeLine()

        // Then
        verify { view.showTweetList(any()) }
        verify(exactly = 0) { view.onRetrieveTweetError() }
        verify { view.changeProgressBarVisibility(true) }
        verify { view.changeProgressBarVisibility(false) }
        verify { view.hideRefreshingView() }
    }

    @Test
    fun retrieveTimeLine_exception() {
        // Given
        every { interactor.fetchHomeTimeline() } returns Single.error(Exception())

        // When
        presenter.attachView(view, lifecycle)
        presenter.retrieveTimeLine()

        // Then
        verify(exactly = 0) { view.showTweetList(any()) }
        verify { view.onRetrieveTweetError() }
        verify { view.changeProgressBarVisibility(true) }
        verify { view.changeProgressBarVisibility(false) }
        verify { view.hideRefreshingView() }
    }

    @Test
    fun setupObserver() {
        val testObserver = TestObserver<User>()
        val user = mockk<User>()
        profileClickObserver.subscribe(testObserver)

        // When
        presenter.attachView(view, lifecycle)
        profileClickObserver.onNext(user)

        // Then
        testObserver.assertValue(user)
        verify { view.goToProfileScreen(user) }
    }

    @Test
    fun setupObserver_exception() {
        val testObserver = TestObserver<User>()
        val user = mockk<User>()
        profileClickObserver.subscribe(testObserver)

        // When
        presenter.attachView(view, lifecycle)
        profileClickObserver.onError(Exception())

        // Then
        testObserver.assertError(Exception::class.java)
        verify(exactly = 0) { view.goToProfileScreen(user) }
    }
}