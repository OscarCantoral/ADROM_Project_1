package com.example.ch3mxr.base

data class SessionData(
    val isLoggedIn: Boolean = false,
    val authProvider: String = "",
    val token: String = "",
    val userId: String = "",
    val email: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val fotoUrl: String = "",
    val usuario: String = ""
)
