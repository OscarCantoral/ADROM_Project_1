package com.example.ch3mxr.data.remote

import com.example.ch3mxr.data.domain.dto.TokenResponse
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private val clientBasicAuth: String
) {
    suspend fun login(
        username: String,
        password: String
    ): TokenResponse? {
        return try {
            authApiService.getUser(
                basicAuth = clientBasicAuth,
                username = username,
                password = password
            )
        }catch (ex: Exception){
            ex.printStackTrace()
            null
        }
    }
}