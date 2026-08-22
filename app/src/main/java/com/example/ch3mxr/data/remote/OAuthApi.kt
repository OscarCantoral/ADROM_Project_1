package com.example.ch3mxr.data.remote

import com.example.ch3mxr.data.dto.OAuthTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OAuthApi {
    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun getToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("scope") scope: String = "api.read"
    ): OAuthTokenResponse
}