package com.ezsoftware.ezplans.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun OutlinedTextForms(
    valor: String,
    label: String,
    hayError: Boolean = false,
    ancho: Dp = Dp.Unspecified,
    alto: Dp = Dp.Unspecified,
    tipoTeclado: KeyboardType = KeyboardType.Text,
    transfVisual: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean,
    maxLines: Int = 1,
    onValorChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .then(if (ancho != Dp.Unspecified) Modifier.width(ancho) else Modifier.fillMaxWidth())
            .then(if (alto != Dp.Unspecified) Modifier.height(alto) else Modifier),
        value = valor,
        onValueChange = onValorChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado),
        visualTransformation = transfVisual,
        isError = hayError,
        singleLine = singleLine,
        maxLines = maxLines
    )
}

@Composable
fun ButtonForms(
    texto: String,
    onClick: () -> Unit
){
    Button(
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary),
        onClick = onClick
    ) {
        Text(texto)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    fechaTexto: String,
    hayError: Boolean,
    onDateSelected: (String) -> Unit
) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .clickable { showDatePicker = true },
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            width = 1.dp,
            color = if (hayError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Ícono de fecha",
                tint = if (hayError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
            Text(
                text = if (fechaTexto.isNotEmpty()) fechaTexto else "Selecciona una fecha",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if (hayError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val fechaMillis = datePickerState.selectedDateMillis
                    val texto = fechaMillis?.let {
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            .withZone(ZoneId.systemDefault())
                        formatter.format(Instant.ofEpochMilli(it))
                    } ?: ""
                    onDateSelected(texto)
                    showDatePicker = false
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}


