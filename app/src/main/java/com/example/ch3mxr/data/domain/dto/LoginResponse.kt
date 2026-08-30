package com.example.ch3mxr.data.domain.dto

data class LoginResponse(
    val id: String,
    val username: String,
    val email: String,
    val displayName: String? = null,
    val firstName: String? = null,
    val lastName: String? = null
)