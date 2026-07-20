package com.example.ch3mxr.base

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")

class SessionManager(private val context: Context) {

    private object Keys {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val AUTH_PROVIDER = stringPreferencesKey("auth_provider")
        val TOKEN = stringPreferencesKey("token")
        val USER_ID = stringPreferencesKey("user_id")
        val EMAIL = stringPreferencesKey("email")
        val NOMBRE = stringPreferencesKey("nombre")
        val APELLIDO = stringPreferencesKey("apellido")
        val FOTO_URL = stringPreferencesKey("foto_url")
        val USUARIO = stringPreferencesKey("usuario")
    }

    val session: Flow<SessionData> = context.dataStore.data.map { prefs ->
        SessionData(
            isLoggedIn = prefs[Keys.IS_LOGGED_IN] ?: false,
            authProvider = prefs[Keys.AUTH_PROVIDER] ?: "",
            token = prefs[Keys.TOKEN] ?: "",
            userId = prefs[Keys.USER_ID] ?: "",
            email = prefs[Keys.EMAIL] ?: "",
            nombre = prefs[Keys.NOMBRE] ?: "",
            apellido = prefs[Keys.APELLIDO] ?: "",
            fotoUrl = prefs[Keys.FOTO_URL] ?: "",
            usuario = prefs[Keys.USUARIO] ?: ""
        )
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.IS_LOGGED_IN] ?: false
    }

    suspend fun saveSession(sessionData: SessionData) {
        context.dataStore.edit { prefs ->
            prefs[Keys.IS_LOGGED_IN] = true
            prefs[Keys.AUTH_PROVIDER] = sessionData.authProvider
            prefs[Keys.TOKEN] = sessionData.token
            prefs[Keys.USER_ID] = sessionData.userId
            prefs[Keys.EMAIL] = sessionData.email
            prefs[Keys.NOMBRE] = sessionData.nombre
            prefs[Keys.APELLIDO] = sessionData.apellido
            prefs[Keys.FOTO_URL] = sessionData.fotoUrl
            prefs[Keys.USUARIO] = sessionData.usuario
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
