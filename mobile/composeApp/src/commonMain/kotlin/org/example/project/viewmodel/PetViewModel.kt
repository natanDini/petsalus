package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.network.KtorClient
import org.example.project.model.Especie
import org.example.project.model.Raca
import org.example.project.service.PetService

class PetViewModel : ViewModel() {
    // Usa o KtorClient.httpClient existente
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
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchRacas(especieId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _racas.value = emptyList() // Limpa raças anteriores
            try {
                val result = petService.getRacasByEspecie(especieId)
                _racas.value = result
            } catch (e: Exception) {
                _error.value = "Erro ao buscar raças: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}