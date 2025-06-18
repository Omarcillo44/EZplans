package com.ezsoftware.ezplans.ui.components

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ezsoftware.ezplans.viewmodel.ThemeViewModel
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.ezsoftware.ezplans.preferences.UsuarioRegistrado
import com.ezsoftware.ezplans.preferences.obtenerUsuariosDefault


//############ sustituir por obtencion de usuarios real ############
data class UsuariosActividad(
    val id: Int,
    val nombre: String,
    val telefono: String
)

val listUsuActiv = listOf(
    UsuariosActividad(1,"Ana López", "555-1234"),
    UsuariosActividad(2,"Carlos Ruiz", "555-5678"),
    UsuariosActividad(3,"Luisa Martínez", "555-9876"),
    UsuariosActividad(4,"Luisa Martínez", "555-9876")
)
// ################################################################

@Composable
fun CrearNuevaActividad(navControlador: NavController, themeViewModel: ThemeViewModel,){
    val context = LocalContext.current
    val usuariosDefault = remember { obtenerUsuariosDefault(context) }

    var mostrarAyuda by remember { mutableStateOf(false) }
    var mostrarTema by remember { mutableStateOf(false) }

    var errorTitulo by rememberSaveable { mutableStateOf(false) }
    var titulo by rememberSaveable { mutableStateOf("") }
    var detalles by rememberSaveable { mutableStateOf("") }
    var usuariosSelect by rememberSaveable { mutableStateOf(listOf<Int>()) }
    var nombreUsuSelect by rememberSaveable { mutableStateOf(listOf<String>()) }
    var divicionIgual by remember { mutableStateOf(true) }
    var gastoTotal by remember { mutableStateOf(0) }

    var selectedTab = rememberSaveable { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(10.dp)) {

            item { TituloNuevaActividad(navControlador) }

            item {
                TabsNuevaActividad(
                    usuariosDefault = usuariosDefault,
                    titulo = titulo,
                    errorTitulo = errorTitulo,
                    detalles = detalles,
                    usuariosSelect = usuariosSelect,
                    nombreUsuSelect = nombreUsuSelect,
                    divicionIgual = divicionIgual,
                    gastoTotal = gastoTotal,
                    selectedTab = selectedTab,
                    onTituloCambio = { titulo = it },
                    onDetalleCambio = { detalles = it },
                    onUsuariosSelectChange = { usuariosSelect = it },
                    onErrorTituloCambio = { errorTitulo = it },
                    onDivicionIgual = { divicionIgual = it },
                    onNombreUsuSelect = { nombreUsuSelect = it }
                )
            }

            item {
                BotonesNuevaActividad(
                    navControlador = navControlador,
                    titulo = titulo,
                    detalles = detalles,
                    usuariosSelect = usuariosSelect,
                    divicionIgual = divicionIgual,
                    selectedTab = selectedTab,
                    nombreUsuSelect = nombreUsuSelect
                )
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
fun TituloNuevaActividad(navControlador: NavController){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Titulo("Nueva Actividad")
            IconButton(
                onClick = { navControlador.navigate("UIPrincipal") },
                modifier = Modifier.size(50.dp)
            ) {
                Icon(imageVector = Icons.Default.Close, "Cerrar")
            }
        }
        Texto("Completa la información para crear una nueva actividad", false)
    }
    HorizontalDivider(
        thickness = 2.dp,
        color =  MaterialTheme.colorScheme.outline,
        modifier = Modifier
            .padding(vertical = 10.dp)
    )
}

@Composable
fun TabsNuevaActividad(
    usuariosDefault: List<UsuarioRegistrado>,
    titulo: String,
    errorTitulo: Boolean,
    detalles: String,
    usuariosSelect: List<Int>,
    nombreUsuSelect: List<String>,
    divicionIgual: Boolean,
    gastoTotal: Int,
    selectedTab: MutableState<Int>,
    onTituloCambio: (String) -> Unit,
    onDetalleCambio: (String) -> Unit,
    onUsuariosSelectChange: (List<Int>) -> Unit,
    onErrorTituloCambio: (Boolean) -> Unit,
    onDivicionIgual: (Boolean) -> Unit,
    onNombreUsuSelect: (List<String>) -> Unit
) {
    val tabs = listOf("Información básica", "Participantes", "Contribuciones", "Resumen de deudas")

    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        val configuration = LocalConfiguration.current
        val esHorizontal = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        if(esHorizontal){
            TabRow(
                selectedTabIndex = selectedTab.value,
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab.value == index,
                        onClick = { selectedTab.value = index },
                        enabled = false,
                        text = { Text(text = title) }
                    )
                }
            }
        }else{
            ScrollableTabRow(
                selectedTabIndex = selectedTab.value,
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab.value == index,
                        onClick = { selectedTab.value = index },
                        enabled = false,
                        text = { Text(text = title) }
                    )
                }
            }
        }
        when (selectedTab.value) {
            0 -> TabInfoBasicaActiv(
                titulo = titulo,
                errorTitulo = errorTitulo,
                detalles = detalles,
                onTituloCambio = { onTituloCambio(it) },
                onDetalleCambio = { onDetalleCambio(it) },
                onErrorTituloCambio = { onErrorTituloCambio(it) }
            )
            1 -> TabParticipantesActiv(
                usuariosDefault = usuariosDefault,
                usuariosSelect = usuariosSelect,
                onUsuariosSelectChange = onUsuariosSelectChange,
                divicionIgual = divicionIgual,
                onDivicionIgual = onDivicionIgual,
                nombreUsuSelect = nombreUsuSelect,
                onNombreUsuSelect = onNombreUsuSelect
            )
            2 -> TabContribuciones(
                gastoTotal = gastoTotal,
                usuariosSelect = usuariosSelect,
                nombreUsuSelect = nombreUsuSelect
            )
            3 -> TabResumenDeudas()
        }
    }
}


@Composable
fun BotonesNuevaActividad(
    navControlador: NavController,
    titulo: String,
    detalles: String,
    usuariosSelect: List<Int>,
    divicionIgual: Boolean,
    selectedTab: MutableState<Int>,
    nombreUsuSelect: List<String>,
) {
    HorizontalDivider(
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.outline,
        modifier = Modifier.padding(vertical = 10.dp)
    )
    Spacer(modifier = Modifier.size(20.dp))

    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textoBotonIzq = if (selectedTab.value == 0) "Cancelar" else "Anterior"
        val textoBotonDer = if (selectedTab.value == 3) "Crear Actividad" else "Continuar"

        ButtonForms(
            texto = textoBotonIzq,
            onClick = {
                if (selectedTab.value != 0) {
                    selectedTab.value -= 1
                } else if (selectedTab.value == 0){
                    navControlador.navigate("UIPrincipal")
                }
            }
        )
        Spacer(modifier = Modifier.size(20.dp))

        ButtonForms(
            texto = textoBotonDer,
            onClick = {
                when (selectedTab.value) {
                    0 -> {
                        Toast.makeText(context, "$titulo, $detalles", Toast.LENGTH_SHORT).show()
                            selectedTab.value += 1
                    }
                    1 -> {
                        Toast.makeText(context, "$usuariosSelect, $divicionIgual, $nombreUsuSelect", Toast.LENGTH_SHORT).show()
                        selectedTab.value += 1
                    }
                    2 -> {
                        selectedTab.value += 1
                    }
                    3 -> {
                        Toast.makeText(context, "Plan creado correctamente", Toast.LENGTH_SHORT).show()
                        navControlador.navigate("UIPrincipal")
                    }
                }
            }
        )
    }
}
