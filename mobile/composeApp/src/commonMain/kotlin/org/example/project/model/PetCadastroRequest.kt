package org.example.project.model

import kotlinx.serialization.Serializable

@Serializable
data class PetCadastroRequest(
    val peso: Double,
    val nome: String,
    val idade: Int,
    val sexo: String,
    val racaId: Long,
    val especieId: Long
)
