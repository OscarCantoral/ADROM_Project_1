package com.example.ch3mxr

import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import com.example.ch3mxr.ParticleBackground
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ch3mxr.ui.theme.Octosquares
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenImproved(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    // 1. Nombres corregidos (empiezan con minúscula)
    val cyanColor = Color(0xFF0ED2F7)
    val deepBlue = Color(0xFF0F172A)
    val darkBlueBg = Color(0xFF0B255F)
    val appShape = RoundedCornerShape(12.dp)

    // Estados
    var usuarioState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }
    var errorState by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        deepBlue,
                        darkBlueBg
                    )
                )
            )
    )
    {
        ParticleBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 36.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            // Logo
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(top = 33.dp)
            ) {

                LogoGlow()

                Image(
                    painter = painterResource(
                        R.drawable.tablet
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(220.dp)
                )

                AnimatedFlask()
            }

            Image(
                painter = painterResource(
                    id = R.drawable.logo_cxrb
                ),
                contentDescription = "ChemXR",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .offset(y = (-35).dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Campo Usuario
            OutlinedTextField(
                value = usuarioState,
                onValueChange = {
                    usuarioState = it
                    errorState = ""
                },
                placeholder = { Text("Usuario", color = Color.Gray) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = cyanColor // Uso de nombre corregido
                    )
                },
                shape = appShape, // Uso de nombre corregido
                colors = OutlinedTextFieldDefaults.colors(

                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,

                    focusedBorderColor = cyanColor,
                    unfocusedBorderColor = cyanColor.copy(alpha = 0.4f),

                    focusedContainerColor = Color(0xFF18316D).copy(alpha = 0.45f),
                    unfocusedContainerColor = Color(0xFF18316D).copy(alpha = 0.35f)
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Campo Contraseña
            OutlinedTextField(
                value = passwordState,
                onValueChange = {
                    passwordState = it
                    errorState = ""
                },
                placeholder = { Text("Contraseña", color = Color.Gray) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = cyanColor // Uso de nombre corregido
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.6f)
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                shape = appShape, // Uso de nombre corregido
                colors = OutlinedTextFieldDefaults.colors(

                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,

                    focusedBorderColor = cyanColor,
                    unfocusedBorderColor = cyanColor.copy(alpha = 0.4f),

                    focusedContainerColor = Color(0xFF18316D).copy(alpha = 0.45f),
                    unfocusedContainerColor = Color(0xFF18316D).copy(alpha = 0.35f)
                )
            )

            Spacer(modifier = Modifier.height(10.dp))



            if (errorState.isNotEmpty()) {
                Text(text = errorState, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Botón Ingresar
            if (isLoading) {
                CircularProgressIndicator(color = cyanColor)
            } else {
                if (isLoading) {

                    CircularProgressIndicator(
                        color = cyanColor
                    )

                } else {

                    AnimatedGlowButton(

                        text = "Ingresar",

                        onClick = {

                            if (
                                usuarioState.isBlank() ||
                                passwordState.isBlank()
                            ) {

                                errorState =
                                    "Completa todos los campos"

                            } else {

                                isLoading = true

                                scope.launch {

                                    delay(2000)

                                    if (
                                        usuarioState == "admin" &&
                                        passwordState == "1234"
                                    ) {

                                        onLoginSuccess()

                                    } else {

                                        errorState =
                                            "Usuario o contraseña incorrectos"
                                    }

                                    isLoading = false
                                }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Botón Registrar
            OutlinedButton(
                onClick = { onRegisterClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = appShape, // Uso de nombre corregido
                border = BorderStroke(2.dp, cyanColor), // Uso de nombre corregido
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = cyanColor // Uso de nombre corregido
                )
            ) {
                Text("Registrarse", fontSize = 18.sp,
                    fontFamily = Octosquares, fontWeight = FontWeight.SemiBold)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Lo alineamos a la derecha
            ) {
                TextButton(
                    onClick = { /* Aquí iría la lógica para recuperar contraseña */ }
                ) {
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        fontFamily = Octosquares,
                        color = cyanColor.copy(alpha = 0.8f), // Un poco más tenue
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(
                            Color.White.copy(alpha = 0.15f)
                        )
                )

                Text(
                    text = " O CONTINÚA CON ",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(
                            Color.White.copy(alpha = 0.15f)
                        )
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        1.dp,
                        Color(0xFFE5E7EB)
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {

                    Icon(
                        painter = painterResource(
                            R.drawable.ic_google
                        ),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        text = " Google",
                        color = Color.Black
                    )
                }

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        1.dp,
                        Color(0xFFE5E7EB)
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        painter = painterResource(
                            R.drawable.ic_microsoft
                        ),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        text = " Microsoft",
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun LogoGlow() {

    Canvas(
        modifier = Modifier
            .size(220.dp)
    ) {

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFF29D3FF).copy(alpha = 2.00f),
                    Color(0xFF29D3FF).copy(alpha = 0.10f),
                    Color.Transparent
                ),
                center = Offset(
                    size.width / 2,
                    size.height / 2
                ),
                radius = size.minDimension / 2
            ),
            radius = size.minDimension / 2,
            center = Offset(
                size.width / 2,
                size.height / 2
            )
        )
    }
}