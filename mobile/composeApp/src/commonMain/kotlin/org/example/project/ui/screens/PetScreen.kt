package org.example.project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.example.project.model.Especie
import org.example.project.model.Raca
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

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var expanded by remember { mutableStateOf(false) }
    var selectedEspecie by remember { mutableStateOf<Especie?>(null) }

    var nome by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        println("PetScreen: Iniciando busca de espécies")
        viewModel.fetchEspecies()
    }

    LaunchedEffect(selectedEspecie) {
        selectedEspecie?.let {
            println("PetScreen: Espécie selecionada - ${it.nome}")
            viewModel.fetchRacas(it.id.toInt())
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Cadastrar Pet",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

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

            if (isLoading) {
                Text("Carregando...", color = MaterialTheme.colorScheme.primary)
            }

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            OutlinedTextField(
                value = idade,
                onValueChange = { if (it.all { char -> char.isDigit() }) idade = it },
                label = { Text("Idade") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = peso,
                onValueChange = { if (it.matches(Regex("^\\d*\\.?\\d*\$"))) peso = it },
                label = { Text("Peso (kg)") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
            )

            // Dropdown de Sexo
            var expandedSexo by remember { mutableStateOf(false) }
            val opcoesSexo = listOf("MASCULINO", "FEMININO")

            Text("Selecione o sexo:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Box {
                OutlinedTextField(
                    value = if (sexo.isNotBlank()) sexo else "Clique para selecionar",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth().clickable { expandedSexo = !expandedSexo },
                    readOnly = true,
                    enabled = false,
                    label = { Text("Sexo") },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Sexo",
                            modifier = Modifier.clickable { expandedSexo = !expandedSexo })
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                DropdownMenu(
                    expanded = expandedSexo,
                    onDismissRequest = { expandedSexo = false }
                ) {
                    opcoesSexo.forEach { opcao ->
                        DropdownMenuItem(
                            text = { Text(opcao) },
                            onClick = {
                                sexo = opcao
                                expandedSexo = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown de espécie
            Text("Selecione a espécie:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Box {
                OutlinedTextField(
                    value = selectedEspecie?.nome ?: "Clique para selecionar",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded },
                    readOnly = true,
                    enabled = false,
                    label = { Text("Espécie") },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown",
                            modifier = Modifier.clickable { expanded = !expanded })
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    especies.forEach { especie ->
                        DropdownMenuItem(
                            text = { Text(especie.nome) },
                            onClick = {
                                selectedEspecie = especie
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            var expandedRaca by remember { mutableStateOf(false) }
            var selectedRaca by remember { mutableStateOf<Raca?>(null) }

            Text("Selecione a raça:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Box {
                OutlinedTextField(
                    value = selectedRaca?.nome ?: "Clique para selecionar",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth().clickable { expandedRaca = !expandedRaca },
                    readOnly = true,
                    enabled = false,
                    label = { Text("Raça") },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Raça",
                            modifier = Modifier.clickable { expandedRaca = !expandedRaca })
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                DropdownMenu(
                    expanded = expandedRaca,
                    onDismissRequest = { expandedRaca = false }
                ) {
                    racas.forEach { raca ->
                        DropdownMenuItem(
                            text = { Text(raca.nome) },
                            onClick = {
                                selectedRaca = raca
                                expandedRaca = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val racaId = selectedRaca?.id ?: 0L
                    val especieId = selectedEspecie?.id ?: 0L
                    viewModel.registrarPet(
                        nome, idade, peso, sexo, racaId, especieId
                    ) { success, message ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Pet")
            }
        }
    }
}
