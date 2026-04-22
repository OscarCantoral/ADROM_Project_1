package com.example.ch3mxr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CreateGroupScreen(
    onBack: () -> Unit
) {

    val cyanColor = Color(0xFF0ED2F7)
    val darkBg = Color(0xFF020617)

    var nombre by remember { mutableStateOf("") }
    var tematica by remember { mutableStateOf("") }

    var miembroInput by remember { mutableStateOf("") }
    var miembros by remember { mutableStateOf(listOf<String>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg)
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text("CREAR GRUPO", color = Color.White)

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del grupo") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors(cyanColor)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = tematica,
                onValueChange = { tematica = it },
                label = { Text("Asignatura / Temática") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors(cyanColor)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Input miembro + botón agregar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = miembroInput,
                    onValueChange = { miembroInput = it },
                    label = { Text("Agregar miembro") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors(cyanColor)
                )

                IconButton(
                    onClick = {
                        if (miembroInput.isNotBlank()) {
                            miembros = miembros + miembroInput
                            miembroInput = ""
                        }
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = cyanColor)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Lista de miembros
            Column(modifier = Modifier.fillMaxWidth()) {
                miembros.forEach { miembro ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(Color.Black, RoundedCornerShape(10.dp))
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = miembro,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(onClick = {
                            miembros = miembros.filter { it != miembro }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = Color.Red
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 🔘 Confirmar
            Button(
                onClick = {
                    if (
                        nombre.isNotBlank() &&
                        tematica.isNotBlank() &&
                        miembros.isNotEmpty()
                    ) {
                        onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = cyanColor
                )
            ) {
                Text("CONFIRMAR", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// 🔹 Reutilizable para todos los TextField
@Composable
fun textFieldColors(cyanColor: Color) = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedBorderColor = cyanColor,
    unfocusedBorderColor = Color.Gray,
    focusedLabelColor = cyanColor,
    unfocusedLabelColor = Color.Gray,
    cursorColor = cyanColor
)