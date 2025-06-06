package com.ezsoftware.ezplans.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.ezsoftware.ezplans.viewmodel.AutenticacionViewModel

@Composable
fun LoginScreen(viewModel: AutenticacionViewModel, onLoginSuccess: () -> Unit) {
    var celular by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

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
        OutlinedTextField(
            value = celular,
            onValueChange = {
                if (it.length <= 10 && it.all { c -> c.isDigit() }) {
                    celular = it
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text("Celular") }
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.login(celular, pass,
                onSuccess = { onLoginSuccess() },
                onError = { error = it }
            )
        }) {
            Text("Iniciar sesión")
        }

        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}
