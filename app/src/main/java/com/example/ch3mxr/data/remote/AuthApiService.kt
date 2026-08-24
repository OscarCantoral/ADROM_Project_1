package com.example.ch3mxr.data.remote

import com.example.ch3mxr.data.domain.dto.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun getUser(
        @Header("Authorization") basicAuth: String,
        @Field("grant_type") grantType: String = "password",
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("scope") scope: String = "api.read"
    ): TokenResponse
}