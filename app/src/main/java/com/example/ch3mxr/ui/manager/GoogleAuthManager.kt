package com.example.ch3mxr.ui.manager

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.example.ch3mxr.Config
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import java.security.SecureRandom

class GoogleAuthManager(private val context: Context) {

    private val credentialManager = CredentialManager.create(context)

    suspend fun signInWithGoogle(): GoogleIdTokenCredential? {
        Log.i("REQUEST TO LOG IN WITH GOOGLE","REQUEST TO LOG IN WITH GOOGLE")
        val signInWithGoogleOption: GetSignInWithGoogleOption =
            GetSignInWithGoogleOption.Builder(serverClientId = Config.WEB_CLIENT_ID)
                .setNonce(generateSecureRandomNonce())
                .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            Log.i("RESULT GOOGLE AUTH", result.toString())
            handleSignInResponse(result)
        } catch (e: GetCredentialException) {
            Log.e("GoogleAuth", "Sign in failed", e)
            null
        }
    }

    private fun handleSignInResponse(result: GetCredentialResponse): GoogleIdTokenCredential? {
        val credential = result.credential

        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            return try {
                GoogleIdTokenCredential.createFrom(credential.data)
            } catch (e: GoogleIdTokenParsingException) {
                Log.e("GoogleAuth", "Failed to parse Google ID token", e)
                null
            }
        }
        return null
    }

    private fun generateSecureRandomNonce(): String {
        val random = SecureRandom()
        val bytes = ByteArray(32)
        random.nextBytes(bytes)
        return Base64.encodeToString(bytes, Base64.NO_PADDING or Base64.NO_WRAP or Base64.URL_SAFE)
    }

}