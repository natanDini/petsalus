package org.example.project.network

import kotlinx.serialization.Serializable

@Serializable
data class CadastroRequest(
    val cpf: String,
    val nome: String,
    val email: String,
    val senha: String,
    val username: String,
    val telefone: String,
    val userRole: String,
    val endereco: Endereco
)

@Serializable
data class Endereco(
    val cep: String,
    val bairro: String,
    val cidade: String,
    val numero: String,
    val estado: String,
    val endereco: String,
    val complemento: String
)
