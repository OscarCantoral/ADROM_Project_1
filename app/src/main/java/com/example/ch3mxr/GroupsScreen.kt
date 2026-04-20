package com.example.ch3mxr

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GroupsScreen(
    onGrupoClick: (String) -> Unit,
    onLibresClick: () -> Unit,
    onEditClick: (String) -> Unit
) {

    val cyanColor = Color(0xFF0ED2F7)
    val darkBg = Color(0xFF020617)

    var grupos by remember {
        mutableStateOf(listOf("GRUPO ESTUDIOS"))
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

            // 🔹 Título + botón agregar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("MIS GRUPOS", color = Color.White)

                IconButton(onClick = {
                    grupos = grupos + "GRUPO ${grupos.size + 1}"
                }) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = cyanColor)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Lista de grupos
            Column(modifier = Modifier.fillMaxWidth()) {

                grupos.forEach { grupo ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color.Black, RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // 📌 Nombre grupo (click → entrar)
                        Text(
                            text = grupo,
                            color = cyanColor,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onGrupoClick(grupo) }
                        )

                        // ✏️ EDITAR
                        IconButton(onClick = {
                            onEditClick(grupo)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar",
                                tint = cyanColor
                            )
                        }

                        // 🗑️ ELIMINAR
                        IconButton(onClick = {
                            grupos = grupos.filter { it != grupo }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 🔻 Footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "LIBRES",
                        color = Color.Gray,
                        modifier = Modifier.clickable {
                            onLibresClick()
                        }
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "GRUPOS",
                        color = cyanColor
                    )

                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(50.dp)
                            .background(cyanColor)
                    )
                }
            }
        }
    }
}