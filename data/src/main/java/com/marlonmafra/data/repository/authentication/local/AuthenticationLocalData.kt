package com.marlonmafra.data.repository.authentication.local

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject

const val AUTH = "AUTH"
const val TOKEN = "TOKEN"
const val TOKEN_SECRET = "TOKEN_SECRET"

class AuthenticationLocalData @Inject constructor(
    context: Context
) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        AUTH,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveData(oathToken: String, oathTokenSecret: String) {
        sharedPreferences.edit {
            putString(TOKEN, oathToken)
            putString(TOKEN_SECRET, oathTokenSecret)
        }
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    fun getTokenSecret(): String? {
        return sharedPreferences.getString(TOKEN_SECRET, null)
    }
}