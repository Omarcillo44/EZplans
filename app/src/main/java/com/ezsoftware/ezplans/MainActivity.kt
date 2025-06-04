package com.ezsoftware.ezplans

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezsoftware.ezplans.ui.components.LoginScreen
import com.ezsoftware.ezplans.ui.theme.EZplansTheme
import com.ezsoftware.ezplans.viewmodel.AutenticacionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EZplansTheme {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppRoot() {
    val viewModel: AutenticacionViewModel = viewModel()
    var isLoggedIn by remember { mutableStateOf(false) }

    if (isLoggedIn) {
        // Aquí puedes poner la siguiente pantalla (por ahora un placeholder)
        UIPrincipal()
    } else {
        LoginScreen(viewModel) { isLoggedIn = true }
    }
}

@Composable
fun UIPrincipal() {
    // Pantalla tras el login
    androidx.compose.material3.Text("¡Bienvenida a la app!")
}
