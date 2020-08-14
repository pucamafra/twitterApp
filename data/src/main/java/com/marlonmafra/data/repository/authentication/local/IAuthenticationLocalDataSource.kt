package com.marlonmafra.data.repository.authentication.local

interface IAuthenticationLocalDataSource {

    fun saveData(oathToken: String, oathTokenSecret: String)

    fun getToken(): String?

    fun getTokenSecret(): String?

    fun cleanData()
}