import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ezsoftware.ezplans.models.dashboard.datosDashboard
import com.ezsoftware.ezplans.models.dashboard.datosPlan
import com.ezsoftware.ezplans.models.dashboard.datosResumen
import com.ezsoftware.ezplans.preferences.PreferenceHelper
import com.ezsoftware.ezplans.ui.components.DialogoAyuda
import com.ezsoftware.ezplans.ui.components.DialogoTema
import com.ezsoftware.ezplans.ui.components.MenuFab
import com.ezsoftware.ezplans.ui.components.PlanesCard
import com.ezsoftware.ezplans.ui.components.ResumenCard
import com.ezsoftware.ezplans.ui.components.Titulo
import com.ezsoftware.ezplans.viewmodel.DashboardViewModel
import com.ezsoftware.ezplans.viewmodel.ThemeViewModel

@Composable
fun DashboardComponent(navControlador: NavController, themeViewModel: ThemeViewModel, viewModel: DashboardViewModel) {
    val context = LocalContext.current

    // Recuperar datos desde el ViewModel
    val idUsuario = PreferenceHelper(context).leerIDUsuario()
    var datosDashboard by remember { mutableStateOf<datosDashboard?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var soloCompletos by remember { mutableStateOf<Boolean?>(null) }

    var mostrarAyuda by remember { mutableStateOf(false) }
    var mostrarTema by remember { mutableStateOf(false) }

    // Función para cargar datos
    fun cargarDatos() {
        idUsuario?.let {
            viewModel.obtenerDatosDashboard(
                idUsuario = it,
                soloCompletos = soloCompletos,
                esAdmin = null,
                onSuccess = { datosDashboard = it },
                onError = { errorMessage = it }
            )
        }
    }

    LaunchedEffect(Unit) {
        cargarDatos()
    }

    // Recargar cuando cambie el filtro
    LaunchedEffect(soloCompletos) {
        if (datosDashboard != null) { // Solo recargar si ya hay datos iniciales
            cargarDatos()
        }
    }

    Box(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            item { Titulo("Resumen") }

            item {
                errorMessage?.let {
                    Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
                }
            }

            datosDashboard?.let { datos ->
                item { SeccionResumen(datos.datosResumen) }
                item { Spacer(modifier = Modifier.height(25.dp)) }

                // Título "Planes" con combo
                item {
                    SeccionTituloConFiltro(
                        filtroSeleccionado = soloCompletos,
                        onFiltroChange = { soloCompletos = it }
                    )
                }

                item { Spacer(modifier = Modifier.height(15.dp)) }
                item { PlanesLista(datos.datosPlanes) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeccionTituloConFiltro(
    filtroSeleccionado: Boolean?,
    onFiltroChange: (Boolean?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val opciones = listOf(
        "Todos" to null,
        "Completos" to true,
        "Pendientes" to false
    )

    val opcionSeleccionada = opciones.find { it.second == filtroSeleccionado }?.first ?: "Todos"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Titulo("Planes")

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = opcionSeleccionada,
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .width(140.dp),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                textStyle = MaterialTheme.typography.bodyMedium
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opciones.forEach { (texto, valor) ->
                    DropdownMenuItem(
                        text = { Text(texto) },
                        onClick = {
                            onFiltroChange(valor)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SeccionResumen(datosResumen: datosResumen) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ResumenCard(
                titulo = "Planes administrados",
                cantidad = datosResumen.planesAdministrados.toString(),
                modifier = Modifier.weight(1f)
            )
            ResumenCard(
                titulo = "Planes participante",
                cantidad = datosResumen.planesMiembro.toString(),
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ResumenCard(
                titulo = "Deudas pendientes",
                cantidad = datosResumen.deudasPendientes,
                modifier = Modifier.weight(1f)
            )
            ResumenCard(
                titulo = "Por cobrar",
                cantidad = datosResumen.deudasPorCobrar,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun PlanesLista(planes: List<datosPlan>) {
    Column {
        planes.forEach { plan ->
            PlanesCard(
                titulo = plan.titulo,
                fecha = plan.fecha,
                gasto = plan.gastoTotal,
                miembros = plan.numeroDeMiembros,
                rol = plan.rolDelUsuario,
                estado = plan.estadoDelPlan
            )
            Spacer(modifier = Modifier.size(15.dp))
        }
    }
}