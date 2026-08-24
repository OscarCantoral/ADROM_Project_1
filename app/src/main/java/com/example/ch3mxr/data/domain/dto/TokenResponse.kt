package com.example.ch3mxr.data.domain.dto

data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val scope: String
)
