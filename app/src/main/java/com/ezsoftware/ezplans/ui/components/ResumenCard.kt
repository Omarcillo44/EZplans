package com.ezsoftware.ezplans.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ResumenCard(
    titulo: String,
    cantidad: String,
    alturaMax: Int,
    modifier: Modifier = Modifier, // Parámetro para que no se suicide el ancho
    altura: (Int) -> Unit
) {
    val density = LocalDensity.current

    Card(
        modifier = modifier // Usa el modifier recibido primero, sino cómo xdxdxd
            .fillMaxWidth()
            .then(
                if (alturaMax > 0)
                    Modifier.height(with(density) { alturaMax.toDp() })
                else Modifier
            )
            .onGloballyPositioned {
                altura(it.size.height)
            }
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(15.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Texto(titulo, false)
            Spacer(modifier = Modifier.height(8.dp))
            Texto(cantidad, true)
        }
    }
}