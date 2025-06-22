// PetScreen.kt - Versão com debug
package org.example.project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.example.project.model.Especie
import org.example.project.viewmodel.PetViewModel

@Composable
fun PetScreen(
    navController: NavHostController,
    viewModel: PetViewModel = viewModel()
) {
    val especies by viewModel.especies.collectAsState()
    val racas by viewModel.racas.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Estado para controlar dropdown aberto/fechado
    var expanded by remember { mutableStateOf(false) }
    var selectedEspecie by remember { mutableStateOf<Especie?>(null) }

    // Debug - Log dos estados
    LaunchedEffect(especies) {
        println("PetScreen: Especies atualizadas - Total: ${especies.size}")
        especies.forEach {
            println("PetScreen: Especie - ID: ${it.id}, Nome: ${it.nome}")
        }
    }

    // Quando seleciona uma espécie, busca as raças dela
    LaunchedEffect(selectedEspecie) {
        selectedEspecie?.let {
            println("PetScreen: Espécie selecionada - ${it.nome}")
            viewModel.fetchRacas(it.id.toInt())
        }
    }

    // Busca espécies ao carregar a tela
    LaunchedEffect(Unit) {
        println("PetScreen: Iniciando busca de espécies")
        viewModel.fetchEspecies()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Selecione o Pet",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Mensagem de erro
        error?.let {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Debug info
        if (isLoading) {
            Text("Carregando...", color = MaterialTheme.colorScheme.primary)
        }
        Text("Total de espécies: ${especies.size}")

        Spacer(modifier = Modifier.height(8.dp))

        Text("Selecione a espécie:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Box {
            OutlinedTextField(
                value = selectedEspecie?.nome ?: "Clique para selecionar",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        println("PetScreen: Dropdown clicado")
                        expanded = !expanded
                    },
                readOnly = true,
                enabled = false, // Mudança aqui - desabilita o campo mas mantém clickable
                label = { Text("Espécie") },
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown",
                        modifier = Modifier.clickable {
                            expanded = !expanded
                        }
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    println("PetScreen: Dropdown fechado")
                    expanded = false
                },
                modifier = Modifier.fillMaxWidth(0.9f) // Ajuste de largura
            ) {
                println("PetScreen: Renderizando dropdown com ${especies.size} itens")

                if (especies.isEmpty() && !isLoading) {
                    DropdownMenuItem(
                        text = { Text("Nenhuma espécie disponível") },
                        onClick = { expanded = false },
                        enabled = false
                    )
                } else if (especies.isEmpty() && isLoading) {
                    DropdownMenuItem(
                        text = {
                            Row {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Carregando...")
                            }
                        },
                        onClick = {},
                        enabled = false
                    )
                } else {
                    especies.forEach { especie ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = especie.nome,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            },
                            onClick = {
                                println("PetScreen: Espécie selecionada - ${especie.nome}")
                                selectedEspecie = especie
                                expanded = false
                            }
                        )
                        Divider()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Seção de raças
        selectedEspecie?.let { especie ->
            Text(
                "Raças de ${especie.nome}:",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            when {
                isLoading && racas.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                racas.isEmpty() -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Text(
                            text = "Nenhuma raça encontrada para esta espécie",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
                else -> {
                    Text("Total de raças: ${racas.size}")
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f) // Permite scroll se muitas raças
                    ) {
                        items(racas) { raca ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            println("PetScreen: Raça clicada - ${raca.nome}")
                                        }
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = raca.nome,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}