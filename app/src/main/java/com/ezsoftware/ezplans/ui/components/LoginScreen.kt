package com.ezsoftware.ezplans.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.ezsoftware.ezplans.viewmodel.AutenticacionViewModel

@Composable
fun LoginScreen(viewModel: AutenticacionViewModel, onLoginSuccess: () -> Unit) {
    var celular by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(value = celular, onValueChange = { celular = it }, label = { Text("Celular") })
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
