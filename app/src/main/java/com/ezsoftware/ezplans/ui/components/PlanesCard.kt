package com.ezsoftware.ezplans.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PlanesCard(
    /*
    producto: Producto,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    onClick: () -> Unit
    */
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            //.clickable(onClick = onClick),
            .padding(vertical = 0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column( modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                    //.wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Imagen("placeholder", 50)
                Spacer(modifier = Modifier.width(8.dp))

                Box(modifier = Modifier.weight(1f)){
                    Texto("Salida sana a un lugar sano en condiciones sanas y uno mas solo para probar ekisde", true)
                }
                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Imagen("placeholder", 20)
                        Spacer(modifier = Modifier.size(5.dp))
                        TextoPeq("\$100,000.00")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Imagen("placeholder", 20)
                        Spacer(modifier = Modifier.size(5.dp))
                        TextoPeq("10-06-05")
                    }
                }
            }
            Spacer(modifier = Modifier.size(18.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Row {
                        Imagen("placeholder", 20)
                        Spacer(modifier = Modifier.size(10.dp))
                        TextoPeq("5 miembros")
                    }
                }
                Box {
                    Row {
                        Imagen("placeholder", 20)
                        Spacer(modifier = Modifier.size(10.dp))
                        TextoPeq("Completo")
                    }
                }
                Box {
                    Row {
                        Imagen("placeholder", 20)
                        Spacer(modifier = Modifier.size(10.dp))
                        TextoPeq("Admin")
                    }
                }
            }
        }
    }
}