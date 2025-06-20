package org.example.project.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Definindo as cores usando sua paleta

val CoralDark = Color(0xFF8C0335)
val BeigeLight = Color(0xFFFAD8B3)
val BlueAccent = Color(0xFF17A0BF)
val OrangeStrong = Color(0xFFF29422)
val RedStrong = Color(0xFFF22222)
val OrangeLight = Color(0xFFFAB029)

private val LightColors = lightColorScheme(
    primary = OrangeStrong,
    onPrimary = Color.White,
    secondary = CoralDark,
    onSecondary = Color.White,
    tertiary = BlueAccent,
    background = OrangeLight,
    onBackground = CoralDark,
    surface = Color.White,
    onSurface = BeigeLight,
    error = RedStrong,
    onError = Color.White

)

private val DarkColors = darkColorScheme(
    primary = OrangeStrong,
    onPrimary = Color.Black,
    secondary = CoralDark,
    onSecondary = Color.White,
    tertiary = BlueAccent,
    background = CoralDark,
    onBackground = BeigeLight,
    surface = CoralDark,
    onSurface = BeigeLight,
    error = RedStrong,
    onError = Color.Black
)

@Composable
fun PetSalusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(), // você pode personalizar depois
        content = content
    )
}
