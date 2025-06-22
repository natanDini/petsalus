package org.example.project.ui.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.network.KtorClient
import org.example.project.service.UserService
import org.example.project.content.TokenStorage


import java.io.File
import java.io.InputStream
import androidx.compose.ui.graphics.Brush
import kotlinx.coroutines.flow.collectLatest
import org.example.project.utils.ImageUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavController) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFFF6F61), Color(0xFFFFA726))
    )

    var nome by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var base64Image by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val userService = UserService(KtorClient.httpClient)

    // TokenStorage para ler o token salvo
    val tokenStorage: TokenStorage = remember { TokenStorage(context) }

    var token by remember { mutableStateOf<String?>(null) }


    // Coletar token armazenado no DataStore
    LaunchedEffect(Unit) {
        tokenStorage.tokenFlow.collectLatest {
            token = it
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                if (token != null) {
                    uploadImage(context, it, userService, token!!) { responseBase64 ->
                        base64Image = responseBase64
                    }
                } else {
                    // Token não disponível - tratar aqui caso queira
                }
            }
        }
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Perfil do Usuário",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.White.copy(alpha = 0.15f), shape = CircleShape)
                        .clickable { imagePickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (base64Image != null) {
                        val imageBitmap = ImageUtils.decodeBase64ToImageBitmap(base64Image!!)
                        imageBitmap?.let {
                            Image(
                                bitmap = it,
                                contentDescription = "Foto Perfil",
                                modifier = Modifier
                                    .size(120.dp)
                                    .background(Color.White.copy(alpha = 0.15f), shape = CircleShape)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Adicionar Foto",
                            tint = Color.White,
                            modifier = Modifier.size(64.dp)
                        )
                    }

                }

                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    placeholder = { Text("Nome") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                    value = idade,
                    onValueChange = { idade = it },
                    placeholder = { Text("Idade") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                )

                Button(
                    onClick = { /* salvar perfil backend */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Salvar Perfil", color = Color.Black)
                }
            }
        }
    }
}

// Função atualizada para receber token e passá-lo para o UserService
fun uploadImage(
    context: Context,
    uri: Uri,
    userService: UserService,
    token: String,
    onResponse: (String?) -> Unit
) {
    val contentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)

    inputStream?.use { input -> tempFile.outputStream().use { input.copyTo(it) } }

    CoroutineScope(Dispatchers.IO).launch {
        val responseBase64 = userService.uploadPhoto(tempFile, token)
        if (!responseBase64.isNullOrBlank()) {
            onResponse(responseBase64)
        } else {
            // Evita crash
            println("Erro: base64 retornado nulo ou vazio")
            onResponse(null)
        }
    }
}
