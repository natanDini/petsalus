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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.shadow



import java.io.File
import java.io.InputStream
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import kotlinx.coroutines.flow.collectLatest
import org.example.project.utils.ImageUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavController) {

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var base64Image by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val userService = UserService(KtorClient.httpClient)
    val tokenStorage: TokenStorage = remember { TokenStorage(context) }
    var token by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        tokenStorage.tokenFlow.collectLatest { token = it }
    }

    LaunchedEffect(token) {
        token?.let {
            val perfil = userService.getPerfilCompleto(it)
            perfil?.let { p ->
                nome = p.nome
                cpf = p.cpf
                email = p.email
                username = p.username
                telefone = p.telefone
                endereco = p.endereco.toString()
                base64Image = p.fotoPerfil
            }
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                token?.let { tkn ->
                    uploadImage(context, it, userService, tkn) { responseBase64 ->
                        base64Image = responseBase64
                    }
                }
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil", style = MaterialTheme.typography.titleSmall) },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .clickable { imagePickerLauncher.launch("image/*") }
                    .border(2.dp, Color.White, CircleShape)
                    .shadow(10.dp, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (base64Image != null) {
                    val imageBitmap = ImageUtils.decodeBase64ToImageBitmap(base64Image!!)
                    imageBitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "Foto Perfil",
                            modifier = Modifier
                                .size(160.dp)
                                .clip(CircleShape)
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Adicionar Foto",
                        tint = Color.Gray,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PerfilItem("Nome", nome)
                    PerfilItem("Email", email)
                    PerfilItem("Username", username)
                    PerfilItem("CPF", cpf)
                    PerfilItem("Telefone", telefone)
                    PerfilItem("Endereço", endereco)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("menu")},
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(8.dp, RoundedCornerShape(20.dp))
            ) {
                Text(
                    "Voltar",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun PerfilItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
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
