package org.example.project.ui.screens


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.example.network.KtorClient
import org.example.project.network.CadastroRequest
import org.example.project.network.Endereco
import org.example.project.ui.CoralDark
import org.example.project.ui.OrangeStrong
import org.example.project.ui.OrangeLight
import kotlin.random.Random
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.OutlinedTextField
import org.example.project.ui.RedStrong


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(navController: NavController) {

    val infiniteTransition = rememberInfiniteTransition()
    val animProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing)
        )
    )

    var formVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { formVisible = true }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(CoralDark, OrangeLight)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        AnimatedVisibility(
            visible = formVisible,
            enter = fadeIn(tween(700)) + slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(700, easing = FastOutSlowInEasing)
            ),
            modifier = Modifier.align(Alignment.Center) // corrige problema com align
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(34.dp)
                    .shadow(24.dp, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                color = Color.White.copy(alpha = 0.95f)
            ) {
                CadastroForm()
            }
        }
    }
}


@Composable
fun CadastroForm() {
    var cpf by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
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
    val snackbarHostState = remember { SnackbarHostState() }

    // Estados para controlar erro em cada campo
    var cpfError by remember { mutableStateOf(false) }
    var nomeError by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var senhaError by remember { mutableStateOf(false) }
    var telefoneError by remember { mutableStateOf(false) }
    var cepError by remember { mutableStateOf(false) }
    var bairroError by remember { mutableStateOf(false) }
    var cidadeError by remember { mutableStateOf(false) }
    var estadoError by remember { mutableStateOf(false) }
    var enderecoError by remember { mutableStateOf(false) }
    var numeroError by remember { mutableStateOf(false) }
    // complemento normalmente não é obrigatório, então deixei sem erro

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    // Função que valida todos os campos obrigatórios e seta erros
    fun validateFields(): Boolean {
        cpfError = cpf.isBlank()
        nomeError = nome.isBlank()
        usernameError = username.isBlank()
        emailError = email.isBlank()
        senhaError = senha.isBlank()
        telefoneError = telefone.isBlank()
        cepError = cep.isBlank()
        bairroError = bairro.isBlank()
        cidadeError = cidade.isBlank()
        estadoError = estado.isBlank()
        enderecoError = endereco.isBlank()
        numeroError = numero.isBlank()

        return !(cpfError || nomeError || usernameError || emailError || senhaError || telefoneError || cepError ||
                bairroError || cidadeError || estadoError || enderecoError || numeroError)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .align(Alignment.TopCenter),
            snackbar = { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = RedStrong,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(24.dp),

                )
            }
        )

        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Junte-se a uma comunidade inteira de pais de pet",
                style = MaterialTheme.typography.titleSmall.copy(
                    color = CoralDark,
                    fontSize = 14.sp
                )
            )

            StyledTextField(
                cpf,
                { cpf = it },
                "CPF",
                isError = cpfError,
                onValueChangedClearError = { cpfError = false }
            )
            StyledTextField(
                nome,
                { nome = it },
                "Nome Completo",
                isError = nomeError,
                onValueChangedClearError = { nomeError = false }
            )
            StyledTextField(
                username,
                { username = it },
                "Digite um username",
                isError = usernameError,
                onValueChangedClearError = { usernameError = false }
            )
            StyledTextField(
                email,
                { email = it },
                "E-mail",
                isError = emailError,
                onValueChangedClearError = { emailError = false }
            )
            StyledTextField(
                senha,
                { senha = it },
                "Senha",
                isPassword = true,
                isError = senhaError,
                onValueChangedClearError = { senhaError = false }
            )
            StyledTextField(
                telefone,
                { telefone = it },
                "Telefone",
                isError = telefoneError,
                onValueChangedClearError = { telefoneError = false }
            )

            Text(
                text = "Endereço",
                style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF243B55)),
                modifier = Modifier.align(Alignment.Start)
            )

            StyledTextField(
                cep,
                { cep = it },
                "CEP",
                isError = cepError,
                onValueChangedClearError = { cepError = false }
            )
            StyledTextField(
                bairro,
                { bairro = it },
                "Bairro",
                isError = bairroError,
                onValueChangedClearError = { bairroError = false }
            )
            StyledTextField(
                cidade,
                { cidade = it },
                "Cidade",
                isError = cidadeError,
                onValueChangedClearError = { cidadeError = false }
            )
            StyledTextField(
                estado,
                { estado = it },
                "Estado",
                isError = estadoError,
                onValueChangedClearError = { estadoError = false }
            )
            StyledTextField(
                endereco,
                { endereco = it },
                "Endereço",
                isError = enderecoError,
                onValueChangedClearError = { enderecoError = false }
            )
            StyledTextField(
                numero,
                { numero = it },
                "Número",
                isError = numeroError,
                onValueChangedClearError = { numeroError = false }
            )
            StyledTextField(
                complemento,
                { complemento = it },
                "Complemento"
                // opcional, sem erro
            )

            GradientButton(
                text = "Cadastrar",
                onClick = {
                    if (validateFields()) {
                        val enderecoRequest = Endereco(
                            cep, bairro, cidade, numero, estado, endereco, complemento
                        )
                        val cadastroRequest = CadastroRequest(
                            cpf, nome, email, senha,
                            username,
                            telefone, "DONO", enderecoRequest
                        )

                        scope.launch {
                            val sucesso = KtorClient.apiService.cadastrarUsuario(cadastroRequest)
                            if (sucesso) {
                                snackbarHostState.showSnackbar("Cadastro concluído!")
                            } else {
                                snackbarHostState.showSnackbar("Erro no cadastro!")
                            }
                        }
                    }
                }
            )


        }
    }
}

    @Composable
    fun StyledTextField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        isPassword: Boolean = false,
        isError: Boolean = false,
        onValueChangedClearError: () -> Unit = {}
    ) {
        Column {
            OutlinedTextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                    onValueChangedClearError()
                },
                label = { Text(label) },
                singleLine = true,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                isError = isError
            )
            if (isError) {
                Text(
                    text = "Campo obrigatório",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                )
            }
        }
    }


    @Composable
    fun GradientButton(
        text: String,
        onClick: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(CoralDark, OrangeStrong)
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .shadow(12.dp, RoundedCornerShape(24.dp))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }

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

