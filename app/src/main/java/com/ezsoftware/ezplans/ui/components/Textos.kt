package com.ezsoftware.ezplans.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Titulo(
    titulo: String
){
    Text(
        text = titulo,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun SubTitulo(
    titulo: String,
    bold: Boolean
){
    Text(
        text = titulo,
        fontSize = 20.sp,
        fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun Texto(
    texto: String,
    bold: Boolean
){
    Text(
        text = texto,
        fontSize = 18.sp,
        fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun TextoPeq(
    texto: String
){
    Text(
        text = texto,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onSurface
    )
}



