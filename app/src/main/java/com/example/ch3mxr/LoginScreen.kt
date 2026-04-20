package com.example.ch3mxr

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
    val darkBlueBg = Color(0xFF020617)
    val appShape = RoundedCornerShape(30.dp)

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
                    colors = listOf(deepBlue, darkBlueBg) // Uso de nombres corregidos
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 36.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(160.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

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
                    unfocusedBorderColor = cyanColor.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                    unfocusedBorderColor = cyanColor.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(26.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Lo alineamos a la derecha
            ) {
                TextButton(
                    onClick = { /* Aquí iría la lógica para recuperar contraseña */ }
                ) {
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        color = cyanColor.copy(alpha = 0.8f), // Un poco más tenue
                        fontSize = 14.sp
                    )
                }
            }

            if (errorState.isNotEmpty()) {
                Text(text = errorState, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Botón Ingresar
            if (isLoading) {
                CircularProgressIndicator(color = cyanColor)
            } else {
                Button(
                    onClick = {
                        if (usuarioState.isBlank() || passwordState.isBlank()) {
                            errorState = "Completa todos los campos"
                        } else {
                            isLoading = true
                            scope.launch {
                                delay(2000)
                                if (usuarioState == "admin" && passwordState == "1234") {
                                    onLoginSuccess()
                                } else {
                                    errorState = "Usuario o contraseña incorrectos"
                                }
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = appShape, // Uso de nombre corregido
                    colors = ButtonDefaults.buttonColors(
                        containerColor = cyanColor, // Uso de nombre corregido
                        contentColor = deepBlue // Uso de nombre corregido
                    )
                ) {
                    Text("Ingresar", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                Text("Registrar", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}