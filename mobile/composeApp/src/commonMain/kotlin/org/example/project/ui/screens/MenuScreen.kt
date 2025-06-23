package org.example.project.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.image
import kotlinproject.composeapp.generated.resources.PETSALUS
import org.example.project.network.LoginRequest
import org.example.project.ui.BeigeLight
import org.example.project.ui.BlueAccent
import org.example.project.ui.CoralDark
import org.example.project.ui.OrangeLight
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,     // OrangeStrong (#F29422)
            BeigeLight    // CoralDark (#8C0335)
        )
    )

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(CoralDark, BlueAccent) // Exemplo: DarkCoral -> LightYellow
    )


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(250.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Menu",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(16.dp)
                )

                DrawerMenuItem(icon = Icons.Default.Home, label = "Home") { /* ação home */ }
                DrawerMenuItem(icon = Icons.Default.Favorite, label = "Favoritos") { /* ação favoritos */ }
                DrawerMenuItem(icon = Icons.Default.Person, label = "Perfil") {
                    navController.navigate("perfil")
                }
                DrawerMenuItem(icon = Icons.Default.Notifications, label = "Notificações") { /* ação notificações */ }
                DrawerMenuItem(icon = Icons.Default.Settings, label = "Configurações") { /* ação configurações */ }
                Spacer(modifier = Modifier.weight(1f))
                DrawerMenuItem(icon = Icons.Default.ExitToApp, label = "Sair") { /* ação sair */ }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    ) {
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
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (drawerState.isClosed) {
                                scope.launch { drawerState.open() }
                            } else {
                                scope.launch { drawerState.close() }
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Image(
                        painter = painterResource(Res.drawable.image),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Fit
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                            .clickable {
                            navController.navigate("cadastropet")
                        },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                                            append("Não cadastrou seu pet ainda?")
                                        }
                                    },
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )



                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(brush = gradientBrush)) {
                                            append("Cadastre o seu melhor amigo agora!")
                                        }
                                    },
                                    style = MaterialTheme.typography.bodyLarge
                                )

                            }
                        }
                    }




                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Buscar...", color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),

                            focusedContainerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f),

                            cursorColor = MaterialTheme.colorScheme.primary,

                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),

                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),

                            focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                        )
                    )


                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            PromoCard(CardData("Promoção 1", "Descrição da promoção 1"))
                        }
                        item {
                            PromoCard(CardData("Promoção 2", "Descrição da promoção 2"))
                        }
                        item {
                            PromoCard(CardData("Promoção 3", "Descrição da promoção 3"))
                        }
                    }

                    Text(
                        text = "Promoções exclusivas",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                              navController.navigate("listarpet")
                            }
                            .padding(16.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        RoundIcon(Icons.Default.Home, "Home")
                        RoundIcon(Icons.Default.Favorite, "Favoritos")
                        RoundIcon(Icons.Default.Person, "Perfil")
                        RoundIcon(Icons.Default.Notifications, "Notificações")
                        RoundIcon(Icons.Default.Settings, "Configurações")
                    }


                }
            }
        }
    }
}

@Composable
fun DrawerMenuItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    NavigationDrawerItem(
        icon = { Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.onBackground) },
        label = { Text(label, color = MaterialTheme.colorScheme.onBackground) },
        selected = false,
        onClick = onClick,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

@Composable
fun RoundIcon(icon: ImageVector, description: String) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(MaterialTheme.colorScheme.onPrimary, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun PromoCard(card: CardData) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(card.title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
            Text(
                card.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
        }
    }
}

data class CardData(val title: String, val description: String)
