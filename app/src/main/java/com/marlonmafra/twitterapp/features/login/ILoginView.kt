package com.marlonmafra.twitterapp.features.login

interface ILoginView {

    fun openCallBack(oauthToken:String)

    fun userLogged()

}