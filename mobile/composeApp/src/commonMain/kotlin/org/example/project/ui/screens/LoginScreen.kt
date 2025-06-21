package org.example.project.ui.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.image
import kotlinx.coroutines.launch
import org.example.network.KtorClient
import org.example.project.network.LoginRequest
import org.example.project.ui.BeigeLight
import org.example.project.ui.BlueAccent
import org.example.project.ui.CoralDark
import org.example.project.ui.OrangeLight
import org.example.project.ui.OrangeStrong
import org.example.project.ui.RedStrong

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    val colors = MaterialTheme.colorScheme

    // Animação do gradiente
    val transition = rememberInfiniteTransition(label = "gradient")
    val color1 by transition.animateColor(
        initialValue = OrangeLight, // DarkCoral
        targetValue = BeigeLight,  // LightYellow
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "color1"
    )

    val color2 by transition.animateColor(
        initialValue = CoralDark,  // LightYellow
        targetValue = BlueAccent,  // DarkCoral
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "color2"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(color1, color2)
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = colors.onBackground
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(Res.drawable.image),
                    contentDescription = null,
                    modifier = Modifier
                        .height(120.dp)
                        .padding(bottom = 16.dp)
                )

                Text(
                    text = "Bem-vindo!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.secondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Faça login para continuar",
                    fontSize = 16.sp,
                    color = colors.onBackground
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username", color = colors.secondary) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.secondary,
                        focusedLabelColor = colors.secondary,
                        cursorColor = colors.secondary,
                        unfocusedBorderColor = colors.onSurface,
                        unfocusedLabelColor = colors.onSurface
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    label = { Text("Senha", color = colors.secondary) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.secondary,
                        focusedLabelColor = colors.secondary,
                        cursorColor = colors.secondary,
                        unfocusedBorderColor = colors.onSurface,
                        unfocusedLabelColor = colors.onSurface
                    )
                )

                val scope = rememberCoroutineScope()

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        scope.launch {
                            val loginResponse = KtorClient.apiService.login(LoginRequest(username, senha))
                            if (loginResponse != null) {
                                println("Token recebido: ${loginResponse.token}")
                                navController.navigate("menu")
                            } else {
                                println("Falha no login.")
                                // Aqui pode mostrar o Snackbar
                            }
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CoralDark,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Entrar", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(24.dp))

                ClickableText(
                    text = AnnotatedString("Não tem login? Cadastre-se!"),
                    onClick = { navController.navigate("cadastro") },
                    style = typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
}
