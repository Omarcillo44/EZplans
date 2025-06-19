package com.ezsoftware.ezplans.ui.components

import android.widget.NumberPicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
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
    capitalizacion: KeyboardCapitalization = KeyboardCapitalization.Sentences,
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
        keyboardOptions = KeyboardOptions(
            keyboardType = tipoTeclado,
            capitalization = capitalizacion),
        visualTransformation = transfVisual,
        isError = hayError,
        singleLine = singleLine,
        maxLines = maxLines
    )
}

@Composable
fun ButtonForms(
    texto: String,
    onClick: () -> Unit,
    habilitado: Boolean = true
){
    Button(
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary),
        onClick = onClick,
        enabled = habilitado
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorDeNumeros(
    valor: Int,
    rangoMin: Int = 0,
    rangoMax: Int = Int.MAX_VALUE,
    onValorCambiado: (Int) -> Unit
) {
    var texto by remember { mutableStateOf(valor.toString()) }

    fun actualizarDesdeTexto(nuevoTexto: String) {
        texto = nuevoTexto
        nuevoTexto.toIntOrNull()?.let {
            if (it in rangoMin..rangoMax) onValorCambiado(it)
        }
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            IconButton(
                onClick = {
                    if (valor > rangoMin) onValorCambiado(valor - 1)
                    texto = (valor - 1).coerceAtLeast(rangoMin).toString()
                },
                enabled = valor > rangoMin,
                modifier = Modifier.weight(1f)
            ) {
                Text("-", style = MaterialTheme.typography.headlineMedium)
            }

            TextField(
                value = texto,
                onValueChange = { actualizarDesdeTexto(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = 4.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )

            IconButton(
                onClick = {
                    if (valor < rangoMax) onValorCambiado(valor + 1)
                    texto = (valor + 1).coerceAtMost(rangoMax).toString()
                },
                enabled = valor < rangoMax,
                modifier = Modifier.weight(1f)
            ) {
                Text("+", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleUserDropdown(
    opciones: List<String>,
    seleccionado: Int?, // solo uno o ninguno
    onSeleccionCambio: (Int?) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val textoSeleccionado = seleccionado?.let { opciones.getOrNull(it) } ?: "Selecciona usuario"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = textoSeleccionado,
            onValueChange = {},
            readOnly = true,
            label = { Text("Usuario") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            textStyle = MaterialTheme.typography.bodyMedium
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            opciones.forEachIndexed { index, usuario ->
                DropdownMenuItem(
                    text = { Text(usuario) },
                    onClick = {
                        onSeleccionCambio(index)
                        expanded = false
                    }
                )
            }
        }
    }
}



