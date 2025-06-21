package org.example.project



import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.ui.screens.LoginScreen
import org.example.project.ui.screens.MenuScreen
import org.example.project.ui.screens.PerfilScreen
import org.example.project.ui.screens.SplashScreen
import org.example.project.ui.screens.CadastroScreen

@Composable
fun App() {
    val navController = rememberNavController()
    NavigationGraph(navController = navController)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash"  // Agora a splash é a primeira tela
    ) {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }

        composable("menu"){
            MenuScreen(navController = navController)
        }

        composable("perfil"){
            PerfilScreen(navController = navController)
        }

        composable("cadastro"){
            CadastroScreen(navController = navController)
        }

        // Outras telas, exemplo:
        // composable("home") { HomeScreen(navController) }
    }
}

