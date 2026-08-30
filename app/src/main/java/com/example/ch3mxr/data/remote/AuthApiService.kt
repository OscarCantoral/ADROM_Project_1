package com.example.ch3mxr.data.remote

import com.example.ch3mxr.data.domain.dto.LoginRequest
import com.example.ch3mxr.data.domain.dto.LoginResponse
import com.example.ch3mxr.data.domain.dto.RegisterRequest
import com.example.ch3mxr.data.domain.dto.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse
}