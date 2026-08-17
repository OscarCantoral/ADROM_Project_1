package com.example.ch3mxr.ui.manager

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

class FacebookAuthManager {

    private lateinit var callbackManager: CallbackManager
    private lateinit var loginManager: LoginManager

    var onLoginSuccess: ((String, String) -> Unit)? = null
    var onLoginCancel: (() -> Unit)? = null
    var onLoginError: ((String) -> Unit)? = null

    fun init() {
        callbackManager = CallbackManager.Factory.create()
        loginManager = LoginManager.getInstance()
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                Log.i("FacebookAuth", "Login exitoso. Token: ${result.accessToken.token}")
                Log.i("FacebookAuth", "User ID: ${result.accessToken.userId}")
                onLoginSuccess?.invoke(result.accessToken.token, result.accessToken.userId)
            }

            override fun onCancel() {
                Log.w("FacebookAuth", "Login cancelado por el usuario")
                onLoginCancel?.invoke()
            }

            override fun onError(error: FacebookException) {
                Log.e("FacebookAuth", "Error en login: ${error.message}", error)
                onLoginError?.invoke(error.message ?: "Error desconocido")
            }
        })
    }

    fun signInWithFacebook(activity: Activity) {
        loginManager.logInWithReadPermissions(activity, listOf("public_profile", "email"))
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
