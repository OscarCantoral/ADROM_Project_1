package com.example.ch3mxr.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.ch3mxr.data.domain.SessionData
import com.google.gson.Gson
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class LocalData @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
) {
    private object PreferencesKeys {
        val SESSION_DATA = stringPreferencesKey("session_data")
    }

    suspend fun saveSessionData(sessiondata: SessionData){
        val jsonString = gson.toJson(sessiondata)
        dataStore.edit {preferences ->
            preferences[PreferencesKeys.SESSION_DATA] = jsonString
        }
    }

    val getSessionData: Flow<SessionData?> = dataStore.data.map { preferences ->
        val jsonString = preferences[PreferencesKeys.SESSION_DATA]
        if (jsonString.isNullOrEmpty()) {
            null
        } else {
            gson.fromJson(jsonString, SessionData::class.java)
        }
    }

    suspend fun clearSession() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}