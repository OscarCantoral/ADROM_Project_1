package com.example.ch3mxr.ui.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ch3mxr.base.SessionData
import com.example.ch3mxr.data.datastore.LocalData
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SessionViewModel @Inject constructor(val localData: LocalData): ViewModel() {

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