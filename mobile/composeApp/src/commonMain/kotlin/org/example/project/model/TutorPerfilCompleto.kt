package org.example.project.model

import kotlinx.serialization.Serializable

@Serializable
data class PerfilCompleto(
    val cpf: String,
    val nome: String,
    val email: String,
    val username: String,
    val telefone: String,
    val endereco: Endereco,
    val fotoPerfil: String? = null // base64 com prefixo "data:image/jpeg;base64,..."
)
