package org.example.project.network

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest(
    val username: String,
    val senha: String
)


@Serializable
data class LoginResponse(
    val token: String,
    val userRole: String
)
