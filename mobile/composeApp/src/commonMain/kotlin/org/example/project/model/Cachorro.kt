package org.example.project.model

@kotlinx.serialization.Serializable
data class Especie(
    val id: Long,
    val nome: String
)

@kotlinx.serialization.Serializable
data class Raca(
    val id: Long,
    val nome: String
)
