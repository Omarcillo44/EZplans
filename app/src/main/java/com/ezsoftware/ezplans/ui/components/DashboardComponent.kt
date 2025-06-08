package com.ezsoftware.ezplans.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ezsoftware.ezplans.models.dashboard.FiltroEstadoPlan
import com.ezsoftware.ezplans.models.dashboard.datosDashboard
import com.ezsoftware.ezplans.preferences.PreferenceHelper
import com.ezsoftware.ezplans.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardComponent(viewModel: DashboardViewModel) {
    val context = LocalContext.current
    val idUsuario = PreferenceHelper(context).leerIDUsuario()

    var filtroSeleccionado by remember { mutableStateOf(FiltroEstadoPlan.COMPLETOS) }
    var expandido by remember { mutableStateOf(false) }

    var datosDashboard by remember { mutableStateOf<datosDashboard?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(filtroSeleccionado) {
        idUsuario?.let {
            viewModel.obtenerDatosDashboard(
                idUsuario = it,
                soloCompletos = filtroSeleccionado.soloCompletos,
                esAdmin = true,
                onSuccess = { datosDashboard = it },
                onError = { errorMessage = it }
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        errorMessage?.let {
            Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
        }

        datosDashboard?.let { datos ->
            ResumenCard("Planes administrados", datos.datosResumen.planesAdministrados.toString(), 0) {}
            ResumenCard("Planes participante", datos.datosResumen.planesMiembro.toString(), 0) {}
            ResumenCard("Deudas pendientes", datos.datosResumen.deudasPendientes, 0) {}
            ResumenCard("Por cobrar", datos.datosResumen.deudasPorCobrar, 0) {}
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Filtro dropdown
        ExposedDropdownMenuBox(
            expanded = expandido,
            onExpandedChange = { expandido = !expandido }
        ) {
            TextField(
                value = filtroSeleccionado.label,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expandido,
                onDismissRequest = { expandido = false }
            ) {
                FiltroEstadoPlan.values().forEach { filtro ->
                    DropdownMenuItem(
                        text = { Text(filtro.label) },
                        onClick = {
                            filtroSeleccionado = filtro
                            expandido = false
                        }
                    )
                }
            }
        }
    }
}
