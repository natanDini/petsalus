package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.network.KtorClient
import org.example.project.model.Especie
import org.example.project.model.Raca
import org.example.project.service.PetService
import org.example.project.model.PetCadastroRequest
import org.example.project.model.PetResponse

class PetViewModel : ViewModel() {

    private val petService = PetService(KtorClient.httpClient)

    private val _especies = MutableStateFlow<List<Especie>>(emptyList())
    val especies: StateFlow<List<Especie>> = _especies.asStateFlow()

    private val _racas = MutableStateFlow<List<Raca>>(emptyList())
    val racas: StateFlow<List<Raca>> = _racas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchEspecies() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = petService.getEspecies()
                _especies.value = result
            } catch (e: Exception) {
                _error.value = "Erro ao buscar espécies: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchRacas(especieId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _racas.value = emptyList()
            try {
                val result = petService.getRacasByEspecie(especieId)
                _racas.value = result
            } catch (e: Exception) {
                _error.value = "Erro ao buscar raças: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun registrarPet(
        nome: String,
        idadeStr: String,
        pesoStr: String,
        sexo: String,
        racaId: Long,
        especieId: Long,
        onResult: (Boolean, String) -> Unit
    ) {
        val idade = idadeStr.toIntOrNull()
        val peso = pesoStr.toDoubleOrNull()

        if (nome.isBlank() || idade == null || peso == null || sexo.isBlank() || racaId <= 0 || especieId <= 0) {
            onResult(false, "Por favor, preencha todos os campos corretamente")
            return
        }

        val petRequest = PetCadastroRequest(
            nome = nome,
            idade = idade,
            peso = peso,
            sexo = sexo,
            racaId = racaId,
            especieId = especieId
        )

        // TOKEN FIXO AQUI
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbGljZSIsInJvbGUiOiJUVVRPUiIsImV4cCI6MjExMDY0MDM5N30.37IwFmFyTbcPrn3nf75tKmTRvw9If2RG7pW0NOK8e80"

        // <-- Coloque seu token fixo aqui

        viewModelScope.launch {
            try {
                val response: HttpResponse = petService.registrarPet(token, petRequest)
                if (response.status.value in 200..299) {
                    onResult(true, "Pet registrado com sucesso!")
                } else {
                    onResult(false, "Erro ao registrar pet: ${response.status}")
                }
            } catch (e: Exception) {
                onResult(false, "Erro: ${e.message}")
            }
        }
    }

    // Adicione o serviço de listagem (reaproveitando o KtorClient que você já tem)
    private val petListService = PetService(KtorClient.httpClient)

    // Crie o StateFlow da listagem
    private val _pets = MutableStateFlow<List<PetResponse>>(emptyList())
    val pets: StateFlow<List<PetResponse>> = _pets.asStateFlow()

    // Método para buscar pets
    fun fetchPets() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = petListService.getPets()
                _pets.value = result
            } catch (e: Exception) {
                _error.value = "Erro ao buscar pets: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }

    }
}
