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
fun EditGroupScreen(
    groupName: String,
    onBack: () -> Unit
) {

    val cyanColor = Color(0xFF0ED2F7)
    val darkBg = Color(0xFF020617)

    var miembros by remember {
        mutableStateOf(listOf("Adrian", "Oscar", "Luis"))
    }

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

            // 🔹 Título
            Text(
                text = "EDITAR GRUPO: $groupName",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Lista de miembros
            Column(modifier = Modifier.fillMaxWidth()) {

                miembros.forEach { miembro ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .background(Color.Black, RoundedCornerShape(10.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = miembro,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )

                        // ❌ eliminar
                        IconButton(onClick = {
                            miembros = miembros.filter { it != miembro }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Eliminar",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ➕ Agregar miembro
            Button(
                onClick = {
                    miembros = miembros + "Nuevo ${miembros.size + 1}"
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = cyanColor
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Agregar", color = Color.Black)
            }

            Spacer(modifier = Modifier.weight(1f))

            // 🔘 Confirmar
            Button(
                onClick = { onBack() },
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