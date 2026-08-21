package com.example.ch3mxr.ui.features.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
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
    onCreateClick: () -> Unit,
    onLogoutClick: () -> Unit
) {

    val cyanColor = Color(0xFF0ED2F7)
    val darkBg = Color(0xFF020617)

    var selectedTab by remember { mutableStateOf("grupos") }

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
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MIS GRUPOS",
                    color = Color.White
                )

                IconButton(onClick = onLogoutClick) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Cerrar sesión",
                        tint = Color.Red.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { onCreateClick() },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(60.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = cyanColor
                ),
                border = BorderStroke(1.dp, cyanColor)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = cyanColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("CREAR GRUPO")
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (grupos.isNotEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    grupos.forEach { grupo ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onGrupoClick(grupo) },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Black
                            ),
                            border = BorderStroke(1.dp, cyanColor.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = grupo,
                                    color = cyanColor,
                                    modifier = Modifier.weight(1f)
                                )

                                IconButton(onClick = { onEditClick(grupo) }) {
                                    Icon(Icons.Default.Add, null, tint = cyanColor)
                                }
                            }
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "No tienes grupos aún",
                    color = Color.Gray
                )
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-25).dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "LIBRES",
                        color = if (selectedTab == "libres") cyanColor else Color.Gray,
                        modifier = Modifier.clickable {
                            selectedTab = "libres"
                            onLibresClick()
                        }
                    )

                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(50.dp)
                            .background(
                                if (selectedTab == "libres") cyanColor else Color.Transparent
                            )
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "PRIVADOS",
                        color = cyanColor
                    )

                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(50.dp)
                            .background(
                                if (selectedTab == "grupos") cyanColor else Color.Transparent
                            )
                    )
                }
            }
        }
    }
}
