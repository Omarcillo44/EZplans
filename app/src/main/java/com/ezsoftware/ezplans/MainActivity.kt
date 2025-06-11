package com.ezsoftware.ezplans

import DashboardComponent
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezsoftware.ezplans.preferences.PreferenceHelper
import com.ezsoftware.ezplans.ui.components.LoginScreen
import com.ezsoftware.ezplans.ui.components.VistaDetalladaPlan
import com.ezsoftware.ezplans.ui.theme.EZplansTheme
import com.ezsoftware.ezplans.viewmodel.AutenticacionViewModel
import com.ezsoftware.ezplans.viewmodel.DashboardViewModel
import com.ezsoftware.ezplans.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        val prefs = PreferenceHelper(applicationContext)
        val isDarkTheme = prefs.leerEstadoModoOscuro()

        val themeViewModel: ThemeViewModel by viewModels()

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
    var isLoggedIn by rememberSaveable { mutableStateOf(false) }

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
    val viewModel: DashboardViewModel = viewModel()

    NavHost(navController = navControlador, startDestination = "VistaDetalladaPlan") {
        composable("UIPrincipal") {
            DashboardComponent(navControlador, themeViewModel, viewModel)
        }
        composable("VistaDetalladaPlan") {
            VistaDetalladaPlan(navControlador, themeViewModel)
        }
    }
}
