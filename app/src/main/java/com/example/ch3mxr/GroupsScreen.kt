package com.example.ch3mxr

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
    onEditClick: (String) -> Unit,
    onCreateClick: () -> Unit
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

            // HEADER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("MIS GRUPOS", color = Color.White)

                IconButton(onClick = { onCreateClick() }) {
                    Icon(Icons.Default.Add, null, tint = cyanColor)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // LISTA
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

                        Text(
                            text = grupo,
                            color = cyanColor,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onGrupoClick(grupo) }
                        )

                        IconButton(onClick = { onEditClick(grupo) }) {
                            Icon(Icons.Default.Edit, null, tint = cyanColor)
                        }

                        IconButton(onClick = {
                            grupos = grupos.filter { it != grupo }
                        }) {
                            Icon(Icons.Default.Delete, null, tint = Color.Red)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 🔻 FOOTER (ALINEADO IGUAL QUE HOME)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "LIBRES",
                        color = Color.Gray,
                        modifier = Modifier.clickable {
                            onLibresClick()
                        }
                    )

                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(50.dp)
                            .background(Color.Transparent)
                    )
                }

                // GRUPOS ACTIVO
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