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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ezsoftware.ezplans.models.DatosActividadPlan
import com.ezsoftware.ezplans.models.DatosDeudasPorPlan
import com.ezsoftware.ezplans.models.DatosResumenMiembrosPlan
import com.ezsoftware.ezplans.models.DatosResumenPlan
import com.ezsoftware.ezplans.models.DatosVistaDetalladaPlan
import com.ezsoftware.ezplans.ui.components.Dashboard.ResumenCard
import com.ezsoftware.ezplans.viewmodel.ThemeViewModel
import com.ezsoftware.ezplans.viewmodel.VistaDetalladaViewModel

@Composable
fun VistaDetalladaPlan(
    navControlador: NavController,
    themeViewModel: ThemeViewModel,
    vistaDetalladaViewModel: VistaDetalladaViewModel,
    idPlan: Int
){
    var datosVistaDetallada by remember { mutableStateOf<DatosVistaDetalladaPlan?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var mostrarAyuda by remember { mutableStateOf(false) }
    var mostrarTema by remember { mutableStateOf(false) }

    // Función para cargar datos
    fun cargarDatos() {
        idPlan?.let {
            vistaDetalladaViewModel.obtenerDetallesPlan(
                idPlan = it,
                onSuccess = { datosVistaDetallada = it },
                //onError = { errorMessage = it }
            )
        }
    }

    LaunchedEffect(Unit) {
        cargarDatos()
    }

    Box(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            datosVistaDetallada?.let { datos ->

                item { ResumenPlan(datos.resumenPlan) }

                item { TabsPlan(
                    datos.actividades,
                    datos.miembros,
                    datos.deudas)
                }
            }
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
fun ResumenPlan(resumenPlan: DatosResumenPlan){
    TituloVistaDet(
        titulo = resumenPlan.tituloPlan,
        fecha = resumenPlan.fechaPlan,
        estado = resumenPlan.estadoPlan
    )
    ProgressBar(
        avance = resumenPlan.avancePlan
    )
    ResumenDet(
        gasto = resumenPlan.gastoPlan,
        miembros = resumenPlan.cantidadMiembrosPlan,
        actividades = resumenPlan.actividadesCompletadasPlan,
        deudas = resumenPlan.cantidadDeudasPendientesPlan
    )
}

@Composable
fun TituloVistaDet(
    titulo: String,
    fecha: String,
    estado: String
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(top = 8.dp, bottom = 8.dp), // adapta la altura al maximo que ocupan los elementos hijos
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.weight(1f)) {
            SubTitulo(titulo, true)
        }
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Row {
                Imagen("placeholder", 20)
                Spacer(modifier = Modifier.size(5.dp))
                TextoPeq(fecha)
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row {
                Imagen("placeholder", 20)
                Spacer(modifier = Modifier.size(5.dp))
                TextoPeq(estado)
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
fun ProgressBar(
    avance: Double
){
    SubTitulo("$avance% completado", false)
    Spacer(modifier = Modifier.height(8.dp))
    LinearProgressIndicator(
        progress = { avance.toFloat()/100f },
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp),
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun ResumenDet(
    gasto: String,
    miembros: String,
    actividades: String,
    deudas: Int
){
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ResumenCard(
                titulo = "Gato Total",
                cantidad = gasto,
                modifier = Modifier.weight(1f)
            )
            ResumenCard(
                titulo = "Miembros",
                cantidad = miembros,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ResumenCard(
                titulo = "Actividades",
                cantidad = actividades,
                modifier = Modifier.weight(1f)
            )
            ResumenCard(
                titulo = "Deudas pendientes",
                cantidad = deudas.toString(),
                modifier = Modifier.weight(1f)
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun TabsPlan(
    actividades: List<DatosActividadPlan>,
    miembros: List<DatosResumenMiembrosPlan>,
    deudas : List<DatosDeudasPorPlan>
) {
    val tabs = listOf("Actividades", "Miembros", "Deudas")
    val selectedTab = rememberSaveable { mutableStateOf(0) }

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
            2 -> TabDeudas(deudas)
        }
    }
}
