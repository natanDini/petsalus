package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavController) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFFF6F61), Color(0xFFFFA726))
    )

    var nome by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var fotoPerfil by remember { mutableStateOf(false) }
    var fotoPet by remember { mutableStateOf(false) }

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

                // Foto de perfil
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            if (fotoPerfil) Color.White.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.15f),
                            shape = CircleShape
                        )
                        .clickable { fotoPerfil = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Adicionar Foto",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                }

                // Campo Nome
                TextField(
                    value = nome,
                    onValueChange = { nome = it },
                    placeholder = { Text("Nome") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                )

                // Campo Idade
                TextField(
                    value = idade,
                    onValueChange = { idade = it },
                    placeholder = { Text("Idade") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),

                )


                // Foto do pet
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            if (fotoPet) Color.White.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.15f),
                            shape = CircleShape
                        )
                        .clickable { fotoPet = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Adicionar Foto do Pet",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                }

                // Botão Salvar
                Button(
                    onClick = {
                        // ação para salvar perfil
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Salvar Perfil", color = Color.Black)
                }
            }
        }
    }
}
