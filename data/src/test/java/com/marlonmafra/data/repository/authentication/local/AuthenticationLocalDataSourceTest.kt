package com.marlonmafra.data.repository.authentication.local

import android.content.SharedPreferences
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AuthenticationLocalDataSourceTest {

    @InjectMockKs
    private lateinit var authenticationLocalData: AuthenticationLocalDataSource

    @MockK
    private lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun saveData() {
        val token = "token"
        val tokenSecret = "tokenSecret"
        val editor = mockk<SharedPreferences.Editor>()
        every { sharedPreferences.edit() } returns editor

        every { editor.putString(AuthenticationLocalDataSource.TOKEN, token) } returns mockk()
        every {
            editor.putString(
                AuthenticationLocalDataSource.TOKEN_SECRET,
                tokenSecret
            )
        } returns mockk()
        every { editor.apply() } returns mockk()

        authenticationLocalData.saveData(token, tokenSecret)

        verify {
            editor.putString(AuthenticationLocalDataSource.TOKEN, token)
            editor.putString(AuthenticationLocalDataSource.TOKEN_SECRET, tokenSecret)
            editor.apply()
        }
    }

    @Test
    fun getToken() {
        val expected = "token"

        every {
            sharedPreferences.getString(AuthenticationLocalDataSource.TOKEN, null)
        } returns expected

        val result = authenticationLocalData.getToken()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getTokenSecret() {
        val expected = "tokenSecret"

        every {
            sharedPreferences.getString(AuthenticationLocalDataSource.TOKEN_SECRET, null)
        } returns expected

        val result = authenticationLocalData.getTokenSecret()
        Assert.assertEquals(expected, result)
    }
}