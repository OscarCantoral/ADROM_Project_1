package com.example.ch3mxr

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onQuimicaClick: () -> Unit,
    onLibresClick: () -> Unit,
    onGruposClick: () -> Unit
) {

    val cyanColor = Color(0xFF0ED2F7)
    val darkBg = Color(0xFF020617)

    var selectedTab by remember { mutableStateOf("libres") }

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

            Text(
                text = "CURSOS",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { onQuimicaClick() },
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
                Text("QUIMICA")
            }

            Spacer(modifier = Modifier.weight(1f))

            // 🔻 FOOTER (alineado correctamente)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // LIBRES
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

                // GRUPOS
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "GRUPOS",
                        color = if (selectedTab == "grupos") cyanColor else Color.Gray,
                        modifier = Modifier.clickable {
                            selectedTab = "grupos"
                            onGruposClick()
                        }
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