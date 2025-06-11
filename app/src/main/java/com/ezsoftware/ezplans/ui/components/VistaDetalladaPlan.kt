package com.ezsoftware.ezplans.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ezsoftware.ezplans.viewmodel.ThemeViewModel



//------------------------------- para probar XD  -------------------------------//
// Data class para los planes
data class Actividad(
    val titulo: String,
    val gasto: String,
    val miembros: String,
    val estado: String
)

// Data class para los miembros
data class Miembro(
    val nombre: String,
    val telefono: String,
    val debe: String,
    val haPagado: String
)
//----------------- sustituir por la obtencion normal de datos ------------------//
val actividades = listOf(
    Actividad("Actividad absolutamente sana parte del plan sano", "$100000.00", "5 miembros", "pendiente"),
    Actividad("Actividad", "$100000.00", "5 miembros", "pendiente"),
    Actividad("Actividad absolutamente sano", "$100000.00", "5 miembros", "pendiente"),
    Actividad("Actividad absolutamente sano", "$100000.00", "5 miembros", "pendiente"),
    Actividad("Actividad absolutamente sano", "$100000.00", "5 miembros", "pendiente")
)

val miembros = listOf(
    Miembro("Omar Lorenzo o no ekisde", "5555555555", "$100000.00", "$100000.00"),
    Miembro("Omar Lorenzo", "5555555555", "$100000.00", "$100000.00"),
    Miembro("Omar Lorenzo", "5555555555", "$100000.00", "$100000.00"),
    Miembro("Omar Lorenzo", "5555555555", "$100000.00", "$100000.00"),
    Miembro("Omar Lorenzo", "5555555555", "$100000.00", "$100000.00")
)
//-------------------------------    -------------------------------//



@Composable
fun VistaDetalladaPlan(navControlador: NavController, themeViewModel: ThemeViewModel){
//fun VistaDetallada(){
    var mostrarAyuda by remember { mutableStateOf(false) }
    var mostrarTema by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            item { TituloVistaDet() }

            item { ProgressBar() }

            item { ResumenDet() }

            item { TabsPlan(actividades, miembros) }
        }
    }


    MenuFab(
        navControlador,
        onAyudaClick = { mostrarAyuda = true },
        onTemaClick = { mostrarTema = true }
    )
    if (mostrarAyuda) DialogoAyuda { mostrarAyuda = false }
    if (mostrarTema) DialogoTema(onClose = { mostrarTema = false }, themeViewModel = themeViewModel)

}

@Composable
fun TituloVistaDet(){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(top = 8.dp, bottom = 8.dp), // adapta la altura al maximo que ocupan los elementos hijos
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.weight(1f)) {
            SubTitulo("Salida sana a un lugar sano en condiciones sanas", true)
        }
        Column(
            modifier = Modifier
                .fillMaxHeight(), 
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Row {
                Imagen("placeholder", 20)
                TextoPeq("10-06-05")
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row {
                Imagen("placeholder", 20)
                TextoPeq("Pendiente")
            }
        }
    }

    HorizontalDivider(
        thickness = 2.dp,
        color =  MaterialTheme.colorScheme.outline,
        modifier = Modifier
            .padding(vertical = 10.dp)
    )
}

@Composable
fun ProgressBar(){
    SubTitulo("50% completado", false)
    Spacer(modifier = Modifier.height(8.dp))
    LinearProgressIndicator(
        progress = { 0.5f },
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp),
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun ResumenDet(){
    var alturaCardsRes by remember { mutableIntStateOf(0) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ResumenCard(
                titulo = "Gato Total",
                cantidad = "$100,000.0",
                alturaMax = alturaCardsRes,
                modifier = Modifier.weight(1f)
            ) { h ->
                alturaCardsRes = h.coerceAtLeast(alturaCardsRes)
            }
            ResumenCard(
                titulo = "Miembros",
                cantidad = "6",
                alturaMax = alturaCardsRes,
                modifier = Modifier.weight(1f)
            ) { h ->
                alturaCardsRes = h.coerceAtLeast(alturaCardsRes)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ResumenCard(
                titulo = "Actividades",
                cantidad = "2/4",
                alturaMax = alturaCardsRes,
                modifier = Modifier.weight(1f)
            ) { h ->
                alturaCardsRes = h.coerceAtLeast(alturaCardsRes)
            }
            ResumenCard(
                titulo = "Deudas pendientes",
                cantidad = "3",
                alturaMax = alturaCardsRes,
                modifier = Modifier.weight(1f)
            ) { h ->
                alturaCardsRes = h.coerceAtLeast(alturaCardsRes)
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun TabsPlan(actividades: List<Actividad>, miembros: List<Miembro>) {
    val tabs = listOf("Actividades", "Miembros", "Deudas")
    val selectedTab = remember { mutableStateOf(0) }

    Column {
        TabRow(selectedTabIndex = selectedTab.value) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab.value == index,
                    onClick = { selectedTab.value = index },
                    text = { Text(text = title) },
                )
            }
        }
        // Contenido para cada tab
        when (selectedTab.value) {
            0 -> TabActividades(actividades)
            1 -> TabMiembros(miembros)
            2 -> TabDeudas()
        }
    }
}
