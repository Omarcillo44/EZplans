package com.ezsoftware.ezplans

import android.content.Context
import com.ezsoftware.ezplans.ui.components.Dashboard.DashboardComponent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezsoftware.ezplans.preferences.PreferenceHelper
import com.ezsoftware.ezplans.ui.components.CrearNuevaActividad
import com.ezsoftware.ezplans.ui.components.CrearNuevoPlan
import com.ezsoftware.ezplans.ui.components.LoginScreen
import com.ezsoftware.ezplans.ui.components.VistaDetalladaPlan
import com.ezsoftware.ezplans.ui.theme.EZplansTheme
import com.ezsoftware.ezplans.viewmodel.AutenticacionViewModel
import com.ezsoftware.ezplans.viewmodel.DashboardViewModel
import com.ezsoftware.ezplans.viewmodel.EliminarPlanViewModel
import com.ezsoftware.ezplans.viewmodel.MiembrosPlanViewModel
import com.ezsoftware.ezplans.viewmodel.NuevaActividadViewModel
import com.ezsoftware.ezplans.viewmodel.NuevoPlanViewModel
import com.ezsoftware.ezplans.viewmodel.ThemeViewModel
import com.ezsoftware.ezplans.viewmodel.VistaDetalladaViewModel

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
                    //AppNavegacion(themeViewModel)
                    AppRoot(themeViewModel)
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
        AppNavegacion(themeViewModel)
    } else {
        LoginScreen(viewModel) { isLoggedIn = true }
    }
}

@Composable
fun AppNavegacion(themeViewModel: ThemeViewModel) {
    val navControlador = rememberNavController()
    val dashboardViewModel: DashboardViewModel = viewModel()
    val vistaDetalladaViewModel: VistaDetalladaViewModel = viewModel()
    val nuevoPlanViewModel: NuevoPlanViewModel = viewModel()
    val eliminarPlanViewModel: EliminarPlanViewModel = viewModel()
    val miembrosPlanViewModel: MiembrosPlanViewModel = viewModel()
    val nuevaActividadViewModel: NuevaActividadViewModel = viewModel()


    /*Avísame mañana que hay que cambiar el start destination, ya iba a matar toda esta parte*/
    NavHost(navController = navControlador, startDestination = "UIPrincipal") {
        composable("UIPrincipal") {
            DashboardComponent(navControlador, themeViewModel, dashboardViewModel, vistaDetalladaViewModel)
        }
        composable("VistaDetalladaPlan/{idPlan}") { backStackEntry ->
            val idPlan = backStackEntry.arguments?.getString("idPlan")?.toIntOrNull() ?: -1
            VistaDetalladaPlan(navControlador, themeViewModel, vistaDetalladaViewModel, eliminarPlanViewModel, idPlan)
        }
        composable("CrearNuevoPlan") { backStackEntry ->
            CrearNuevoPlan(navControlador, themeViewModel, nuevoPlanViewModel)
        }
        composable("CrearNuevaActividad/{idPlan}") { backStackEntry ->
            val idPlan = backStackEntry.arguments?.getString("idPlan")?.toIntOrNull() ?: -1
            CrearNuevaActividad(navControlador, themeViewModel, miembrosPlanViewModel, nuevaActividadViewModel, idPlan)
        }
    }
}