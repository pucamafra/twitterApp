package com.marlonmafra.data.repository.authentication.local

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Named

class AuthenticationLocalDataSource @Inject constructor(
    @Named("localUseSharedPreferencesData") private val sharedPreferences: SharedPreferences
) : IAuthenticationLocalDataSource {

    companion object {
        const val AUTH = "AUTH"
        const val TOKEN = "TOKEN"
        const val TOKEN_SECRET = "TOKEN_SECRET"
    }

    override fun saveData(oathToken: String, oathTokenSecret: String) {
        sharedPreferences.edit {
            putString(TOKEN, oathToken)
            putString(TOKEN_SECRET, oathTokenSecret)
        }
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    override fun getTokenSecret(): String? {
        return sharedPreferences.getString(TOKEN_SECRET, null)
    }

    override fun cleanData() {
        sharedPreferences.edit().clear().apply()
    }
}