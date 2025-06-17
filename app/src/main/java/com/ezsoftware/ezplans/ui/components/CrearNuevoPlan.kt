package com.ezsoftware.ezplans.ui.components

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
import androidx.compose.ui.platform.LocalContext


//############ sustituir por obtencion de usuarios real ############
data class Usuario(
    val id: Int,
    val nombre: String,
    val telefono: String
)

val listaUsuarios = listOf(
    Usuario(1,"Ana López", "555-1234"),
    Usuario(2,"Carlos Ruiz", "555-5678"),
    Usuario(3,"Luisa Martínez", "555-9876")
)
// ################################################################

@Composable
fun CrearNuevoPlan(navControlador: NavController, themeViewModel: ThemeViewModel,){

    var mostrarAyuda by remember { mutableStateOf(false) }
    var mostrarTema by remember { mutableStateOf(false) }

    var errorTitulo by rememberSaveable { mutableStateOf(false) }
    var titulo by rememberSaveable { mutableStateOf("") }
    var errorDetalles by rememberSaveable { mutableStateOf(false) }
    var detalles by rememberSaveable { mutableStateOf("") }
    var errorFecha by rememberSaveable { mutableStateOf(false) }
    var fecha by rememberSaveable { mutableStateOf("") }
    var usuariosSelect by rememberSaveable { mutableStateOf(listOf<Int>()) }


    var selectedTab = rememberSaveable { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(10.dp)) {

            item { TituloNuevoPlan() }

            item {
                TabsNuevoPlan(
                    titulo = titulo,
                    errorTitulo = errorTitulo,
                    fecha = fecha,
                    errorFecha = errorFecha,
                    detalles = detalles,
                    usuariosSelect = usuariosSelect,
                    selectedTab = selectedTab,
                    onTituloCambio = { titulo = it },
                    onFechaCambio = { fecha = it },
                    onDetalleCambio = { detalles = it },
                    onUsuariosSelectChange = { usuariosSelect = it },
                    onErrorTituloCambio = { errorTitulo = it },
                    onErrorFechaCambio = { errorFecha = it },
                )
            }

            item {
                Spacer(modifier = Modifier.size(20.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    ButtonForms(
                        texto = "Cancelar",
                        onClick = {
                            navControlador.navigate("UIPrincipal")
                        }
                    )

                    Spacer(modifier = Modifier.size(20.dp))
                    val context = LocalContext.current
                    ButtonForms(
                        texto = "Crear plan",
                        onClick = {
                            if (titulo.isBlank()){
                                Toast.makeText(context, "Debe ingresar un titulo", Toast.LENGTH_SHORT).show()
                                selectedTab.value = 0 // se muestra la tab correspondiente para que el usuario sepa que onda
                                errorTitulo = true
                            }else if (fecha.isBlank()){
                                Toast.makeText(context, "Debe seleccionar una fecha", Toast.LENGTH_SHORT).show()
                                selectedTab.value = 0
                                errorFecha = true
                            }else if (usuariosSelect.size == 0){
                                Toast.makeText(context, "Debe seleccionar almenos un participante", Toast.LENGTH_SHORT).show()
                                selectedTab.value = 1
                            }else{
                                Toast.makeText(context, "titulo: $titulo, fecha: $fecha, detalles: " +
                                        "$detalles, usuarios seleccionados $usuariosSelect", Toast.LENGTH_LONG).show()
                                println("titulo: $titulo")
                                println("fecha: $fecha")
                                println("detalles: $detalles")
                                println("usuarios seleccionados $usuariosSelect")
                            }
                        }
                    )
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
fun TituloNuevoPlan(){
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
            Titulo("Nuevo plan")
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(50.dp)
            ) {
                Icon(imageVector = Icons.Default.Close, "Cerrar")
            }
        }
        Texto("Completa la información para crear un nuevo plan." +
                "Podrás añadir actividades después.", false)
    }
    HorizontalDivider(
        thickness = 2.dp,
        color =  MaterialTheme.colorScheme.outline,
        modifier = Modifier
            .padding(vertical = 10.dp)
    )
}

@Composable
fun TabsNuevoPlan(
    titulo: String,
    errorTitulo: Boolean,
    fecha: String,
    errorFecha: Boolean,
    detalles: String,
    usuariosSelect: List<Int>,
    selectedTab: MutableState<Int>,
    onTituloCambio: (String) -> Unit,
    onFechaCambio: (String) -> Unit,
    onDetalleCambio: (String) -> Unit,
    onUsuariosSelectChange: (List<Int>) -> Unit,
    onErrorTituloCambio: (Boolean) -> Unit,
    onErrorFechaCambio: (Boolean) -> Unit
) {
    val tabs = listOf("Información básica", "Participantes")

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
            0 -> TabInfoBasica(
                titulo = titulo,
                errorTitulo = errorTitulo,
                fecha = fecha,
                errorFecha = errorFecha,
                detalles = detalles,
                onTituloCambio = { onTituloCambio(it) },
                onFechaCambio = { onFechaCambio(it) },
                onDetalleCambio = { onDetalleCambio(it) },
                onErrorTituloCambio = { onErrorTituloCambio(it) },
                onErrorFechaCambio = { onErrorFechaCambio(it) }
            )
            1 -> TabParticipantes(
                usuariosSelect = usuariosSelect,
                onUsuariosSelectChange = onUsuariosSelectChange
            )
        }
    }
}
