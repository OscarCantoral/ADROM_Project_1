package com.example.ch3mxr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {

    val cyanColor = Color(0xFF0ED2F7)
    val deepBlue = Color(0xFF0F172A)
    val darkBlueBg = Color(0xFF020617)
    val appShape = RoundedCornerShape(30.dp)

    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    var error by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(deepBlue, darkBlueBg)
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Registro",
                color = Color.White,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 Campo helper
            @Composable
            fun field(
                value: String,
                onChange: (String) -> Unit,
                placeholder: String,
                icon: @Composable () -> Unit,
                isPassword: Boolean = false
            ) {
                OutlinedTextField(
                    value = value,
                    onValueChange = {
                        onChange(it)
                        error = ""
                    },
                    placeholder = { Text(placeholder, color = Color.Gray) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    leadingIcon = icon,
                    visualTransformation = if (isPassword && !passwordVisible)
                        PasswordVisualTransformation()
                    else
                        VisualTransformation.None,
                    trailingIcon = if (isPassword) {
                        {
                            IconButton(onClick = {
                                passwordVisible = !passwordVisible
                            }) {
                                Icon(
                                    imageVector = if (passwordVisible)
                                        Icons.Default.Visibility
                                    else
                                        Icons.Default.VisibilityOff,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    } else null,
                    shape = appShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = cyanColor,
                        unfocusedBorderColor = cyanColor.copy(alpha = 0.5f)
                    )
                )
            }

            // 🔹 Campos
            field(usuario, { usuario = it }, "Usuario", {
                Icon(Icons.Default.Person, null, tint = cyanColor)
            })

            field(password, { password = it }, "Contraseña", {
                Icon(Icons.Default.Lock, null, tint = cyanColor)
            }, isPassword = true)

            field(nombre, { nombre = it }, "Nombre", {
                Icon(Icons.Default.Badge, null, tint = cyanColor)
            })

            field(apellido, { apellido = it }, "Apellido", {
                Icon(Icons.Default.Badge, null, tint = cyanColor)
            })

            field(correo, { correo = it }, "Correo", {
                Icon(Icons.Default.Email, null, tint = cyanColor)
            })

            field(telefono, { telefono = it }, "Teléfono", {
                Icon(Icons.Default.Phone, null, tint = cyanColor)
            })

            if (error.isNotEmpty()) {
                Text(error, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔘 Botón Registrar
            if (isLoading) {
                CircularProgressIndicator(color = cyanColor)
            } else {
                Button(
                    onClick = {

                        if (
                            usuario.isBlank() ||
                            password.isBlank() ||
                            nombre.isBlank() ||
                            apellido.isBlank() ||
                            correo.isBlank() ||
                            telefono.isBlank()
                        ) {
                            error = "Todos los campos son obligatorios"
                            return@Button
                        }

                        isLoading = true

                        scope.launch {
                            delay(2000)
                            onRegisterSuccess()
                            isLoading = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = appShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = cyanColor,
                        contentColor = deepBlue
                    )
                ) {
                    Text("Registrar")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = { onBackToLogin() }) {
                Text("Volver al login", color = cyanColor)
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}