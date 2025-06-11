package com.ezsoftware.ezplans.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun TabActividades(datos: List<Actividad>) {
    val filas = datos.chunked(2) // se ordenan en pares

    Column (verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 12.dp)
    ){
        filas.forEach { fila ->
            var alturaCardsRes by remember { mutableIntStateOf(0) }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CardTabActividad(titulo = fila[0].titulo,
                    gasto = fila[0].gasto,
                    miembros = fila[0].miembros,
                    estado = fila[0].estado,
                    alturaMax = alturaCardsRes,
                    modifier = Modifier.weight(1f)
                ) { h ->
                    alturaCardsRes = h.coerceAtLeast(alturaCardsRes)
                }
                if (fila.size > 1) {
                    CardTabActividad(titulo = fila[1].titulo,
                        gasto = fila[1].gasto,
                        miembros = fila[1].miembros,
                        estado = fila[1].estado,
                        alturaMax = alturaCardsRes,
                        modifier = Modifier.weight(1f)
                    ) { h ->
                        alturaCardsRes = h.coerceAtLeast(alturaCardsRes)
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun TabMiembros(datos: List<Miembro>) {
    Column (verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 12.dp)
    ){
        datos.forEach { dato ->
            CardTabMiembros(nombre = dato.nombre,
                telefono = dato.telefono,
                debe = dato.debe,
                haber = dato.haPagado
            )
        }
    }
}

@Composable
fun TabDeudas() {
    Column (verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 12.dp)
    ){
        //Contenido
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CardTabActividad(
    titulo: String,
    gasto: String,
    miembros: String,
    estado: String,
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Texto(titulo, false)
            Spacer(modifier = Modifier.height(8.dp))
            Texto(gasto, true)
            Spacer(modifier = Modifier.size(10.dp))

            FlowRow( // si no caben en la misma fila los acomoda verticalmente
                horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
            ) {
                Row {
                    Imagen("placeholder", 20)
                    Spacer(modifier = Modifier.size(5.dp))
                    TextoPeq(miembros)
                }
                Row {
                    Imagen("placeholder", 20)
                    Spacer(modifier = Modifier.size(5.dp))
                    TextoPeq(estado)
                }
            }

        }
    }
}

@Composable
fun CardTabMiembros(
    nombre: String,
    telefono: String,
    debe: String,
    haber: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp)
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
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                //horizontalArrangement = Arrangement.SpaceBetween,
                //verticalAlignment = Alignment.CenterVertically
            ) {
                Imagen("placeholder", 50)
                Spacer(modifier = Modifier.size(5.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Texto(nombre, true)
                    TextoPeq(telefono)
                }
                Box(modifier = Modifier
                    .fillMaxSize(),
                    contentAlignment = Alignment.TopEnd
                ){
                    Imagen("placeholder", 20)
                }
            }
            Spacer(modifier = Modifier.size(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row {
                    TextoPeq("Debe:")
                    Spacer(modifier = Modifier.size(10.dp))
                    Imagen("placeholder", 20)
                    Spacer(modifier = Modifier.size(5.dp))
                    TextoPeq(debe)
                }
                Row {
                    TextoPeq("Aportó:")
                    Spacer(modifier = Modifier.size(10.dp))
                    Imagen("placeholder", 20)
                    Spacer(modifier = Modifier.size(5.dp))
                    TextoPeq(haber)
                }
            }
        }
    }
}