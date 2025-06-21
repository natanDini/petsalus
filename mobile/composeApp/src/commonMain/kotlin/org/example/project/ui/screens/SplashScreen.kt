package org.example.project.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import kotlinproject.composeapp.generated.resources.Res
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import kotlinproject.composeapp.generated.resources.splash
import androidx.compose.runtime.LaunchedEffect

@Composable
fun SplashScreen(navController: NavController) {
    val infiniteTransition = rememberInfiniteTransition()

    // Animação de alpha para piscar entre 0.3 e 1.0

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        delay(3500)  // splash dura 3.5s
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.splash),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f), // imagem translúcida (60% opaca)
            contentScale = ContentScale.Crop
        )


    }
}