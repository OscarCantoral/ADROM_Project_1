package com.example.ch3mxr.ui.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ch3mxr.data.domain.SessionData
import com.example.ch3mxr.data.datastore.LocalData
import com.example.ch3mxr.ui.manager.FacebookAuthManager
import com.example.ch3mxr.ui.manager.GoogleAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SessionViewModel @Inject constructor(val localData: LocalData, val facebookAuthManager: FacebookAuthManager, val googleAuthManager: GoogleAuthManager): ViewModel() {

    fun saveSession(sessionData: SessionData){
        viewModelScope.launch {
            localData.saveSessionData(sessionData)
        }
    }

    fun clearSession(){
        viewModelScope.launch {
            localData.clearSession()
        }
    }
}