package com.example.ch3mxr.data.remote

import com.example.ch3mxr.data.domain.dto.LoginRequest
import com.example.ch3mxr.data.domain.dto.LoginResponse
import com.example.ch3mxr.data.domain.dto.RegisterRequest
import com.example.ch3mxr.data.domain.dto.RegisterResponse
import com.google.gson.JsonParser
import retrofit2.HttpException
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val authApiService: AuthApiService,
    @Suppress("unused") private val clientBasicAuth: String
) {
    suspend fun login(
        username: String,
        password: String
    ): Pair<LoginResponse?, String?> {
        return try {
            authApiService.login(LoginRequest(username, password)) to null
        } catch (ex: HttpException) {
            null to extractErrorMessage(ex)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null to "No se pudo conectar al servidor"
        }
    }

    suspend fun register(request: RegisterRequest): Pair<RegisterResponse?, String?> {
        return try {
            authApiService.register(request) to null
        } catch (ex: HttpException) {
            null to extractErrorMessage(ex)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null to "No se pudo conectar al servidor"
        }
    }

    private fun extractErrorMessage(ex: HttpException): String {
        val fallback = "Error del servidor (${ex.code()})"
        return try {
            val body = ex.response()?.errorBody()?.string()
            if (body.isNullOrEmpty()) return fallback
            val root = JsonParser.parseString(body)
            if (root.isJsonObject) {
                val error = root.asJsonObject.get("error")?.asString
                if (!error.isNullOrEmpty()) error else fallback
            } else {
                fallback
            }
        } catch (t: Exception) {
            fallback
        }
    }
}