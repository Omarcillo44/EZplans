package com.ezsoftware.ezplans.ui.components

import android.view.Menu
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ezsoftware.ezplans.viewmodel.AutenticacionViewModel
import com.ezsoftware.ezplans.viewmodel.ThemeViewModel

@Composable
fun LoginScreen(viewModel: AutenticacionViewModel, onLoginSuccess: () -> Unit) {
    var celular by rememberSaveable  { mutableStateOf("") }
    var pass by rememberSaveable  { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var errorPass by rememberSaveable  { mutableStateOf(false) }
    var errorCel by rememberSaveable  { mutableStateOf(false) }

    var mostrarAyuda by remember { mutableStateOf(false) }
    var mostrarTema by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f) // ocupa el espacio sobrante
            ) {
                Titulo("¡Bienvenido!")
                Spacer(modifier = Modifier.size(15.dp))
                Texto("Coloca tus datos para poder ingresar", true)
            }
            Imagen("placeholder", 100) // tamaño fijo
        }

        Spacer(modifier = Modifier.size(40.dp))
        OutlinedTextForms(celular, "Celular", errorCel, 280, KeyboardType.Phone,
            {
                if (it.length <= 10 && it.all { c -> c.isDigit() }) {
                    celular = it
                }
                errorCel = false
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextForms(pass, "Contraseña", errorPass, 280, KeyboardType.Password, PasswordVisualTransformation(),
            {
                pass = it
                errorPass = false
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (celular.isBlank() || celular.length != 10){
                errorCel = true
                error = null
            }else if (pass.isBlank()) {
                errorPass = true
                error = null
            }else {
                //viewModel.login(celular, pass,
                viewModel.login("5561397608", "contraseña",
                    onSuccess = { onLoginSuccess() },
                    onError = { error = it }
                )
            }
        }) {
            Text("Iniciar sesión")
        }

        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        if (errorCel) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Número de celular inválido",
                color = MaterialTheme.colorScheme.error,
            )
        }

        if (errorPass) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "La contraseña no puede estar vacía",
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}
