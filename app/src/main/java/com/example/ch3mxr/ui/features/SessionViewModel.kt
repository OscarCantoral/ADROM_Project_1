package com.example.ch3mxr.ui.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ch3mxr.data.domain.dto.RegisterRequest
import com.example.ch3mxr.data.domain.dto.SessionData
import com.example.ch3mxr.data.datastore.LocalData
import com.example.ch3mxr.data.remote.RemoteRepository
import com.example.ch3mxr.ui.manager.FacebookAuthManager
import com.example.ch3mxr.ui.manager.GoogleAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SessionViewModel @Inject constructor(
    val localData: LocalData,
    val remoteRepository: RemoteRepository,
    val facebookAuthManager: FacebookAuthManager,
    val googleAuthManager: GoogleAuthManager
) : ViewModel() {

    fun saveSession(sessionData: SessionData) {
        viewModelScope.launch {
            localData.saveSessionData(sessionData)
        }
    }

    fun makeLogin(
        username: String,
        password: String,
        onResult: (session: SessionData?, error: String?) -> Unit
    ) {
        viewModelScope.launch {
            val (response, error) = remoteRepository.login(username, password)
            if (response != null) {
                val session = SessionData(
                    isLoggedIn = true,
                    authProvider = "manual",
                    userId = response.id,
                    usuario = response.username,
                    email = response.email,
                    nombre = response.firstName ?: "",
                    apellido = response.lastName ?: ""
                )
                onResult(session, null)
            } else {
                onResult(null, error)
            }
        }
    }

    fun registerUser(
        username: String,
        password: String,
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        onResult: (success: Boolean, error: String?) -> Unit
    ) {
        viewModelScope.launch {
            val (response, error) = remoteRepository.register(
                RegisterRequest(
                    username = username,
                    password = password,
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    phone = phone
                )
            )
            onResult(response != null, error)
        }
    }

    fun clearSession() {
        viewModelScope.launch {
            localData.clearSession()
        }
    }
}