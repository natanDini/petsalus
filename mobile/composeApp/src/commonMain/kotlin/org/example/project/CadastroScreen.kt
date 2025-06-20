import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(navController: NavController) {
    // --- PARTICULAS DINÂMICAS NO FUNDO ---
    val particles = remember { List(40) { Particle() } }
    val infiniteTransition = rememberInfiniteTransition()

    val animProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing)
        )
    )

    // Estado dos campos
    var cpf by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }

    // Animação de entrada do formulário
    var formVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        formVisible = true
    }

    // Gradiente de fundo elegante
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF141E30), Color(0xFF243B55))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        // Partículas animadas no fundo
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            particles.forEachIndexed { index, particle ->
                val x = (particle.startX + animProgress * particle.speed * width) % width
                val y = (particle.startY + animProgress * particle.speed * height) % height

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            particle.color.copy(alpha = 0.6f),
                            particle.color.copy(alpha = 0f)
                        )
                    ),
                    radius = particle.radius,
                    center = Offset(x, y)
                )
            }
        }

        // Formulário em card com sombra e bordas suaves
        AnimatedVisibility(
            visible = formVisible,
            enter = fadeIn(animationSpec = tween(700)) + slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(700, easing = FastOutSlowInEasing)
            )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .align(Alignment.Center)
                    .shadow(
                        elevation = 24.dp,
                        shape = RoundedCornerShape(28.dp),
                        clip = false
                    ),
                shape = RoundedCornerShape(28.dp),
                color = Color.White.copy(alpha = 0.95f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Logo estilizada
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF3366FF), Color(0xFF00CCFF))
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "A",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        )
                    }

                    Text(
                        text = "Cadastro Inovador",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF243B55)
                        )
                    )

                    @Composable

                    fun StyledTextField(
                        value: String,
                        onValueChange: (String) -> Unit,
                        label: String,
                        isPassword: Boolean = false
                    ) {
                        OutlinedTextField(
                            value = value,
                            onValueChange = onValueChange,
                            label = { Text(label) },
                            singleLine = true,
                            shape = RoundedCornerShape(18.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),

                            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
                        )
                    }

                    StyledTextField(cpf, { cpf = it }, "CPF")
                    StyledTextField(nome, { nome = it }, "Nome Completo")
                    StyledTextField(email, { email = it }, "E-mail")
                    StyledTextField(senha, { senha = it }, "Senha", isPassword = true)
                    StyledTextField(telefone, { telefone = it }, "Telefone")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Endereço",
                        style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF243B55)),
                        modifier = Modifier.align(Alignment.Start)
                    )

                    StyledTextField(cep, { cep = it }, "CEP")
                    StyledTextField(bairro, { bairro = it }, "Bairro")
                    StyledTextField(cidade, { cidade = it }, "Cidade")
                    StyledTextField(estado, { estado = it }, "Estado")
                    StyledTextField(endereco, { endereco = it }, "Endereço")
                    StyledTextField(numero, { numero = it }, "Número")
                    StyledTextField(complemento, { complemento = it }, "Complemento")

                    Spacer(modifier = Modifier.height(20.dp))

                    var pressed by remember { mutableStateOf(false) }
                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF3366FF), Color(0xFF00CCFF))
                            ).toBrushColor()
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 12.dp,
                            pressedElevation = 6.dp,
                            disabledElevation = 0.dp
                        )
                    ) {
                        Text(
                            "Cadastrar",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

// Helper para converter Brush para Color para ButtonDefaults (workaround)
@Composable
fun Brush.toBrushColor(): Color {
    // Um truque para criar um gradiente que o ButtonDefaults aceita.
    // Aqui usamos a cor média do gradiente.
    // Para gradientes reais no botão, a API oficial ainda não suporta, mas isso dá uma base.
    val colors = when (this) {
        is Brush.HorizontalGradient -> this.colors
        is Brush.VerticalGradient -> this.colors
        else -> listOf(Color.Blue, Color.Cyan)
    }
    return colors[(colors.size / 2).coerceIn(0, colors.size - 1)]
}

// Classe para partículas animadas
class Particle {
    val startX = Random.nextFloat()
    val startY = Random.nextFloat()
    val radius = Random.nextFloat() * 15f + 5f
    val speed = Random.nextFloat() * 0.2f + 0.05f
    val color = Color(
        red = Random.nextFloat() * 0.4f + 0.6f,
        green = Random.nextFloat() * 0.4f + 0.6f,
        blue = 1f,
        alpha = 1f
    )
}
