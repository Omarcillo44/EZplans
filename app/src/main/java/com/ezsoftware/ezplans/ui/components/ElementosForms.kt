package com.ezsoftware.ezplans.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedTextForms(
    valor: String,
    label: String,
    hayError: Boolean,
    size: Int,
    tipoTeclado: KeyboardType,
    onValorChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.width(size.dp),
        value = valor,
        onValueChange = onValorChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = tipoTeclado
        ),
        singleLine = true,
        isError = hayError
    )
}

@Composable
fun OutlinedTextForms(
    valor: String,
    label: String,
    hayError: Boolean,
    size: Int,
    tipoTeclado: KeyboardType,
    transfVisual: VisualTransformation,
    onValorChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.width(size.dp),
        value = valor,
        onValueChange = onValorChange,
        label = { Text(label) },
        visualTransformation = transfVisual,
        keyboardOptions = KeyboardOptions(
            keyboardType = tipoTeclado
        ),
        singleLine = true,
        isError = hayError
    )
}
