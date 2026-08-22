package com.example.ch3mxr.ui.components

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView

@Composable
fun PdfComponent(
    isOpened: Boolean,
    onClose: () -> Unit
) {

    if (!isOpened) return

    BackHandler {
        onClose()
    }

    val darkBg = Color(0xFF020617)
    val pdfRed = Color(0xFFDC3129)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg)
            .statusBarsPadding()
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            color = Color(0xFF0B1220),
            shadowElevation = 4.dp
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "MANUAL",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            onClose()
                        },
                    shape = RoundedCornerShape(10.dp),
                    color = pdfRed
                ) {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar manual",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        AndroidView(
            factory = { context ->
                PDFView(context, null).apply {
                    fromAsset("manual.pdf")
                        .defaultPage(0)
                        .spacing(4)
                        .onError { error ->
                            Log.e("PdfComponent", "Error al cargar el manual", error)
                        }
                        .load()
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        )
    }
}
