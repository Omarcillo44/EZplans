package com.ezsoftware.ezplans.ui.components

import android.content.Context
import android.os.Build
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ezsoftware.ezplans.preferences.PreferenceHelper
import com.ezsoftware.ezplans.viewmodel.ThemeViewModel

@Composable
fun MenuFab(
    navController: NavController,
    onAyudaClick: () -> Unit,
    onTemaClick: () -> Unit
) {
    var isFabExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp, end = 25.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            if (isFabExpanded) {
                val menuOptions = listOf("Tema", "Ayuda", "Ver planes", "Crear nuevo plan")

                menuOptions.forEach { option ->
                    ExtendedFloatingActionButton(
                        onClick = {
                            when (option) {
                                "Ver planes" -> {
                                    isFabExpanded = false
                                    //navController.navigate("")
                                }
                                "Crear nuevo plan" -> {
                                    isFabExpanded = false
                                    //navController.navigate("")
                                }
                                "Ayuda" -> {
                                    isFabExpanded = false
                                    onAyudaClick()
                                }
                                "Tema" -> {
                                    isFabExpanded = false
                                    onTemaClick()
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .widthIn(min = 150.dp),
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ) {
                        Icon(
                            imageVector = when (option) {
                                "Ayuda" -> Icons.Default.Info
                                "Tema" -> Icons.Default.Build
                                else -> Icons.Default.MoreVert
                            },
                            contentDescription = option
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(option)
                    }
                }
            }

            FloatingActionButton(
                onClick = { isFabExpanded = !isFabExpanded },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = if (isFabExpanded) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = if (isFabExpanded) "Cerrar menú" else "Abrir menú"
                )
            }
        }
    }
}

@Composable
fun MiniFabConTexto(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.secondary,
        shadowElevation = 6.dp,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondary
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
                Text(
                    "Instrucciones para el uso del catálogo:",
                    style = MaterialTheme.typography.bodyMedium
                )

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
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Modo claro
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            themeViewModel.setDarkTheme(false)
                            estadoModoOscuro = false
                            preferenciaModoOscuro.guardarEstadoModoOscuro(false)
                        }
                        .padding(vertical = 6.dp)
                ) {
                    RadioButton(selected = !estadoModoOscuro, onClick = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Modo claro", style = MaterialTheme.typography.bodyLarge)
                }

                // Modo oscuro
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            themeViewModel.setDarkTheme(true)
                            estadoModoOscuro = true
                            preferenciaModoOscuro.guardarEstadoModoOscuro(true)
                        }
                        .padding(vertical = 6.dp)
                ) {
                    RadioButton(selected = estadoModoOscuro, onClick = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Modo oscuro", style = MaterialTheme.typography.bodyLarge)
                }

                // Colores dinámicos (Android 12+)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val nuevoValor = !themeViewModel.themeState.value.useDynamicColor
                                themeViewModel.setDynamicColor(nuevoValor)
                            }
                            .padding(vertical = 6.dp)
                    ) {
                        RadioButton(
                            selected = themeViewModel.themeState.value.useDynamicColor,
                            onClick = null
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Colores dinámicos", style = MaterialTheme.typography.bodyLarge)
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

