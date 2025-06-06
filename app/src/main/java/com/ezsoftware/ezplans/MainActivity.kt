package com.ezsoftware.ezplans

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezsoftware.ezplans.preferences.PreferenceHelper
import com.ezsoftware.ezplans.ui.components.LoginScreen
import com.ezsoftware.ezplans.ui.components.MenuFab
import com.ezsoftware.ezplans.ui.components.PlanesCard
import com.ezsoftware.ezplans.ui.components.ResumenCard
import com.ezsoftware.ezplans.ui.components.Titulo
import com.ezsoftware.ezplans.ui.theme.EZplansTheme
import com.ezsoftware.ezplans.viewmodel.AutenticacionViewModel
import com.ezsoftware.ezplans.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val prefs = PreferenceHelper(applicationContext)
        val isDarkTheme = prefs.leerEstadoModoOscuro()

        // Asegúrate de acceder a tu ViewModel así:
        val themeViewModel: ThemeViewModel by viewModels()

        // Actualiza el estado antes de setContent
        themeViewModel.setDarkTheme(isDarkTheme)

        setContent {
            val currentThemeState = themeViewModel.themeState.value

            EZplansTheme (themeState = currentThemeState){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavegacion(themeViewModel)
                    //AppRoot(themeViewModel)
                }
            }
        }
    }
}

@Composable
fun AppRoot(themeViewModel: ThemeViewModel) {
    val viewModel: AutenticacionViewModel = viewModel()
    var isLoggedIn by remember { mutableStateOf(false) }

    if (isLoggedIn) {
        // Aquí puedes poner la siguiente pantalla (por ahora un placeholder)
        AppNavegacion(themeViewModel)
    } else {
        LoginScreen(viewModel) { isLoggedIn = true }
    }
}

@Composable
fun AppNavegacion(themeViewModel: ThemeViewModel) {
    val navControlador = rememberNavController()
    NavHost(navController = navControlador, startDestination = "UIPrincipal") {
        composable("UIPrincipal") {
            UIPrincipal(navControlador, themeViewModel)
        }
    }
}

@Composable
fun UIPrincipal(navControlador: NavController, themeViewModel: ThemeViewModel) {
    //val auxSQLite = DBHelper(LocalContext.current)
    //VistaProductos(auxSQLite, navControlador, themeViewModel)
    VistaInicio(navControlador, themeViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaInicio(navControlador: NavController, themeViewModel: ThemeViewModel){
    var showHelpDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    //val productos = remember { mutableStateOf(dbManager.obtenerProductos()) }

    var alertaEliminacion by remember { mutableStateOf(false) }
    //var productoAEliminar by remember { mutableStateOf<Producto?>(null) }
    var showThemeDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn {
                item {
                    Titulo("Resumen")
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    // variable para que todas las tarjetas tengan la misma altura
                    var alturaCardsRes by remember { mutableIntStateOf(0) }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                ResumenCard("Planes administrados", "15", alturaCardsRes) { h ->
                                    if (h > alturaCardsRes) alturaCardsRes = h
                                }
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                ResumenCard("Planes participante", "6", alturaCardsRes) { h ->
                                    if (h > alturaCardsRes) alturaCardsRes = h
                                }
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                ResumenCard("Deudas pendientes", "$100,000.00", alturaCardsRes) { h ->
                                    if (h > alturaCardsRes) alturaCardsRes = h
                                }
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                ResumenCard("Por cobrar", "$100,000.00", alturaCardsRes) { h ->
                                    if (h > alturaCardsRes) alturaCardsRes = h
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(25.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                    ){
                        Titulo("Mis planes")

                        val opciones = listOf("Completos", "Pendientes")
                        var expandido by remember { mutableStateOf(false) }
                        var seleccion by remember { mutableStateOf(opciones[0]) }

                        ExposedDropdownMenuBox(
                            modifier = Modifier.width(180.dp).height(50.dp),
                            expanded = expandido,
                            onExpandedChange = { expandido = !expandido }
                        ) {
                            TextField(
                                value = seleccion,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
                                modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = expandido,
                                onDismissRequest = { expandido = false }
                            ) {
                                opciones.forEach { opcion ->
                                    DropdownMenuItem(
                                        text = { Text(opcion) },
                                        onClick = {
                                            seleccion = opcion
                                            expandido = false
                                            when (opcion) {
                                                "Completos" -> {
                                                    // Aquí va la lógica para completos
                                                    Toast.makeText(context, "Mostrando tareas completas", Toast.LENGTH_SHORT).show()
                                                }
                                                "Pendientes" -> {
                                                    // Aquí va la lógica para pendientes
                                                    Toast.makeText(context, "Mostrando tareas pendientes", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }

                //items(productos.value) { producto ->
                item{
                    PlanesCard()
                    Spacer(modifier = Modifier.size(16.dp))
                    PlanesCard()
                    Spacer(modifier = Modifier.size(15.dp))
                    PlanesCard()
                    Spacer(modifier = Modifier.size(15.dp))
                    PlanesCard()
                    Spacer(modifier = Modifier.size(15.dp))
                    PlanesCard()
                    Spacer(modifier = Modifier.size(15.dp))
                    PlanesCard()
                    Spacer(modifier = Modifier.size(15.dp))
                    PlanesCard()
                    Spacer(modifier = Modifier.size(15.dp))
                }
                /*
                PlanesCard(
                    producto = producto,
                    onEditar = { navControlador.navigate("EditarProducto/${producto.id}") },
                    onEliminar = { productoAEliminar = producto; alertaEliminacion = true },
                    onClick = { Toast.makeText(context, "Producto #${producto.id}", Toast.LENGTH_SHORT).show() }
                )
                */
                //}
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 96.dp, end = 16.dp) // Padding generoso
        ) {
            MenuFab(
                onAyudaClick = { showHelpDialog = true },
                onTemaClick = { showThemeDialog = true }
            )
        }


        if (showHelpDialog) {
            DialogoAyuda { showHelpDialog = false }
        }

        if (showThemeDialog) {
            DialogoTema(
                onClose = { showThemeDialog = false },
                themeViewModel = themeViewModel
            )
        }
    }
}

@Composable
fun DialogoAyuda(onClose: () -> Unit) {
    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text("Ayuda", style = MaterialTheme.typography.headlineSmall)
        },
        text = {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Text("Instrucciones para el uso del catálogo:", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(12.dp))

                Text("• Deslice hacia arriba o hacia abajo para explorar los productos.")

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Permite editar los datos del producto seleccionado.")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Permite eliminar un producto. Se solicitará confirmación.")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Menu, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Abre el menú lateral con opciones como ayuda y personalización de tema.")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onClose) {
                Text("Entendido")
            }
        }
    )
}



@Composable
fun DialogoTema(
    onClose: () -> Unit,
    themeViewModel: ThemeViewModel
) {
    val context: Context = LocalContext.current
    val preferenciaModoOscuro = remember { PreferenceHelper(context) }

    var estadoModoOscuro by remember { mutableStateOf(preferenciaModoOscuro.leerEstadoModoOscuro()) }

    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text("Seleccionar tema", style = MaterialTheme.typography.headlineSmall)
        },
        text = {
            Column {
                // Opción de tema claro
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            themeViewModel.setDarkTheme(false)
                            estadoModoOscuro = false
                            preferenciaModoOscuro.guardarEstadoModoOscuro(false) // Guardar cambio
                        }
                        .padding(vertical = 8.dp)
                ) {
                    RadioButton(
                        selected = !estadoModoOscuro,
                        onClick = {
                            themeViewModel.setDarkTheme(false)
                            estadoModoOscuro = false
                            preferenciaModoOscuro.guardarEstadoModoOscuro(false) // Guardar cambio
                        }
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = "Modo claro",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Opción de tema oscuro
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            themeViewModel.setDarkTheme(true)
                            estadoModoOscuro = true
                            preferenciaModoOscuro.guardarEstadoModoOscuro(true) // Guardar cambio
                        }
                        .padding(vertical = 8.dp)
                ) {
                    RadioButton(
                        selected = estadoModoOscuro,
                        onClick = {
                            themeViewModel.setDarkTheme(true)
                            estadoModoOscuro = true
                            preferenciaModoOscuro.guardarEstadoModoOscuro(true) // Guardar cambio
                        }
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = "Modo oscuro",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Opción de colores dinámicos (solo para Android 12+)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                themeViewModel.setDynamicColor(!themeViewModel.themeState.value.useDynamicColor)
                            }
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = themeViewModel.themeState.value.useDynamicColor,
                            onClick = {
                                themeViewModel.setDynamicColor(!themeViewModel.themeState.value.useDynamicColor)
                            }
                        )
                        Spacer(Modifier.width(16.dp))
                        Text(
                            text = "Colores dinámicos",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onClose) {
                Text("Aceptar", style = MaterialTheme.typography.labelLarge)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun VistaInicioPreview() {
    // Mock de NavController
    val navController = rememberNavController()

    // Mock de ThemeViewModel (puede necesitar una implementación vacía o simulada)
    val themeViewModel = remember { ThemeViewModel() }

    VistaInicio(navController, themeViewModel)
}