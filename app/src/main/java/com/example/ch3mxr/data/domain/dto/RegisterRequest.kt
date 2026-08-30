package com.example.ch3mxr.data.domain.dto

data class RegisterRequest(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String? = null
)